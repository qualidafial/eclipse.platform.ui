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
package org.eclipse.jface.tests.viewers;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;

public class TreeViewerTest extends AbstractTreeViewerTest {
			
	public TreeViewerTest(String name) {
		super(name);
	}
	protected StructuredViewer createViewer(Composite parent) {
		fTreeViewer= new TreeViewer(parent);
		fTreeViewer.setContentProvider(new TestModelContentProvider());
		return fTreeViewer;
	}
	protected int getItemCount() {
		TestElement first= fRootElement.getFirstChild();
		TreeItem ti= (TreeItem)fViewer.testFindItem(first);
		Tree tree= ti.getParent();		
		return tree.getItemCount();
	}
/**
 * getItemCount method comment.
 */
protected int getItemCount(TestElement element) {
	return 0;
}
	protected String getItemText(int at) {
		Tree tree= (Tree) fTreeViewer.getControl();
		return tree.getItems()[at].getText();
	}
	public static void main(String args[]) {
		junit.textui.TestRunner.run(TreeViewerTest.class);
	}
}
