/*******************************************************************************
 * Copyright (c) 2009 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.gef.editparts.IDeactivatableConnectionHandleRoleEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;

/**
 * This policy disables the connectionrole of an
 * IDeactivatableConnectionHandleRoleEditPart.
 */
public class DisableConnectionHandleRoleEditPolicy extends
		FeedbackConnectionEndpointEditPolicy {

	private IDeactivatableConnectionHandleRoleEditPart sourcePart;
	private IDeactivatableConnectionHandleRoleEditPart destPart;

	@Override
	protected void addSelectionHandles() {
		super.addSelectionHandles();
		if (getHost() instanceof ConnectionEditPart) {
			ConnectionEditPart cep = (ConnectionEditPart) getHost();

			EditPart source = cep.getSource();
			EditPart dest = cep.getTarget();
			if (source != null
					&& source instanceof IDeactivatableConnectionHandleRoleEditPart) {
				sourcePart = (InterfaceEditPart) source;
			}
			if (dest != null
					&& dest instanceof IDeactivatableConnectionHandleRoleEditPart) {
				destPart = (InterfaceEditPart) dest;
			}
			if (sourcePart != null && destPart != null) {
				sourcePart.setConnectionHandleRoleEnabled(false);
				destPart.setConnectionHandleRoleEnabled(false);
			}

		}
	}

	@Override
	protected void removeSelectionHandles() {
		super.removeSelectionHandles();
		if (sourcePart != null && destPart != null) {
			sourcePart.setConnectionHandleRoleEnabled(true);
			destPart.setConnectionHandleRoleEnabled(true);
			sourcePart = null;
			destPart = null;
		}
	}
}
