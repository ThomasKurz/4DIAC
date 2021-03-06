/*******************************************************************************
 * Copyright (c) 2012, 2014, 2017 Profactor GbmH, fortiss GmbH 
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
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;

/**
 * The Class ToolTipFigure.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ValueToolTipFigure extends ToolTipFigure {
	/**
	 * Instantiates a new tool tip figure.
	 * 
	 * @param element
	 *            the element
	 */
	public ValueToolTipFigure(final INamedElement element, Value value) {
		super(element);
		Label l = new Label();
		l.setText(value.getValue());
		line.add(l);
	}

}
