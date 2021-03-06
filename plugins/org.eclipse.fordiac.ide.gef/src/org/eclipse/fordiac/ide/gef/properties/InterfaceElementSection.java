/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH 
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
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InterfaceElementSection extends AbstractSection {
	protected Text nameText;
	protected Text commentText;
	protected Combo typeCombo;
	protected Text parameterText;
	protected CLabel valueCLabel;
	
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(1, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createTypeAndCommentSection(parent);
	}
	
	protected void createTypeAndCommentSection(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Name:"); 
		nameText = createGroupText(composite, true);	
		nameText.addVerifyListener(new IdentifierVerifyListener());
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
				addContentAdapter();
			}
		});
		getWidgetFactory().createCLabel(composite, "Comment:"); 
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
				addContentAdapter();
			}
		});
		getWidgetFactory().createCLabel(composite, "Type: ");
		typeCombo = new Combo(composite, SWT.SINGLE | SWT.READ_ONLY);
		typeCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				Command cmd = null;
				if(getType() instanceof AdapterDeclaration) {
					DataType newType = getTypeForSelection(typeCombo.getText());			
					cmd = new ChangeTypeCommand((VarDeclaration) getType(), newType);
				}else {
					if(getType() instanceof VarDeclaration) {
						cmd = new ChangeTypeCommand((VarDeclaration) getType(), DataTypeLibrary.getInstance().getType(typeCombo.getText()));
					}					
				}
				executeCommand(cmd);
			}
			private DataType getTypeForSelection(String text) {
				for (AdapterTypePaletteEntry adaptertype : getAdapterTypes(getType().getFBNetworkElement().getFbNetwork().getApplication().getAutomationSystem().getPalette())){
					if(adaptertype.getAdapterType().getName().equals(text)) {
						return adaptertype.getAdapterType();
					}
				}
				return null;
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		valueCLabel = getWidgetFactory().createCLabel(composite, "Value:"); 
		parameterText = createGroupText(composite, true);
		parameterText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeValueCommand((VarDeclaration) getType(), parameterText.getText()));
				addContentAdapter();
			}
		});
	}
	
	protected void fillTypeCombo(String text) {
		typeCombo.removeAll();
		if(getType() instanceof Event) {
			for(DataType dataType : EventTypeLibrary.getInstance().getEventTypes()){
				typeCombo.add(dataType.getName());
			}			
		}else {
			if(getType() instanceof AdapterDeclaration) {
				if(null != getType()) {
					for (AdapterTypePaletteEntry adaptertype : getAdapterTypes(getType().getFBNetworkElement().getFbNetwork().getApplication().getAutomationSystem().getPalette())){
						typeCombo.add(adaptertype.getAdapterType().getName());
					}
				}
			}else {
				if(getType() instanceof VarDeclaration) {
					for(DataType dataType : DataTypeLibrary.getInstance().getDataTypesSorted()){
						typeCombo.add(dataType.getName());
					}
				}
			}
		}
		int i = typeCombo.getItems().length - 1;
	    while (!text.equals(typeCombo.getItems()[i]) && i > 0){
	        	--i;
	        } 
		typeCombo.select(i);					
	}

	private static ArrayList<AdapterTypePaletteEntry> getAdapterTypes(final Palette systemPalette){
		ArrayList<AdapterTypePaletteEntry> retVal = new ArrayList<AdapterTypePaletteEntry>();		
		Palette pal = systemPalette;
		if(null == pal){
			pal = TypeLibrary.getInstance().getPalette();
		}			
		retVal.addAll(getAdapterGroup(pal.getRootGroup()));		
		return retVal;
	}
	
	private static ArrayList<AdapterTypePaletteEntry> getAdapterGroup(final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group){
		ArrayList<AdapterTypePaletteEntry> retVal = new ArrayList<AdapterTypePaletteEntry>();	
		for (Iterator<PaletteGroup> iterator = group.getSubGroups().iterator(); iterator.hasNext();) {
			PaletteGroup paletteGroup = iterator.next();
			retVal.addAll(getAdapterGroup(paletteGroup));		
		}		
		retVal.addAll(getAdapterGroupEntries(group));		
		return retVal;
	}
	
	private static ArrayList<AdapterTypePaletteEntry> getAdapterGroupEntries(final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group){
		ArrayList<AdapterTypePaletteEntry> retVal = new ArrayList<AdapterTypePaletteEntry>();	
		for (PaletteEntry entry : group.getEntries()) {
			if(entry instanceof AdapterTypePaletteEntry){
				retVal.add((AdapterTypePaletteEntry) entry);				
			}
		}
		return retVal;
	}
	
	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if(null != type) {
			if(getType().getFBNetworkElement() instanceof SubApp){
				nameText.setEditable(true);
				nameText.setEnabled(true);
				commentText.setEditable(true);
				commentText.setEnabled(true);
				if(getType().getInputConnections().isEmpty() && getType().getOutputConnections().isEmpty()){
					typeCombo.setEnabled(true);
				}else {
					typeCombo.setEnabled(false);
				}
			}else{
				nameText.setEditable(false);
				nameText.setEnabled(false);
				commentText.setEditable(false);
				commentText.setEnabled(false);
				typeCombo.setEnabled(false);
			}
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : "");			 //$NON-NLS-1$
			String itype = "";
			if(type instanceof VarDeclaration){
				itype = ((VarDeclaration)getType()).getType() != null ? ((VarDeclaration)getType()).getType().getName() : "";
				if(getType().isIsInput()){
					parameterText.setVisible(true);
					valueCLabel.setVisible(true);
					parameterText.setText(getType().getValue() != null && getType().getValue().getValue() != null ? getType().getValue().getValue() : ""); //$NON-NLS-1$
				}else{
					valueCLabel.setVisible(false);
					parameterText.setVisible(false);
				}
			}else{
				itype = "Event";
				valueCLabel.setVisible(false);
				parameterText.setVisible(false);
			}
			fillTypeCombo(itype);
		}
		commandStack = commandStackBuffer;
	}
	
	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if(part instanceof DiagramEditorWithFlyoutPalette){
			return ((DiagramEditorWithFlyoutPalette)part).getCommandStack();
		}
		return null;
	}

	protected IInterfaceElement getInputType(Object input) {
		if(input instanceof InterfaceEditPart){
			return ((InterfaceEditPart) input).getModel();
		}
		return null;
	}

	protected IInterfaceElement getType() {
		return (IInterfaceElement)type;
	}

	@Override
	protected void setInputInit() {
	}

	@Override
	protected void setInputCode() {
	}	
}
