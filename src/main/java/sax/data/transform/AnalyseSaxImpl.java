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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import play.sax.main.AbstractTimeSeriesEntry;
import play.sax.main.TimeSeriesEntryWithSaxSymbol;
import play.sax.main.TimeSeriesInSax;

public class AnalyseSaxImpl implements AnalyseSax {

	private TimeSeriesInSax series;
	private Map<Integer, Integer> saxSymbolsMapToFrequencies = new TreeMap<>();

	public AnalyseSaxImpl(TimeSeriesInSax series) {
		this.series = series;
	}

	@Override
	public void computeFrequenciesOfSax() {
		for (TimeSeriesEntryWithSaxSymbol entry : series.getSeries()) {
			saxSymbolsMapToFrequencies.merge(entry.getSax(), 1, Integer::sum);
		}
		System.out.printf("Entry set of Sax map to frequencies: %s\n",
				saxSymbolsMapToFrequencies.entrySet());
		System.out.println();
	}

	@Override
	public List<Integer> locateMostFrequentSax(final int number) {

		final List<Integer> frequentSaxSymbols = new ArrayList<>();
		saxSymbolsMapToFrequencies.entrySet().stream()
				.sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
				.forEach(o -> {
					if (frequentSaxSymbols.size() < number) {
						frequentSaxSymbols.add(o.getKey());
					}
				});

		System.out
				.printf("Most frequent sax symbols: %s\n", frequentSaxSymbols);
		System.out.println();
		return frequentSaxSymbols;
	}

	@Override
	public List<? extends AbstractTimeSeriesEntry> extractEntryGivenSax(
			final Integer sax) {
		final List<TimeSeriesEntryWithSaxSymbol> entryList = new ArrayList<>();
		series.getSeries().stream().filter(entry -> entry.getSax().equals(sax))
				.forEach(entry -> entryList.add(entry));
		return entryList;
	}
}
