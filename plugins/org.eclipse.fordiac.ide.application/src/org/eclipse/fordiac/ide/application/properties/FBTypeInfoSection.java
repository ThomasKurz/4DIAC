/*******************************************************************************
 * Copyright (c) 2014, 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.gef.properties.TypeInfoSection;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;

public class FBTypeInfoSection extends TypeInfoSection {

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		return null;
	}

	@Override
	protected LibraryElement getInputType(Object input) {
		if(input instanceof FBEditPart){
			return ((FBEditPart) input).getModel().getType();	
		}
		return null;
	}

}
