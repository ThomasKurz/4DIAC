/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Michael Hofmann, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppInstanceCommand;
import org.eclipse.fordiac.ide.application.commands.ListFBCreateCommand;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.SetPositionCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.util.dnd.TransferDataSelectionOfFb;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class FBNetworkXYLayoutEditPolicy extends XYLayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new ModifiedNonResizeableEditPolicy();

	}

	@Override
	protected Command createAddCommand(final EditPart child,
			final Object constraint) {
		return null;
	}

	@Override
	protected Command getDeleteDependantCommand(final Request request) {
		return null;
	}

	@Override
	protected Command createChangeConstraintCommand(
			final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		// return a command that can move a "ViewEditPart"
		if (child.getModel() instanceof PositionableElement
				&& constraint instanceof Rectangle) {
			return new SetPositionCommand((PositionableElement)child.getModel(), request, (Rectangle) constraint);
		}
		return null;
	}

	@Override
	protected Command createChangeConstraintCommand(final EditPart child,
			final Object constraint) {
		// not used
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (request == null) {
			return null;
		}
		Object childClass = request.getNewObjectType();
		Rectangle constraint = (Rectangle) getConstraintFor(request);
		if (getHost().getModel() instanceof FBNetwork) {
			FBNetwork fbNetwork = (FBNetwork)getHost().getModel(); 
			if (childClass instanceof FBTypePaletteEntry) {
				FBTypePaletteEntry type = (FBTypePaletteEntry) childClass;
				return new FBCreateCommand(type, fbNetwork, constraint.getLocation().x, constraint.getLocation().y);	
			}
			if (childClass instanceof FBTypePaletteEntry[]) {
				FBTypePaletteEntry[] type = (FBTypePaletteEntry[]) childClass;
				return new ListFBCreateCommand(type, fbNetwork, constraint.getLocation().x, constraint.getLocation().y);
			}			
			if (childClass instanceof SubApplicationTypePaletteEntry) {
				SubApplicationTypePaletteEntry type = (SubApplicationTypePaletteEntry) request .getNewObjectType();
				return new CreateSubAppInstanceCommand(type, fbNetwork, constraint.getLocation().x, constraint.getLocation().y);
			}			
			if (childClass instanceof TransferDataSelectionOfFb[]) {
				TransferDataSelectionOfFb[] type = (TransferDataSelectionOfFb[]) childClass;
				return new ListFBCreateCommand(type, fbNetwork, constraint.getLocation().x, constraint.getLocation().y);
			}
		}		
		return null;
	}

	@Override
	protected Command getAddCommand(final Request generic) {
		return null;
	}
}
