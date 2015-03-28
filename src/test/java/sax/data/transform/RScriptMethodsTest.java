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

import java.util.List;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import sax.data.transform.RScriptMethods;
import sax.data.transform.SaxRepresentationImpl;

public class RScriptMethodsTest extends RScriptMethods {

	@Test(groups = "r_script")
	public void testRScriptWriteAndOutput() {
		SaxRepresentationImpl impl = Mockito.mock(SaxRepresentationImpl.class);
		impl = new SaxRepresentationImpl(4, 4);

		impl.mean = 0.0;
		impl.standardDeviation = 1.0;

		impl.determineBreakpoints();
		List<Double> outcome = impl.breakPoints;
		Assert.assertEquals(outcome.get(1), -0.6744898);
		Assert.assertEquals(outcome.get(2), 0.0);
		Assert.assertEquals(outcome.get(3), 0.6744898);
	}
}
