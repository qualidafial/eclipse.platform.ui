/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.tests.preferences;

import org.eclipse.core.runtime.Platform;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.tests.util.UITestCase;

/**
 * The FontPreferenceTestCase tests adding fonts to
 * the preference store and what occurs if the values
 * are bogus
 */

public class FontPreferenceTestCase extends UITestCase {

	public String BAD_FONT_DEFINITION = "BadFont-regular-10";
	public String TEST_FONT_ID = "org.eclipse.jface.tests.preference.testfont";
	public String MISSING_FONT_ID =
		"org.eclipse.jface.tests.preference.missingfont";

	private IPreferenceStore preferenceStore;

	/**
	 * Constructor for FontPreferenceTestCase.
	 * @param testName
	 */
	public FontPreferenceTestCase(String testName) {
		super(testName);
	}

	/*
	 * @see TestCase#setUp
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AbstractUIPlugin plugin =
			(AbstractUIPlugin) Platform.getPlugin(PlatformUI.PLUGIN_ID);
		preferenceStore = plugin.getPreferenceStore();

		//Set up the bogus entry for the bad first test
		FontData bogusData = new FontData();
		bogusData.setName("BadData");
		bogusData.setHeight(11);
		FontData[] storedValue = new FontData[2];

		//We assume here that the text font is OK
		storedValue[0] = bogusData;
		storedValue[1] =
			(PreferenceConverter
				.getDefaultFontDataArray(preferenceStore, JFaceResources.TEXT_FONT))[0];
		PreferenceConverter.setValue(preferenceStore,TEST_FONT_ID,storedValue);
		PreferenceConverter.setDefault(preferenceStore,TEST_FONT_ID,storedValue);

	}
	
	
	/**
	 * Test for a valid font like the test font. The first good one
	 * we should find should be the first one in the list.
	 */
	
	public void testGoodFontDefinition(){
		
		FontRegistry fontRegistry = JFaceResources.getFontRegistry();
		FontData[] currentTextFonts = 
			PreferenceConverter.getFontDataArray(preferenceStore, JFaceResources.TEXT_FONT);
		FontData[] bestFont = fontRegistry.bestDataArray(currentTextFonts,Display.getCurrent());
		
		//Assert that it is the first font that we get as the
		//valid one
		assertEquals(bestFont[0].getName(),currentTextFonts[0].getName());
		assertEquals(bestFont[0].getHeight(),currentTextFonts[0].getHeight());
	}
	
	/**
	 * Test that if the first font in the list is bad that the 
	 * second one comes back as valid.
	 */
	
	public void testBadFirstFontDefinition(){
		
		FontRegistry fontRegistry = JFaceResources.getFontRegistry();
		FontData[] currentTestFonts = 
			PreferenceConverter.getFontDataArray(preferenceStore, TEST_FONT_ID);
		FontData[] bestFont = fontRegistry.bestDataArray(currentTestFonts,Display.getCurrent());
		
		//Assert that it is the second font that we get as the
		//valid one
		assertEquals(bestFont[0].getName(),currentTestFonts[1].getName());
		assertEquals(bestFont[0].getHeight(),currentTestFonts[1].getHeight());
	}
	
	/**
	 * Test that the no valid font is returned when the entry
	 * is missing.
	 */
	
	public void testNoFontDefinition(){
		
		FontRegistry fontRegistry = JFaceResources.getFontRegistry();
		FontData[] currentTestFonts = 
			PreferenceConverter.getFontDataArray(preferenceStore, MISSING_FONT_ID);
		FontData[] bestFont = fontRegistry.bestDataArray(currentTestFonts,Display.getCurrent());
		
		FontData[] systemFontData = Display.getCurrent().getSystemFont().getFontData();
		
		//Assert that the first font is the system font
		assertEquals(bestFont[0].getName(),systemFontData[0].getName());
		assertEquals(bestFont[0].getHeight(),systemFontData[0].getHeight());
	}
}