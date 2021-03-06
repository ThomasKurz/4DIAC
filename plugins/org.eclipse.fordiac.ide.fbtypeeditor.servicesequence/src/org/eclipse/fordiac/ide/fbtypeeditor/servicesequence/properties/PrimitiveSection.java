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
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveInterfaceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveParameterCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.InputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.OutputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.PrimitiveEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class PrimitiveSection extends AbstractServiceSection {

	protected Text eventText;	
	protected Text parametersText;
	protected Combo serviceInterfaceCombo;
	private Button buttonNone;
	private Button buttonTrue;
	private Button buttonFalse;
	private Group qiGroup;
	
	protected PrimitiveEditPart editPart;
	
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);	
		createEventSection(leftComposite);
		createPrimitiveSection(leftComposite);
		createQISection(rightComposite);
	}

	protected void createEventSection(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));		
		getWidgetFactory().createCLabel(composite, "Event: ");
		eventText = createGroupText(composite, true);	
		eventText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangePrimitiveEventCommand(getType(), eventText.getText()));
				setRadioButton();
				addContentAdapter();
			}
		});
	}
	
	protected void createQISection(Composite parent) {
		qiGroup = getWidgetFactory().createGroup(parent, "QI");	
		qiGroup.setLayout(new RowLayout(SWT.VERTICAL));
		qiGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
	    buttonNone = getWidgetFactory().createButton(qiGroup, "none", SWT.RADIO);
	    buttonNone.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				changeEventText("none");
				refresh();
			}
		});
	    buttonTrue = getWidgetFactory().createButton(qiGroup, "true", SWT.RADIO);
	    buttonTrue.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				changeEventText("true");
				refresh();
			}
		});
	    buttonFalse = getWidgetFactory().createButton(qiGroup, "false", SWT.RADIO);
	    buttonFalse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				changeEventText("false");
				refresh();
			}
		});
	    qiGroup.setVisible(false);
	}
	
	private void changeEventText(String button){
		if(null != editPart){
			String event = getType().getEvent().replace("+", "").replace("-", "");
		switch(button){
			case "true": 
				editPart.getNameLabel().setText(event + "+");
				executeCommand(new ChangePrimitiveEventCommand(getType(),event + "+"));
				break;
			case "false":
				editPart.getNameLabel().setText(event + "-");
				executeCommand(new ChangePrimitiveEventCommand(getType(),event + "-"));
				break;
			default:
				editPart.getNameLabel().setText(event);
				executeCommand(new ChangePrimitiveEventCommand(getType(),event));
				break;
		}
		}
	}
	
	protected void createPrimitiveSection(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Interface: ");
		serviceInterfaceCombo = new Combo(composite, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		serviceInterfaceCombo.setLayoutData(new GridData(SWT.FILL, 0, true, false));	
		serviceInterfaceCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				executeCommand(new ChangePrimitiveInterfaceCommand((Service) getType().eContainer().eContainer().eContainer(), getType(), serviceInterfaceCombo.getText()));
				refresh();
			}
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}
		});
		getWidgetFactory().createCLabel(composite, "Parameters:"); 
		parametersText = createGroupText(composite, true);	
		parametersText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangePrimitiveParameterCommand(getType(), parametersText.getText()));
				addContentAdapter();
			}
		});
	}
	
	protected Primitive getInputType(Object input) {
		if(input instanceof InputPrimitiveEditPart || input instanceof OutputPrimitiveEditPart){
			editPart = (PrimitiveEditPart) input;
			return ((PrimitiveEditPart) input).getCastedModel();
		}
		if(input instanceof InputPrimitive || input instanceof OutputPrimitive){
			IEditorPart editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
			GraphicalViewer view = editor.getAdapter(GraphicalViewer.class);
			editPart = (PrimitiveEditPart) view.getEditPartRegistry().get(input);
			return ((Primitive) input);
		}
		return null;
	}
	
	protected void setInputCode() {
		parametersText.setEnabled(false);
		serviceInterfaceCombo.removeAll();
		eventText.setEnabled(false);
	}	
	
	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if(null != type) {
			parametersText.setText(getType().getParameters() != null ? getType().getParameters() : "");
			setServiceInterfaceDropdown();
			eventText.setText(getType().getEvent());
			if(containsQI()){
				setRadioButton();
				if(type instanceof OutputPrimitive){
					qiGroup.setText("QO");
				}else{
					qiGroup.setText("QI");
				}
				qiGroup.setVisible(true);
			}
		}
		commandStack = commandStackBuffer;
	}
	
	protected Primitive getType(){
		return (Primitive)type;
	}
	
	protected FBType getFB(){
		return (FBType) getType().eContainer().eContainer().eContainer().eContainer();
	}
	
	protected boolean containsQI(){
		if(type instanceof InputPrimitive){
			for (VarDeclaration var : getFB().getInterfaceList().getInputVars()) {
				if(var.getName().equals("QI")){
					return true;
				}
			}
		}else if(type instanceof OutputPrimitive){
			for (VarDeclaration var : getFB().getInterfaceList().getOutputVars()) {
				if(var.getName().equals("QO")){
					return true;
				}
			}
		}
		return false;
	}
		
	public void setRadioButton(){
		if(getType().getEvent().endsWith("+")){
			buttonTrue.setSelection(true);
			buttonFalse.setSelection(false);
			buttonNone.setSelection(false);
		}else{
			if(getType().getEvent().endsWith("-")){
				buttonFalse.setSelection(true);
				buttonNone.setSelection(false);
				buttonTrue.setSelection(false);
			}else{
				buttonNone.setSelection(true);
				buttonFalse.setSelection(false);
				buttonTrue.setSelection(false);
			}
		}
	}
	
	protected boolean isLeftInterfaceSelected(){
		if(serviceInterfaceCombo.getText().equals(serviceInterfaceCombo.getItem(0))){
			return true;
		}
		return false;
	}
	
	public void setServiceInterfaceDropdown(){
		serviceInterfaceCombo.removeAll();
		Service s = (Service)getType().eContainer().eContainer().eContainer();
		serviceInterfaceCombo.add(s.getLeftInterface().getName());
		serviceInterfaceCombo.add(s.getRightInterface().getName());
		if(serviceInterfaceCombo.getItem(0).equals(getType().getInterface().getName())){
			serviceInterfaceCombo.select(0);
		}else{
			serviceInterfaceCombo.select(1);
		}
	}

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		return super.getCommandStack(part);
	}

	@Override
	protected void setInputInit() {
	}
}
