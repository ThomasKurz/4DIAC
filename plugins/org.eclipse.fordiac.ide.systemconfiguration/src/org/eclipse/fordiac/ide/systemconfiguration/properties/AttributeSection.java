/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Monika Wenger
 *      - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.AttributeChangeCommand;
import org.eclipse.fordiac.ide.model.commands.create.AttributeCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.AttributeDeleteCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AttributeSection extends AbstractSection {
	private TableViewer attributeViewer;
	private final String NAME = "name"; //$NON-NLS-1$
	private final String VALUE = "value"; //$NON-NLS-1$
	private final String COMMENT = "comment"; //$NON-NLS-1$
	private Button attributeNew;
	private Button attributeDelete;
	
	protected Device getInputType(Object input) {
		if(input instanceof DeviceEditPart){
			return ((DeviceEditPart) input).getModel();
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);	
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createInputInfoGroup(parent);
		createNewDeleteButton(parent);
	}
	
	private void createNewDeleteButton(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.NONE, SWT.FILL, false, true));
		attributeNew = getWidgetFactory().createButton(composite, "", SWT.PUSH);
		attributeNew.setToolTipText("create attribute");
		attributeNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));	
		attributeNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {	
				if(type instanceof ConfigurableObject){
					executeCommand(new AttributeCreateCommand((ConfigurableObject) type));
					attributeViewer.refresh();
				}
			}
		});
		attributeDelete = getWidgetFactory().createButton(composite, "", SWT.PUSH);
		attributeDelete.setToolTipText("delete selected attribute");
		attributeDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		attributeDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				executeCommand(new AttributeDeleteCommand((ConfigurableObject) type, (Attribute)((IStructuredSelection) attributeViewer.getSelection()).getFirstElement()));
				attributeViewer.refresh();
			}
		});
	}

	private void createInputInfoGroup(Composite parent) {		
		attributeViewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.FILL);
		GridData gridDataVersionViewer = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridDataVersionViewer.minimumHeight = 80;
		gridDataVersionViewer.widthHint = 400;
		attributeViewer.getControl().setLayoutData(gridDataVersionViewer);
		final Table table = attributeViewer.getTable();
		table.setLinesVisible(true);		
		table.setHeaderVisible(true);
		TableColumn column1 = new TableColumn(attributeViewer.getTable(), SWT.LEFT);
		column1.setText(NAME);
		TableColumn column2 = new TableColumn(attributeViewer.getTable(), SWT.LEFT);
		column2.setText(VALUE); 
		TableColumn column3 = new TableColumn(attributeViewer.getTable(), SWT.LEFT);
		column3.setText(COMMENT);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(30, 70));
		layout.addColumnData(new ColumnWeightData(50, 90));
		table.setLayout(layout);		
		attributeViewer.setContentProvider(new InputContentProvider());
		attributeViewer.setLabelProvider(new InputLabelProvider());		
		attributeViewer.setCellEditors(new CellEditor[] {new TextCellEditor(table), new TextCellEditor(table, SWT.MULTI | SWT.V_SCROLL), new TextCellEditor(table)});
		attributeViewer.setColumnProperties(new String[] {NAME, VALUE, COMMENT});
		attributeViewer.setCellModifier(new ICellModifier() {
			public boolean canModify(final Object element, final String property) {
				return true;
			}
			public Object getValue(final Object element, final String property) {
				switch (property) {
				case NAME:
					return ((Attribute) element).getName();
				case VALUE:
					return ((Attribute) element).getValue();	
				case COMMENT:
					return ((Attribute) element).getComment();
				default:
					return null;
				}
			}
			public void modify(final Object element, final String property, final Object value) {
				Attribute data = (Attribute)((TableItem) element).getData();
				AttributeChangeCommand cmd = null;
				switch (property) {
				case NAME:
					cmd = new AttributeChangeCommand(data, value.toString(), null, null);
					break;
				case VALUE:
					cmd = new AttributeChangeCommand(data, null, value.toString(), null);
					break;
				case COMMENT:
					cmd = new AttributeChangeCommand(data, null, null, value.toString());
					break;
				}
				executeCommand(cmd);
				attributeViewer.refresh(data);
			}
		});
	}
	
	@Override
	public void refresh() {
		super.refresh();
		if(null != type) {
			attributeViewer.setInput(getType());
		}
	}

	@Override
	protected EObject getType() {
		if(type instanceof Device){
			return (Device) type;
		}
		return null;
	}

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if(part instanceof DiagramEditorWithFlyoutPalette){
			return ((DiagramEditorWithFlyoutPalette)part).getCommandStack();
		}
		return null;
	}

	@Override
	protected void setInputCode() {	
		attributeViewer.setCellModifier(null);
	}
	
	@Override
	protected void setInputInit() {
		//nothing to do
	}
	
	public class InputContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			if(inputElement instanceof Device){
				return ((Device)inputElement).getAttributes().toArray();
			}
			return new Object[] {};
		}
	}
	
	public class InputLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof Attribute) {
				switch (columnIndex) {
				case 0:
					return ((Attribute) element).getName();
				case 1:
					return ((Attribute) element).getValue();
				case 2:
					return ((Attribute) element).getComment() != null ? ((Attribute) element).getComment() : ""; //$NON-NLS-1$
				default:
					break;
				}
			}
			return element.toString();
		}
	}
}