/*******************************************************************************
 * Copyright (c) 2014, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.actions;

import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

public class CreateNewPlugAction extends CreateFromNewAdapterAction {
	public static final String ID = "CreateNewPlugAction"; //$NON-NLS-1$

	public CreateNewPlugAction(IWorkbenchPart part, FBType fbType) {
		super(part, fbType);
	}

	@Override
	public String getId() {
		return ID;
	}

	protected Command getCreationCommand(AdapterTypePaletteEntry adpEntry) {
		return new CreateInterfaceElementCommand(adpEntry.getAdapterType(), "", "", getFbType().getInterfaceList(), false, -1); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
