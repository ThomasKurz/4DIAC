/*******************************************************************************
 * Copyright (c) 2009, 2012, 2015, 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.gef.EditPart;


/**
 * Objects implementing this element can create an EditPart (for graphical
 * Visualisation in GEF editors).
 */
public interface IEditPartCreator {

	/**
	 * Creates the edit part.
	 * 
	 * @return the created EditPart
	 */
	public EditPart createEditPart();
	
}
