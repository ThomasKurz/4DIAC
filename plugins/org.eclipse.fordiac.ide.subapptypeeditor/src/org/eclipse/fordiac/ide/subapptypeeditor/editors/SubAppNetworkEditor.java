/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.subapptypeeditor.editors;

import org.eclipse.fordiac.ide.fbtypeeditor.network.CompositeNetworkEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPartFactory;
import org.eclipse.fordiac.ide.subapptypeeditor.editparts.SubAppTypeNetworkEditPartFactory;
import org.eclipse.ui.IEditorInput;

public class SubAppNetworkEditor extends CompositeNetworkEditor{

	@Override
	protected void setModel(IEditorInput input) {
		super.setModel(input);
		setPartName("Subapplication Network");
	}
	
	
	@Override
	protected CompositeNetworkEditPartFactory getEditPartFactory() {
		return new SubAppTypeNetworkEditPartFactory(this, fbType, getZoomManger());
	}
}
