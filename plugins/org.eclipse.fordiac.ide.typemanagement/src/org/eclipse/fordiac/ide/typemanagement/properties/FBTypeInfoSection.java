/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.typemanagement.properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.gef.properties.CompilableTypeInfoSection;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;

public class FBTypeInfoSection extends CompilableTypeInfoSection {

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		return null;
	}

	@Override
	protected LibraryElement getInputType(Object input) {
		if(input instanceof IFile){
			IFile file = ((IFile) input);
			return (TypeLibrary.getPaletteGroup( SystemManager.INSTANCE.getPalette(file.getProject()), file.getParent()))
					.getEntry(TypeLibrary.getTypeNameFromFileName(file.getName())).getType();
		}
		return null;
	}
}
