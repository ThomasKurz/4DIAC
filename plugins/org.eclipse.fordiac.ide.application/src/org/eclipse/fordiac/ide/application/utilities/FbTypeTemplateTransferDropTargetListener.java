/*******************************************************************************
 * Copyright (c) 2008 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Michael Hofmann, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.util.dnd.TransferDataSelectionOfFb;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.swt.dnd.DND;

public abstract class FbTypeTemplateTransferDropTargetListener extends
		TemplateTransferDropTargetListener {

	AutomationSystem system;

	/**
	 * Constructs a listener on the specified viewer.
	 * 
	 * @param viewer
	 *            the EditPartViewer
	 */
	public FbTypeTemplateTransferDropTargetListener(
			final EditPartViewer viewer, AutomationSystem system) {
		super(viewer);
		this.system = system;
	}

	/**
	 * The purpose of a template is to be copied. Therefore, the Drop operation
	 * is set to <code>DND.DROP_COPY</code> by default.
	 * 
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDragOver()
	 */
	@Override
	protected void handleDragOver() {
		super.handleDragOver();
		getCurrentEvent().feedback = DND.FEEDBACK_SCROLL | DND.FEEDBACK_EXPAND;
		if (TemplateTransfer.getInstance().getTemplate() == null) {
			getCurrentEvent().detail = DND.DROP_NONE;
			getCurrentEvent().operations = DND.DROP_NONE;

		} else {
			if (TemplateTransfer.getInstance().getTemplate() instanceof FBTypePaletteEntry) {
				FBTypePaletteEntry entry = (FBTypePaletteEntry) TemplateTransfer
						.getInstance().getTemplate();
				AutomationSystem paletteSystem = entry.getGroup().getPallete()
						.getAutomationSystem();

				// If project is null it is an entry from the tool palette
				if ((((null == paletteSystem) && null == system))
						|| ((null != paletteSystem) && (null != system) && (system
								.equals(paletteSystem)))) {
					getCurrentEvent().detail = DND.DROP_COPY;
				} else {
					getCurrentEvent().detail = DND.DROP_NONE;
					getCurrentEvent().operations = DND.DROP_NONE;
				}
			}
		}
	}

	/**
	 * Overridden to select the created object.
	 * 
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDrop()
	 */
	@Override
	protected void handleDrop() {

		if (!(getCurrentEvent().data instanceof FBTypePaletteEntry)
				&& !(getCurrentEvent().data instanceof SubApplicationTypePaletteEntry)
				&& !(getCurrentEvent().data instanceof TransferDataSelectionOfFb[])) {
			// only allow FB type drops and of TransferDataSelectionOfFb -->
			// filter e.g. Folder Drops from Type Navigator
			return;
		}
		//
		super.handleDrop();
		TemplateTransfer.getInstance().setTemplate(null);
	}

}
