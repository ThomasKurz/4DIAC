/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;

class SystemElementItemProviderAdapterFactory extends LibraryElementItemProviderAdapterFactory {

	@Override
	public Adapter createApplicationAdapter() {
		if (applicationItemProvider == null) {
			applicationItemProvider = new ApplicationItemProviderForSystem(this);
		}
		return applicationItemProvider;
	}

	@Override
	public Adapter createSubAppAdapter() {
		if (subAppItemProvider == null) {
			subAppItemProvider = new SubAppItemProviderForSystem(this);
		}
		return subAppItemProvider;
	}

	@Override
	public Adapter createFBAdapter() {
		if (fbItemProvider == null) {
			fbItemProvider = new FBItemProvider(this){
				
				/** we are not showing the real parent of FBs (i.e., FBNetwork or SubAppNetwork)
				 *  in the tree. In order to ensure correct CNF behavior we need to provide a special getparent
				 */
				@Override
				public Object getParent(Object object) {
					
					EObject cont = ((FB)object).eContainer();
					if(cont instanceof FBNetwork){
						return ((FBNetwork)cont).eContainer();
					}
					return super.getParent(object);
				}
			};
		}
		return fbItemProvider;
	}
	
	
	
}