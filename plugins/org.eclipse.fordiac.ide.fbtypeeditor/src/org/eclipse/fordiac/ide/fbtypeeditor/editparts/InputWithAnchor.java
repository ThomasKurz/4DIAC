/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;

public class InputWithAnchor extends WithAnchor {
	
	public InputWithAnchor(IFigure figure, int pos, EditPart editPart) {
		super(figure, pos, editPart);
	}

	@Override
	public Point getLocation(final Point reference) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBox());
		r.translate(-1, -1);
		r.resize(1, 1);
		getOwner().translateToAbsolute(r);
		int leftX = (int)(r.x - (float)((10.0 * getZoomFactor()) * pos));
		int centerY = (int)(r.y + 0.5f * r.height);
		return new Point(leftX, centerY);
	}
}
