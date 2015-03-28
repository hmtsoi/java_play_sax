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

public class TimeSeriesInSax extends AbstractTimeSeries {

	final private List<TimeSeriesEntryWithSaxSymbol> series;

	public TimeSeriesInSax(List<TimeSeriesEntryWithSaxSymbol> series) {
		this.series = new ArrayList<>(series);
	}

	public void addTimeSeriesEntry(TimeSeriesEntryWithSaxSymbol entry) {
		addEntry(entry);
	}

	private boolean addEntry(TimeSeriesEntryWithSaxSymbol entry) {
		return series.add(entry);
	}

	public List<TimeSeriesEntryWithSaxSymbol> getSeries() {
		final List<TimeSeriesEntryWithSaxSymbol> output = new ArrayList<>(
				series);
		Collections.sort(output);
		return output;
	}
}
