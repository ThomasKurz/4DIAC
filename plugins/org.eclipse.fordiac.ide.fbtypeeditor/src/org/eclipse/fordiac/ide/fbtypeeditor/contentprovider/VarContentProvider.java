/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.contentprovider;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.IStructuredContentProvider;

public class VarContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		ArrayList<VarDeclaration> vars = new ArrayList<VarDeclaration>();
		if(inputElement instanceof IInterfaceElement){
			IInterfaceElement ielem = (IInterfaceElement)inputElement;
			FBType fbtype = (FBType) ielem.eContainer().eContainer();
			// filter adapter elements as the are not allowed to be connected by with
			if(ielem.isIsInput()){
				for (VarDeclaration var : fbtype.getInterfaceList().getInputVars()) {
					if (!(var.getType() instanceof AdapterType)) {
						vars.add(var);
					}
				}				
			}else{
				for (VarDeclaration var : fbtype.getInterfaceList().getOutputVars()) {
					if (!(var.getType() instanceof AdapterType)) {
						vars.add(var);
					}
				}
			}
		}
		return vars.toArray();
	}
}
