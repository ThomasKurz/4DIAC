/**
 * *******************************************************************************
 *  * Copyright (c) 2007 - 2011 4DIAC - consortium.
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;


public class InternalVarsItemProvider  extends TransientBasicFBTypeListItemProvider{
	
	public InternalVarsItemProvider(AdapterFactory adapterFactory,
			BasicFBType basicFBType) {
		super(adapterFactory, basicFBType);
	}
	
	@Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object){
      if (childrenFeatures == null)
      {
        super.getChildrenFeatures(object);
        childrenFeatures.add(LibraryElementPackage.Literals.BASIC_FB_TYPE__INTERNAL_VARS);
      }
      return childrenFeatures;
    }

    @Override
    public String getText(Object object){
      return "Internal Variables";
    }
    
    @Override
    public Object getImage(Object object) {
      return overlayImage(object, FordiacImage.ICON_Variables.getImage());      
    }

    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
    {
      super.collectNewChildDescriptors(newChildDescriptors, object);
      newChildDescriptors.add
		(createChildParameter
			(LibraryElementPackage.Literals.BASIC_FB_TYPE__INTERNAL_VARS,
			 LibraryElementFactory.eINSTANCE.createVarDeclaration()));
    }

}
