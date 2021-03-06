/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;

public class SubAppInternalInterfaceEditPart extends UntypedSubAppInterfaceElementEditPart {
	
	@Override
	protected IFigure createFigure() {
		InterfaceFigure figure = new InterfaceFigure();
		figure.setBorder(new ConnectorBorder(getModel()){
			@Override
			public boolean isInput() {
				return !super.isInput();
			}
		});
		return figure;
	}
	
	@Override
	public boolean isInput() {
		return !super.isInput();
	}
	
	
}
