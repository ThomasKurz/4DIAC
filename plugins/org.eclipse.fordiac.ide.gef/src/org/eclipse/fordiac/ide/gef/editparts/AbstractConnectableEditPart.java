/*******************************************************************************
 * Copyright (c) 2015 Profactor GbmH, fortiss GmbH
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

import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.gef.policies.ConnectionMovementHighlightEditPolicy;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public abstract class AbstractConnectableEditPart extends AbstractGraphicalEditPart {

	boolean connectable = false;

	public boolean isConnectable() {
		return connectable;
	}

	public void setConnectable(boolean connectable) {
		this.connectable = connectable;
	}

	protected ConnectionMovementHighlightEditPolicy cmPolicy;

	@Override
	protected void createEditPolicies() {
		if (isConnectable()) {
			cmPolicy = new ConnectionMovementHighlightEditPolicy();
			installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, cmPolicy);
		}
	}

	@Override
	public DragTracker getDragTracker(Request request) {
		if (useConnectionTool()) {
			return new ConnCreateDirectEditDragTrackerProxy(this); 
		}
		return super.getDragTracker(request);
	}

	protected boolean useConnectionTool() {
		if (!isConnectable() || cmPolicy == null) {
			return false;
		}
		return cmPolicy.getCurrentInteractionStyle() == InteractionStyleFigure.REGION_CONNECTION;
	}

}
