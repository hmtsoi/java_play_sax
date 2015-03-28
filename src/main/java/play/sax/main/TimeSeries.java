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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSeries extends AbstractTimeSeries {

	final private List<TimeSeriesEntry> series;
	final private Logger logger = LoggerFactory.getLogger(TimeSeries.class);

	public TimeSeries() {
		series = new ArrayList<TimeSeriesEntry>();
	}

	public void addTimeSeriesEntry(TimeSeriesEntry entry) {

		final boolean additionSuccessful = addEntry(entry);
		if (!additionSuccessful) {
			final String conflictInfo = String.format(
					"Datum already exists for time: %d", entry.getTime());
			logger.info(conflictInfo);
		}
	}

	private boolean addEntry(TimeSeriesEntry entry) {
		if (series.parallelStream().anyMatch(
				e -> entry.getTime() == e.getTime())) {
			return false;
		} else {
			return series.add(entry);
		}
	}

	public List<TimeSeriesEntry> getSeries() {
		final List<TimeSeriesEntry> output = new ArrayList<>(series);
		Collections.sort(output);
		return output;
	}
}
