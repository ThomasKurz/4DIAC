/*******************************************************************************
 * Copyright (c) 2008, 2012, 2013, 2016 Profactor GbmH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.systemconfiguration.policies;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * The Class DeviceViewLayoutEditPolicy.
 */
public class DeviceViewLayoutEditPolicy extends ConstrainedLayoutEditPolicy {
	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
		int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
		if (cornerDim > 1) {
			cornerDim = cornerDim / 2;
		}
		return new ModifiedNonResizeableEditPolicy(cornerDim, new Insets(1));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#
	 * createChangeConstraintCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	@Override
	protected Command createChangeConstraintCommand(final EditPart child, final Object constraint) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getConstraintFor
	 * (org.eclipse.draw2d.geometry.Point)
	 */
	@Override
	protected Object getConstraintFor(final Point point) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getConstraintFor
	 * (org.eclipse.draw2d.geometry.Rectangle)
	 */
	@Override
	protected Object getConstraintFor(final Rectangle rect) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.
	 * eclipse .gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (request == null) {
			return null;
		}
		Object childClass = request.getNewObjectType();
		if (childClass instanceof org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry) {
			org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry type = (org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry) request
					.getNewObjectType();

			if (getHost() instanceof DeviceEditPart) {
				DeviceEditPart devEditPart = (DeviceEditPart) getHost();
				return new ResourceCreateCommand(type, devEditPart.getModel(), false);
			}
		}
		return null;

	}

	/**
	 * @see org.eclipse.gef.EditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	public Command getCommand(Request request) {
		Object type = request.getType();
		if (REQ_ALIGN.equals(type))
			return getAlignCommand((AlignmentRequest) request);

		return super.getCommand(request);
	}

	/**
	 * Returns the command contribution to an alignment request
	 * 
	 * @param request
	 *            the alignment request
	 * @return the contribution to the alignment
	 */
	protected Command getAlignCommand(AlignmentRequest request) {
		AlignmentRequest req = new AlignmentRequest(REQ_ALIGN_CHILDREN);
		req.setEditParts(getHost());
		req.setAlignment(request.getAlignment());
		req.setAlignmentRectangle(request.getAlignmentRectangle());
		return getHost().getParent().getCommand(req);
	}

}
