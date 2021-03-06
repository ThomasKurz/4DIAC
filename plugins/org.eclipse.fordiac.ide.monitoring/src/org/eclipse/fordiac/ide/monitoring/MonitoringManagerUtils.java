/*******************************************************************************
 * Copyright (c) 2012 - 2016 Profactor GmbH, AIT, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Filip Andren, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeNetworkViewerEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory;
import org.eclipse.fordiac.ide.model.monitoring.PortElement;

public class MonitoringManagerUtils {
	
	private MonitoringManagerUtils() {
		throw new AssertionError();  //class should not be instantiated
	}

	public static boolean canBeMonitored(org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart editPart) {
		PortElement port = MonitoringManagerUtils.createPortElement(editPart);  //FIXME think how we can get away without creating a port element 
		return ((port != null) && (port.getPortString() != null));
	}
	
	public static boolean canBeMonitored(FBEditPart obj) {
		// As a first solution try to find the first interface editpart and see if we can monitoring
		for (Object child : obj.getChildren()) {
			if(child instanceof InterfaceEditPart){
				return canBeMonitored((InterfaceEditPart)child);
			}
		}
		return false;
	}

	public static PortElement createPortElement(org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart editPart) {
		if (editPart.getParent() instanceof FBEditPart
				&& editPart.getParent().getParent() instanceof CompositeNetworkViewerEditPart) {
			return createCompositeInternalPortString(editPart);
		}

		FBNetworkElement obj = editPart.getModel().getFBNetworkElement();
		if(obj instanceof FB){
			FB fb = (FB)obj; 
			return createPortElement(fb, editPart);					
		}
		
		return null;

	}

	private static PortElement createPortElement(FB fb,
			org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart ep) {
		Resource res = findResourceForFB(fb);
		if (res == null) {
			return null;
		}
		Device dev = (Device) res.eContainer();
		if (dev == null) {
			return null;
		}
		
		AutomationSystem system = dev.getAutomationSystem();

		PortElement p;
		if (ep.getModel() instanceof AdapterDeclaration){
			p = MonitoringFactory.eINSTANCE.createAdapterPortElement(); 
		}
		else{
			p = MonitoringFactory.eINSTANCE.createPortElement();
		}
		p.setSystem(system);
		p.setDevice(dev);
		p.setResource(res);
		p.setFb(fb);
		p.setInterfaceElement(ep.getModel());
		return p;
	}

	private static PortElement createCompositeInternalPortString(
			org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart editPart) {
		
		FBEditPart fbep = (FBEditPart)editPart.getParent();
		CompositeNetworkViewerEditPart cnep = (CompositeNetworkViewerEditPart) editPart
				.getParent().getParent();

		ArrayList<CompositeNetworkViewerEditPart> parents = new ArrayList<>();

		CompositeNetworkViewerEditPart root = cnep; 
		parents.add(0, root);
		while (root.getparentInstanceViewerEditPart() != null) {
			parents.add(0, root.getparentInstanceViewerEditPart());
			root = root.getparentInstanceViewerEditPart();
		}

		FB fb = root.getFbInstance();
		PortElement pe = createPortElement(fb, editPart);
		if (pe != null) {
			pe.setFb(fbep.getModel());

			for (CompositeNetworkViewerEditPart compositeNetworkEditPart : parents) {
				pe.getHierarchy().add(
						compositeNetworkEditPart.getFbInstance().getName());
			}
			return pe;
		}
		return null;
	}

	private static Resource findResourceForFB(FB fb) {
// TODO  model refacoring - reimplement when subapp mapping model is finished and if needed	
//		EObject container = fb.eContainer();		
//		if(container instanceof ResourceFBNetwork){
//			//we have a resource FB
//			return (ResourceFBNetwork)container;
//		}
//
//		while (!(container instanceof FBNetwork)) {
//			if (container instanceof SubAppNetwork) {
//				ResourceFBNetwork resourceNetwork = ((SubAppNetwork) container)
//						.getParentSubApp().getResource();
//				if (resourceNetwork != null) {
//					return resourceNetwork;
//				}
//				container = ((SubAppNetwork) container).getParentSubApp()
//						.eContainer();
//			}
//		}
		
		return fb.getResource();
	}

	
}
