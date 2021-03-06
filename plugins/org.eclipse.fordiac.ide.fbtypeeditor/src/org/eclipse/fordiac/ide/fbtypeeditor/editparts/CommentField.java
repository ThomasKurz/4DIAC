/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

class CommentField {
	private final IInterfaceElement referencedElement;

	public IInterfaceElement getReferencedElement() {
		return referencedElement;
	}

	/**
	 * Helper object to display comment of an in/output.
	 * 
	 * @param referencedElement the referenced element
	 */
	CommentField(IInterfaceElement referencedElement) {
		this.referencedElement = referencedElement;
	}
	
	public String getLabel() {
		return getReferencedElement().getComment();
	}
}
