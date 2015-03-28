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

import play.sax.main.AbstractTimeSeries;

public abstract class AbstractSaxRepresentation {

	protected AbstractTimeSeries series;
	protected final int dimension;
	protected final int cardinality;

	protected List<Double> breakPoints = new ArrayList<>();

	protected AbstractSaxRepresentation(int dimension, int cardinality) {
		this.dimension = dimension;
		this.cardinality = cardinality;
	}

	abstract public void importTimeSeries(AbstractTimeSeries series);

}
