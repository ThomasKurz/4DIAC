/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IEditorPart;

public class ChangeValueCommand extends Command {

	private static final String CHANGE = Messages.ChangeValueCommand_LABEL_ChangeValue;
	private VarDeclaration var;
	private VarDeclaration mirroredVar;  //the variable of the mapped entity
	private String newValue, oldValue;
	private IEditorPart editor;

	public ChangeValueCommand(VarDeclaration var, String value){
		this.var = var;
		newValue = value;
	}
	
	@Override
	public boolean canExecute(){
		if (var != null && var.getType() != null && var.getType().equals(DataTypeLibrary.getInstance().getType("ANY")) && null != newValue) { //$NON-NLS-1$
			if ((!newValue.equals("")) && (!newValue.contains("#"))){ //$NON-NLS-1$ //$NON-NLS-2$
				Abstract4DIACUIPlugin.statusLineErrorMessage("Constant Values are not allowed on ANY Input!"); //$NON-NLS-1$
				return false;
			}
		}
		return super.canExecute();
	}

	@Override
	public boolean canUndo() {
		return editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());

	}

	public ChangeValueCommand() {
		super(CHANGE);
	}

	@Override
	public void execute() {
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
		mirroredVar = getMirroredVariable();
		if(var.getValue() == null){
			var.setValue(LibraryElementFactory.eINSTANCE.createValue());
			if(null != mirroredVar){
				mirroredVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
			oldValue = ""; //$NON-NLS-1$
		}else{
			oldValue = var.getValue().getValue() != null ? var.getValue().getValue() : ""; //$NON-NLS-1$
		}		
		if ("".equals(newValue)) { //$NON-NLS-1$
			newValue = null;
		}
		var.getValue().setValue(newValue);
		setMirroredVar(newValue);
	}

	@Override
	public void undo() {
		var.getValue().setValue(oldValue);
		setMirroredVar(oldValue);
	}

	@Override
	public void redo() {
		var.getValue().setValue(newValue);
		setMirroredVar(newValue);
	}
	
	private VarDeclaration getMirroredVariable() {
		if(null != var.getFBNetworkElement() && var.getFBNetworkElement().isMapped()){
			FBNetworkElement opposite =  var.getFBNetworkElement().getOpposite();
			if(null != opposite){
				IInterfaceElement element = opposite.getInterfaceElement(var.getName());
				if(element instanceof VarDeclaration){
					return (VarDeclaration)element;
				}
			}
		}
		return null;
	}
	
	private void setMirroredVar(String val) {
		if(null != mirroredVar){
			mirroredVar.getValue().setValue(val);
		}
	}
}