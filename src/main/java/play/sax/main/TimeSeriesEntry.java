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

public class TimeSeriesEntry extends AbstractTimeSeriesEntry implements
		Comparable<TimeSeriesEntry> {
	final protected Long time;
	final protected Double value;

	public TimeSeriesEntry(Long time, Double value) {
		this.time = time;
		this.value = value;
	}

	public Long getTime() {
		return time;
	}

	public Double getValue() {
		return value;
	}

	@Override
	public int compareTo(TimeSeriesEntry o) {
		return time.compareTo(o.time);
	}

}
