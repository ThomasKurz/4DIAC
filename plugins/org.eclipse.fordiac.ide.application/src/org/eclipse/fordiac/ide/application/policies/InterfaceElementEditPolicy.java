/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.application.commands.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public abstract class InterfaceElementEditPolicy extends GraphicalNodeEditPolicy {
	
	@SuppressWarnings("rawtypes")
	private Class cmdRef;

	@SuppressWarnings("rawtypes")
	public InterfaceElementEditPolicy(Class cmdRef) {
		super();
		this.cmdRef = cmdRef;
	}
	
	@Override
	protected Command getConnectionCompleteCommand(
			final CreateConnectionRequest request) {
		if (request.getStartCommand().getClass().equals(cmdRef)) {
			AbstractConnectionCreateCommand command = (AbstractConnectionCreateCommand) request.getStartCommand();
			command.setDestination((InterfaceEditPart) getHost());
			return command;
		}
		return null;
	}


	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		return createReconnectCommand(request);
	}

	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
		return createReconnectCommand(request);
	}

	protected FBNetwork getParentNetwork() {
		EditPart parent = getHost().getParent();
		while (parent != null && !(parent instanceof AbstractFBNetworkEditPart)) {
			parent = parent.getParent();
		}
		if (parent instanceof AbstractFBNetworkEditPart) { // also means that parent != null
			return ((AbstractFBNetworkEditPart) parent).getModel();
		}
		return null;
	}
	
	protected abstract Command createReconnectCommand(ReconnectRequest request);

}