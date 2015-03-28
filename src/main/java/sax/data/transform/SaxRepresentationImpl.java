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

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.sax.main.AbstractTimeSeries;
import play.sax.main.TimeSeries;
import play.sax.main.TimeSeriesEntry;
import play.sax.main.TimeSeriesEntryWithSaxSymbol;
import play.sax.main.TimeSeriesInSax;

public class SaxRepresentationImpl extends AbstractSaxRepresentation implements
		TransformToSaxStrategy {

	final private double reciprocal_a;

	final protected List<Double> reductionVectors = new ArrayList<>();

	protected double mean;
	protected double standardDeviation;
	private TimeSeries series;

	final private static Logger logger = LoggerFactory
			.getLogger(SaxRepresentationImpl.class);

	public SaxRepresentationImpl(int dimension, int cardinality) {
		super(dimension, cardinality);
		this.reciprocal_a = 1 / (double) cardinality;
	}

	@Override
	public void importTimeSeries(final AbstractTimeSeries series) {
		this.series = (TimeSeries) series;
	}

	@Override
	public void doStatistics() {

		final DescriptiveStatistics stats = new DescriptiveStatistics();

		final List<TimeSeriesEntry> entries = series.getSeries();
		for (TimeSeriesEntry entry : entries) {
			double value = entry.getValue();
			stats.addValue(value);
		}
		this.mean = stats.getMean();
		this.standardDeviation = stats.getStandardDeviation();
		System.out.printf("Mean: %s, sd: %s\n", mean, standardDeviation);
	}

	private void evaluatePpaRepresentation() {
		double dimension_over_length = dimension
				/ (double) series.getSeries().size();
		double length_over_dimension = series.getSeries().size() / dimension;

		for (int i = 1; i <= dimension; i++) {
			final int lower_summation_index = (int) Math
					.ceil(length_over_dimension * (i - 1) + 1);
			final double upper_summation_index = length_over_dimension * i;
			double sum = 0.0;
			for (int j = lower_summation_index; j < upper_summation_index; j++) {
				sum += series.getSeries().get(j).getValue();
			}
			reductionVectors.add(dimension_over_length * sum);
		}
	}

	@Override
	public void determineBreakpoints() {

		final String filepath;

		final String s = "Error running R script ";
		try {
			filepath = RScriptMethods.createRScript(this);
			RScriptMethods.runRScriptToGetBreakpoints(filepath,
					this.breakPoints);
			Files.delete(Paths.get(filepath));
		} catch (IOException e) {
			logger.error(s, e);
		} catch (InterruptedException e) {
			logger.error(s, e);
		}
	}

	@Override
	public TimeSeriesInSax translateTimeSeries() {

		evaluatePpaRepresentation();

		final List<TimeSeriesEntryWithSaxSymbol> seriesInSax = new ArrayList<>();
		System.out.println("Breakpoints: " + breakPoints);

		for (int vectorIndex = 0; vectorIndex < reductionVectors.size(); vectorIndex++) {
			Double vector = reductionVectors.get(vectorIndex);

			for (int breakPointIndex = 0; breakPointIndex < breakPoints.size(); breakPointIndex++) {

				if (vector > breakPoints.get(breakPointIndex)
						&& vector <= breakPoints.get(breakPointIndex + 1)) {
					final int saxWord = cardinality - breakPointIndex;
					TimeSeriesEntryWithSaxSymbol entry = new TimeSeriesEntryWithSaxSymbol(
							vectorIndex, vector, saxWord);
					seriesInSax.add(entry);
					System.out.println("Vector index: " + entry.getIndex()
							+ ", vector: " + entry.getValue() + ", saxWord: "
							+ saxWord);
					break;
				}
			}
		}
		return new TimeSeriesInSax(seriesInSax);
	}

	void writeRScript(final BufferedWriter writer) throws IOException {
		writer.write("#!/usr/bin/Rscript\n");
		for (int i = 1; i < cardinality; i++) {
			final String s = String.format("qnorm(%f, mean=%f, sd=%f)\n", i
					* reciprocal_a, mean, standardDeviation);
			writer.write(s);
		}
	}
}
