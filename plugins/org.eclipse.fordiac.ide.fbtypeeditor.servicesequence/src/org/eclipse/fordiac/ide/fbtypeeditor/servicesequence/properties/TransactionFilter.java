/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.TransactionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.jface.viewers.IFilter;

public class TransactionFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if(toTest instanceof TransactionEditPart){
			return true;
		}
		if(toTest instanceof ServiceTransaction){
			return true;
		}
		return false;
	}

}
