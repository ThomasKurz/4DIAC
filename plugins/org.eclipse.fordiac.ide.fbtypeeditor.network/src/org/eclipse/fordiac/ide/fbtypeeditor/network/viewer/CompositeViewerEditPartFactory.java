/*******************************************************************************
 * Copyright (c) 2013 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.GraphicalEditor;

/**
 * A factory for creating ECCEditPart objects.
 */
class CompositeViewerEditPartFactory extends CompositeNetworkEditPartFactory {

	private FB fbInstance;
	private FBEditPart fbEditPart;
	

	public CompositeViewerEditPartFactory(GraphicalEditor editor, FB fbInstance, FBEditPart fbEditPart, ZoomManager zoomManager) {
		super(editor, (CompositeFBType)fbInstance.getType(), zoomManager);
		this.fbInstance = fbInstance;
		this.fbEditPart = fbEditPart;
	}
	
	/**
	 * Maps an object to an EditPart.
	 * 
	 * @param context
	 *          the context
	 * @param modelElement
	 *          the model element
	 * 
	 * @return the part for element
	 * 
	 * @throws RuntimeException
	 *           if no match was found (programming error)
	 */
	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {

		if (modelElement instanceof FBNetwork) {
			CompositeNetworkViewerEditPart compositeNetEP = new CompositeNetworkViewerEditPart();
			compositeNetEP.setFbInstance(fbInstance);
			if (fbEditPart.getParent() instanceof CompositeNetworkViewerEditPart) {
				compositeNetEP.setparentInstanceViewerEditPart((CompositeNetworkViewerEditPart)fbEditPart.getParent());	
			}
			return compositeNetEP;

		} 
		if (modelElement instanceof IInterfaceElement) {
			IInterfaceElement iElement = (IInterfaceElement)modelElement;
			if(iElement.eContainer().eContainer() instanceof CompositeFBType){
				return new CompositeInternalInterfaceEditPart();
			}
		}
		
		return super.getPartForElement(context, modelElement);
	}

}
