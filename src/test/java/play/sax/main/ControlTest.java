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

import org.testng.Assert;
import org.testng.annotations.Test;

public class ControlTest {

	@Test
	public void testTimeSeriesEntryCollisionPrevention() {
		TimeSeriesEntry entry1 = new TimeSeriesEntry(0L, 10.0);
		TimeSeriesEntry entry2 = new TimeSeriesEntry(2L, 20.0);
		TimeSeriesEntry entry3 = new TimeSeriesEntry(1L, 0.0);
		TimeSeriesEntry entry4 = new TimeSeriesEntry(0L, 0.0);

		TimeSeries series = new TimeSeries();
		series.addTimeSeriesEntry(entry1);
		series.addTimeSeriesEntry(entry2);
		series.addTimeSeriesEntry(entry3);
		series.addTimeSeriesEntry(entry4);

		Assert.assertEquals(series.getSeries().size(), 3);
	}
}
