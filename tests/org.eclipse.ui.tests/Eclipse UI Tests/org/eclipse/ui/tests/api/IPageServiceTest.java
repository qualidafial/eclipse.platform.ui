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
package org.eclipse.ui.tests.api;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.IWorkbenchConstants;
import org.eclipse.ui.tests.util.EmptyPerspective;
import org.eclipse.ui.tests.util.UITestCase;

/**
 * Tests the IPageService class.
 */
public class IPageServiceTest extends UITestCase 
	implements IPageListener, org.eclipse.ui.IPerspectiveListener
{
	private IWorkbenchWindow fWindow;
	private IWorkspace fWorkspace;
	
	private boolean pageEventReceived;
	private boolean perspEventReceived;
	
	public IPageServiceTest(String testName) {
		super(testName);
	}

	protected void setUp() {
		fWindow = openTestWindow();
		fWorkspace = ResourcesPlugin.getWorkspace();
	}
	
	/**
	 * Tests the addPageListener method.
	 */	
	public void testAddPageListener() throws Throwable {
/*
 * Commented out because until test case can be updated to work
 * with new window/page/perspective implementation
 * 
		// From Javadoc: "Adds the given listener for page lifecycle events.
		// Has no effect if an identical listener is already registered."
		
		// Add listener.
		fWindow.addPageListener(this);
		
		// Open and close page.
		// Verify events are received.
		pageEventReceived = false;
		IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
			fWorkspace);
		page.close();
		assertTrue(pageEventReceived);
		
		// Remove listener.	
		fWindow.removePageListener(this);		
*/
	}
	
	/**
	 * Tests the removePageListener method.
	 */
	public void testRemovePageListener() throws Throwable {
		// From Javadoc: "Removes the given page listener.
		// Has no affect if an identical listener is not registered."
		
		// Add and remove listener.
		fWindow.addPageListener(this);
		fWindow.removePageListener(this);		
		
		// Open and close page.
		// Verify no events are received.
		pageEventReceived = false;
		IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
			fWorkspace);
		page.close();
		assertTrue(!pageEventReceived);
	}
	
	/**
	 * Tests getActivePage.
	 */
	public void testGetActivePage() throws Throwable {
/*
 * Commented out because until test case can be updated to work
 * with new window/page/perspective implementation
 * 
		// From Javadoc: "return the active page, or null if no page 
		// is currently active"
		
		// Add page.
		IWorkbenchPage page1 = fWindow.openPage(EmptyPerspective.PERSP_ID,
			fWorkspace);
		assertEquals(fWindow.getActivePage(), page1);
		
		// Add second page.
		IWorkbenchPage page2 = fWindow.openPage(EmptyPerspective.PERSP_ID,
			fWorkspace);
		assertEquals(fWindow.getActivePage(), page2);
		
		// Set active page.
		fWindow.setActivePage(page1);
		assertEquals(fWindow.getActivePage(), page1);
		fWindow.setActivePage(page2);
		assertEquals(fWindow.getActivePage(), page2);
		
		// Cleanup.
		page1.close();
		page2.close();
*/
	}
	
	/**
	 * Tests the addPerspectiveListener method.
	 */	
	public void testAddPerspectiveListener() throws Throwable {
/*
 * Commented out because until test case can be updated to work
 * with new window/page/perspective implementation
 * 
		// From Javadoc: "Adds the given listener for a page's perspective lifecycle events.
		// Has no effect if an identical listener is already registered."
		
		// Add listener.
		fWindow.addPerspectiveListener(this);
		
		// Open page and change persp feature.
		// Verify events are received.
		perspEventReceived = false;
		IWorkbenchPage page = fWindow.openPage(IWorkbenchConstants.DEFAULT_LAYOUT_ID,
			fWorkspace);
		page.setEditorAreaVisible(false);
		page.setEditorAreaVisible(true);
		page.close();
		assertTrue(perspEventReceived);
		
		// Remove listener.	
		fWindow.removePerspectiveListener(this);		
*/
	}
	
	/**
	 * Tests the removePerspectiveListener method.
	 */	
	public void testRemovePerspectiveListener() throws Throwable {
		// From Javadoc: "Removes the given page's perspective listener.
		// Has no affect if an identical listener is not registered."
		
		// Add and remove listener.
		fWindow.addPerspectiveListener(this);
		fWindow.removePerspectiveListener(this);		
		
		// Open page and change persp feature.
		// Verify no events are received.
		perspEventReceived = false;
		IWorkbenchPage page = fWindow.openPage(IWorkbenchConstants.DEFAULT_LAYOUT_ID,
			fWorkspace);
		page.setEditorAreaVisible(false);
		page.setEditorAreaVisible(true);
		page.close();
		assertTrue(!perspEventReceived);
	}
	
	/**
	 * @see IPageListener#pageActivated(IWorkbenchPage)
	 */
	public void pageActivated(IWorkbenchPage page) {
		pageEventReceived = true;
	}

	/**
	 * @see IPageListener#pageClosed(IWorkbenchPage)
	 */
	public void pageClosed(IWorkbenchPage page) {
		pageEventReceived = true;
	}

	/**
	 * @see IPageListener#pageOpened(IWorkbenchPage)
	 */
	public void pageOpened(IWorkbenchPage page) {
		pageEventReceived = true;
	}

	/**
	 * @see IPerspectiveListener#perspectiveActivated(IWorkbenchPage, IPerspectiveDescriptor)
	 */
	public void perspectiveActivated(IWorkbenchPage page,
		IPerspectiveDescriptor perspective) 
	{
		perspEventReceived = true;
	}

	/**
	 * @see IPerspectiveListener#perspectiveChanged(IWorkbenchPage, IPerspectiveDescriptor, String)
	 */
	public void perspectiveChanged(IWorkbenchPage page,
		IPerspectiveDescriptor perspective, String changeId) 
	{
		perspEventReceived = true;
	}

}