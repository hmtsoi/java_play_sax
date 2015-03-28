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
package play.sax.main;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sax.data.transform.AnalyseSaxImpl;
import sax.data.transform.SaxRepresentationImpl;

public class Main {

	final private static int ITEMS_IN_RANDOM_TIME_SERIES = 4000;
	final private static int DATA_LOWER_BOUND = 10;
	final private static int DATA_RANGE = 50;

	final static Logger logger = LoggerFactory.getLogger(Main.class);

	private static TimeSeries generateRandomTimeSeries(final int noOfEntries) {

		final TimeSeries series = new TimeSeries();
		RandomDataGenerator randomData = new RandomDataGenerator();
		Random rand = new Random();

		for (int i = 0; i < noOfEntries; i++) {
			final Long time = randomData.nextLong(0, 20000);
			final Double value = DATA_LOWER_BOUND + DATA_RANGE
					* rand.nextDouble();
			final TimeSeriesEntry entry = new TimeSeriesEntry(time, value);

			series.addTimeSeriesEntry(entry);
		}
		return series;
	}

	public static void main(String[] args) throws IOException {

		TimeSeries series = generateRandomTimeSeries(ITEMS_IN_RANDOM_TIME_SERIES);

		SaxRepresentationImpl repr = new SaxRepresentationImpl(50, 16);
		repr.importTimeSeries(series);
		repr.doStatistics();
		repr.determineBreakpoints();
		TimeSeriesInSax seriesInSax = repr.translateTimeSeries();

		AnalyseSaxImpl analysis = new AnalyseSaxImpl(seriesInSax);
		analysis.computeFrequenciesOfSax();
		List<Integer> intList = analysis.locateMostFrequentSax(3);
		for (Integer sax : intList) {
			List<TimeSeriesEntryWithSaxSymbol> entryList = (List<TimeSeriesEntryWithSaxSymbol>) analysis
					.extractEntryGivenSax(sax);
			entryList.forEach(e -> {
				System.out.printf("Index: %d, sax: %d, value: %f\n",
						e.getIndex(), e.getSax(), e.getValue());
			});
			System.out.println();
		}
	}
}
