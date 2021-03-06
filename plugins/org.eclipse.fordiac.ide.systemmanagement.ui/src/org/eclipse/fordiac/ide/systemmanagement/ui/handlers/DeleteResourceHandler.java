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
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceDeleteCommand;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeleteResourceHandler extends AbstractHandler  {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(selection instanceof TreeSelection) {
			if(((TreeSelection) selection).getFirstElement() instanceof Resource) {
				Resource resouce = (Resource) ((TreeSelection) selection).getFirstElement();
				runDeleteAction(resouce);
			}
		}
		return null;
	}

	private static void runDeleteAction(Resource resource) {
		ResourceDeleteCommand cmd = new ResourceDeleteCommand(resource);
		AutomationSystem system = resource.getAutomationSystem();			
		org.eclipse.gef.commands.CommandStack commandStack = SystemManager.INSTANCE.getCommandStack(system);
		commandStack.execute(cmd);
	}

}
