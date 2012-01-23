/*******************************************************************************
 * Copyright (c) 2010 Ovidio Mallo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ovidio Mallo - initial API and implementation (bug 299619)
 *     Ovidio Mallo - bug 301370
 ******************************************************************************/

package org.eclipse.core.tests.internal.databinding.property.value;

import org.eclipse.core.databinding.observable.map.WritableMap;
import org.eclipse.core.internal.databinding.beans.BeanPropertyHelper;
import org.eclipse.core.internal.databinding.beans.BeanValueProperty;
import org.eclipse.core.internal.databinding.property.value.MapSimpleValueObservableMap;
import org.eclipse.core.internal.databinding.property.value.SelfValueProperty;
import org.eclipse.core.tests.internal.databinding.beans.Bean;
import org.eclipse.jface.databinding.conformance.util.ChangeEventTracker;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class MapSimpleValueObservableMapTest extends
		AbstractDefaultRealmTestCase {

	public void testGetKeyValueType() {
		WritableMap masterMap = new WritableMap(String.class, Integer.class);
		SelfValueProperty detailProperty = new SelfValueProperty(Object.class);

		MapSimpleValueObservableMap detailMap = new MapSimpleValueObservableMap(
				masterMap, detailProperty);

		assertEquals(masterMap.getKeyType(), detailMap.getKeyType());
		assertEquals(detailProperty.getValueType(), detailMap.getValueType());
	}

	public void testPut_ReplacedOldValue() {
		// Create any simple master map and detail property.
		WritableMap masterMap = new WritableMap(String.class, Integer.class);
		SelfValueProperty detailProperty = new SelfValueProperty(Integer.class);

		MapSimpleValueObservableMap detailMap = new MapSimpleValueObservableMap(
				masterMap, detailProperty);

		// Our common key.
		String key = "key";

		// Add an entry on the master map for our key.
		Integer oldValue = new Integer(111);
		masterMap.put(key, oldValue);

		// Replace the entry on the detail map for our key.
		Integer newValue = new Integer(777);
		Object returnedOldValue = detailMap.put(key, newValue);

		// Check that the replaced old value is our original value.
		assertSame(oldValue, returnedOldValue);
	}

	public void testLastListenerRemovedClearsListeners() {
		WritableMap masterMap = new WritableMap(String.class, Bean.class);

		Bean masterValue = new Bean("detailValue");
		masterMap.put("masterKey", masterValue);

		BeanValueProperty detailProperty = new BeanValueProperty(
				BeanPropertyHelper.getPropertyDescriptor(Bean.class, "value"),
				String.class);
		MapSimpleValueObservableMap detailMap = new MapSimpleValueObservableMap(
				masterMap, detailProperty);

		assertFalse(masterValue.hasListeners("value"));

		ChangeEventTracker tracker = ChangeEventTracker.observe(detailMap);

		assertTrue(masterValue.hasListeners("value"));

		detailMap.removeChangeListener(tracker);

		assertFalse(masterValue.hasListeners("value"));
	}
}
