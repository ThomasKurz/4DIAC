/*******************************************************************************
 * Copyright (c) 2014 -2106 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.properties;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.navigator.CommonNavigator;

public class SystemInfoSection extends TypeInfoSection {

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if((part instanceof CommonNavigator) && (input instanceof AutomationSystem)){
			return  SystemManager.INSTANCE.getCommandStack((AutomationSystem)input);
		}
		return null;
	}

	@Override
	protected LibraryElement getInputType(Object input) {
		if(input instanceof AutomationSystem){
			return (AutomationSystem) input;	
		}
		return null;
	}

}
