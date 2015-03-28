/*
 *  Copyright 2015 -  Hung Ming Tsoi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * 
 */
package sax.data.transform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RScriptMethods {

	protected RScriptMethods() {
		// Utility class
	}

	final private static String RDirectory = "tmp";
	final private static Charset CHARSET = Charset.forName("UTF-8");

	final private static Logger logger = LoggerFactory
			.getLogger(RScriptMethods.class);

	static String createRScript(SaxRepresentationImpl saxRepresentation)
			throws IOException {

		if (!Files.exists(Paths.get(RDirectory))) {
			new File(RDirectory).mkdir();
		}

		final String timestamp = String.valueOf(System.currentTimeMillis());
		final String filepath = String.format("%s/script_%s", RDirectory,
				timestamp);
		try (BufferedWriter writer = Files.newBufferedWriter(
				Paths.get(filepath), CHARSET)) {

			saxRepresentation.writeRScript(writer);
		}
		new File(filepath).setExecutable(true);
		return filepath;
	}

	static void runRScriptToGetBreakpoints(final String filepath,
			final List<Double> breakpoints) throws IOException,
			InterruptedException {

		final String rCommand = String.format("./%s", filepath);
		final Process callRscript = Runtime.getRuntime().exec(rCommand);

		final Runnable runReadThread = new Runnable() {
			@Override
			public void run() {
				try (BufferedReader input = new BufferedReader(
						new InputStreamReader(callRscript.getInputStream()))) {

					getBreakpointsFromR(input, breakpoints);

				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		};

		final Thread readThread = new Thread(runReadThread);
		readThread.start();
		callRscript.waitFor();
	}

	private static void getBreakpointsFromR(final BufferedReader input,
			final List<Double> breakPoints) throws IOException {

		breakPoints.add(Double.NEGATIVE_INFINITY);
		String line = null;

		while ((line = input.readLine()) != null) {
			final String breakpointStr = line.split(" ")[1];
			final Double breakpoint = Double.parseDouble(breakpointStr);
			breakPoints.add(breakpoint);
		}
		breakPoints.add(Double.POSITIVE_INFINITY);
	}

}
