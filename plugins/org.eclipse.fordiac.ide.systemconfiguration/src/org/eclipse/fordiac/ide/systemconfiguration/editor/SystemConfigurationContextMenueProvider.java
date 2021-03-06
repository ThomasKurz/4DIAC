/*******************************************************************************
 * Copyright (c) 2012, 2016 TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.systemconfiguration.editor;

import org.eclipse.fordiac.ide.gef.ZoomUndoRedoContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

public class SystemConfigurationContextMenueProvider extends
		ZoomUndoRedoContextMenuProvider {

	public SystemConfigurationContextMenueProvider(EditPartViewer viewer,
			ZoomManager zoomManager, ActionRegistry registry) {
		super(viewer, zoomManager, registry);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		super.buildContextMenu(menu);
		IAction action;

		action = registry.getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);
		
		action = registry.getAction(GEFActionConstants.DIRECT_EDIT);
		if (action != null && action.isEnabled()) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}
	}

}
