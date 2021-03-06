/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.ui.IEditorInput;

public class NewSubAppCommand extends AbstractCreateFBNetworkElementCommand {
	/** The input for reopening subApp. */
	private IEditorInput input = null;
	private List<?> selection; 
	private HashMap<IInterfaceElement, IInterfaceElement> connectionModifiers;
	List<List<?>> modifyConnnection;
	boolean createConnection;
	
	public NewSubAppCommand(FBNetwork fbNetwork, List<?> selection, int x, int y) {
		super(fbNetwork, x, y);
		this.selection = selection;
		element = LibraryElementFactory.eINSTANCE.createSubApp();
		element.setInterface(getTypeInterfaceList());
	}
	
	@Override
	public void execute() {
		getSubApp().setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		connectionModifiers = new HashMap<IInterfaceElement, IInterfaceElement>();
		modifyConnnection = new ArrayList<List<?>>();
		super.execute();
		//redo is called by super class
	}
	
	@Override
	public void redo() {
		super.redo();
		for(Object ne : selection){
			if(ne instanceof FBEditPart){
				getSubApp().getSubAppNetwork().getNetworkElements().add(((FBEditPart)ne).getModel());
				createConnection(((FBEditPart)ne).getModel(), true);
			}
		}
		openClosedEditor();
	}

	@Override
	public void undo() {
		super.undo();
		for(Object ne : selection){
			if(ne instanceof FBEditPart){
				fbNetwork.getNetworkElements().add(((FBEditPart)ne).getModel());
				createConnection(((FBEditPart)ne).getModel(), false);
			}
		}
		element.setInterface(getTypeInterfaceList());
		connectionModifiers.clear();
		modifyConnnection.clear();
		closeOpenedSubApp();
	}
	
	
	private void moveConnection(Connection con, boolean isRedo){
		if(con instanceof EventConnection){
			if(isRedo){
				int index = fbNetwork.getEventConnections().indexOf(con);
				if(index > -1){
					getSubApp().getSubAppNetwork().addConnection(fbNetwork.getEventConnections().get(index));
				}
			}else{
				int index = getSubApp().getSubAppNetwork().getEventConnections().indexOf(con);
				if(index > -1){
					fbNetwork.addConnection(getSubApp().getSubAppNetwork().getEventConnections().get(index));
				}
			}
		}
		if(con instanceof DataConnection){
			if(isRedo){
				int index = fbNetwork.getDataConnections().indexOf(con);
				if(index > -1){
					getSubApp().getSubAppNetwork().addConnection(fbNetwork.getDataConnections().get(index));
				}
			}else{
				int index = getSubApp().getSubAppNetwork().getDataConnections().indexOf(con);
				if(index > -1){
					fbNetwork.addConnection(getSubApp().getSubAppNetwork().getDataConnections().get(index));
				}
			}
		}
		if(con instanceof AdapterConnection){
			if(isRedo){
				int index = fbNetwork.getAdapterConnections().indexOf(con);
				if(index > -1){
					getSubApp().getSubAppNetwork().addConnection(fbNetwork.getAdapterConnections().get(index));
				}
			}else{
				int index = getSubApp().getSubAppNetwork().getAdapterConnections().indexOf(con);
				if(index > -1){
					fbNetwork.addConnection(getSubApp().getSubAppNetwork().getAdapterConnections().get(index));
				}
			}
		}
	}
	
	private void createConnection(FBNetworkElement ne, boolean isRedo) {
		for(IInterfaceElement ie : ne.getInterface().getAllInterfaceElements()){
			if(ie.isIsInput()){
				for(Connection con : ie.getInputConnections()){
					if(isContained(con.getSourceElement())){
						moveConnection(con, isRedo);
					}else{
						if(isRedo){
							IInterfaceElement newie = addInterfaceElement(con.getDestination());
							connectionModifiers.put(newie, con.getDestination());
							modifyConnnection.add(markConnectionForModification(con, false, newie));
							if(createConnection){
								modifyConnnection.add(markConnectionForModification(EcoreUtil.copy(con), newie, con.getDestination()));
							}
						}
					}
				}
			}else{
				for(Connection con : ie.getOutputConnections()){
					if(isContained(con.getDestinationElement())){
						moveConnection(con, isRedo);
					}else{
						if(isRedo){
							IInterfaceElement newie = addInterfaceElement(con.getSource());
							connectionModifiers.put(newie, con.getSource());
							modifyConnnection.add(markConnectionForModification(con, true, newie));
							if(createConnection){
								modifyConnnection.add(markConnectionForModification(EcoreUtil.copy(con), con.getSource(), newie));
							}
						}
					}
				}
			}
		}
		for(List<?> list : modifyConnnection){
			if(isRedo){
				if(list.get(1) instanceof Boolean){
					if((boolean) list.get(1)){
						((Connection)list.get(0)).setSource((IInterfaceElement) list.get(2));
					}else{
						((Connection)list.get(0)).setDestination((IInterfaceElement) list.get(2));
					}
				}else{
					((Connection)list.get(0)).setSource((IInterfaceElement) list.get(1));
					((Connection)list.get(0)).setDestination((IInterfaceElement) list.get(2));
				}
				if(isSubappInternalConnection((Connection)list.get(0))){
					getSubApp().getSubAppNetwork().addConnection((Connection)list.get(0));					
				}
			}else{
				if(list.get(1) instanceof Boolean){
					if((boolean) list.get(1)){
						((Connection)list.get(0)).setSource(connectionModifiers.get(list.get(2)));
					}else{
						((Connection)list.get(0)).setDestination(connectionModifiers.get(list.get(2)));
					}					
				}else{
					((IInterfaceElement) list.get(1)).getOutputConnections().remove(list.get(0));
					((IInterfaceElement) list.get(2)).getInputConnections().remove(list.get(0));
				}
			}
		}
	}
	
	private ArrayList<?> markConnectionForModification(Connection connection, Object modifySource, IInterfaceElement iie){
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(connection);
		list.add(modifySource);
		list.add(iie);
		return list;
	}
	
	private boolean isSubappInternalConnection(Connection con){
		if((con.getSourceElement().equals(element) || getSubApp().getSubAppNetwork().getNetworkElements().contains(con.getSourceElement())) && 
				(con.getDestinationElement().equals(element) || getSubApp().getSubAppNetwork().getNetworkElements().contains(con.getDestinationElement()))){
			return true;
		}
		return false;
	}
	
	private boolean isContained(FBNetworkElement e) {
		for(Object ne : selection){
			if(ne instanceof FBEditPart && ((FBEditPart)ne).getModel().equals(e)){
				return true;
			}
		}
		return false;
	}

	private IInterfaceElement addInterfaceElement(IInterfaceElement ie){
		IInterfaceElement newie = EcoreUtil.copy(ie);
		newie.setName(ie.getFBNetworkElement().getName() + "_" + ie.getName()); //$NON-NLS-1$
		if(null == getSubApp().getInterfaceElement(newie.getName())){
			createConnection = true;
			if(ie instanceof Event){
				((Event) newie).getWith().clear();
				if(ie.isIsInput()){
					getSubApp().getInterface().getEventInputs().add((Event) newie);
				}else{
					getSubApp().getInterface().getEventOutputs().add((Event) newie);
				}
				return newie;
			}
			if(ie instanceof VarDeclaration){
				if(ie.isIsInput()){
					getSubApp().getInterface().getInputVars().add((VarDeclaration) newie);
				}else{
					getSubApp().getInterface().getOutputVars().add((VarDeclaration) newie);
				}
				return newie;
			}
			if(ie instanceof AdapterDeclaration){
				if(ie.isIsInput()){
					getSubApp().getInterface().getSockets().add((AdapterDeclaration) newie);
				}else{
					getSubApp().getInterface().getPlugs().add((AdapterDeclaration) newie);
				}
				return newie;
			}
		}else{
			createConnection = false;
			return getSubApp().getInterfaceElement(newie.getName());
		}
		return newie;
	}
	
	@Override
	protected InterfaceList getTypeInterfaceList() {
		return LibraryElementFactory.eINSTANCE.createInterfaceList();
	}
	
	@Override
	protected String getInitalInstanceName() {
		return "SubApp"; //$NON-NLS-1$
	}
	
	private void closeOpenedSubApp() {
		input = CommandUtil.closeOpenedSubApp(getSubApp().getSubAppNetwork());
	}

	private SubApp getSubApp() {
		return (SubApp)element;
	}

	private void openClosedEditor() {
		if (input != null) {
			CommandUtil.openSubAppEditor(input);
		}
	}
}
