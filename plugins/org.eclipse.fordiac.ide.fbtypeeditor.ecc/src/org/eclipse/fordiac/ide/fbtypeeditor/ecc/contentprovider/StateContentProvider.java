/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider;

import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class StateContentProvider implements ITreeContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof ECState) {
			return ((ECState) inputElement).getOutTransitions().toArray();
		}
		return new Object[] {};
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ECState && null != ((ECState) parentElement).getOutTransitions()) {
			return ((ECState) parentElement).getOutTransitions().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof ECState) {
			return ((ECState) element).eContainer();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ECState) {
			return ((ECState) element).getOutTransitions().isEmpty();
		}
		return false;
	}
}
