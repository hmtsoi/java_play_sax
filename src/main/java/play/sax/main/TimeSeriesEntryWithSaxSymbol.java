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

public class TimeSeriesEntryWithSaxSymbol extends AbstractTimeSeriesEntry
		implements Comparable<TimeSeriesEntryWithSaxSymbol> {

	private Integer vectorIndex;
	private Double value;
	private Integer sax;

	public TimeSeriesEntryWithSaxSymbol(Integer vectorIndex, Double value,
			Integer saxWord) {
		this.vectorIndex = vectorIndex;
		this.value = value;
		this.sax = saxWord;
	}

	public Integer getIndex() {
		return vectorIndex;
	}

	public Double getValue() {
		return value;
	}

	public Integer getSax() {
		return sax;
	}

	@Override
	public int compareTo(TimeSeriesEntryWithSaxSymbol o) {
		return vectorIndex.compareTo(o.vectorIndex);
	}
}
