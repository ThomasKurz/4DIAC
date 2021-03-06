/*******************************************************************************
 * Copyright (c) 2014 - 2015 Luka Lednicki, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.plugin.popup.actions;

import org.eclipse.fordiac.ide.comgeneration.implementation.Analyzer;
import org.eclipse.fordiac.ide.comgeneration.implementation.CommFBGenerator;
import org.eclipse.fordiac.ide.comgeneration.implementation.CommFBGenerator.TransferedData;
import org.eclipse.fordiac.ide.comgeneration.implementation.CommunicationModel;
import org.eclipse.fordiac.ide.comgeneration.implementation.ProtocolSelector;
import org.eclipse.fordiac.ide.comgeneration.implementation.Utils;
import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.CanPubSubGenerator;
import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.EthernetPubSubGenerator;
import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.MediaSpecificGeneratorFactory;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class GenerateCommAction implements IObjectActionDelegate {
	private Shell shell;
	private Application selectedApplication = null;

	public GenerateCommAction() {
		super();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		String message;
		if (selectedApplication != null) {
			message = selectedApplication.getName();
			Palette palette = Utils.getPalette(selectedApplication);
			MediaSpecificGeneratorFactory specificGeneratorFactory = new MediaSpecificGeneratorFactory();
			EthernetPubSubGenerator ethernetPubSubGenerator = new EthernetPubSubGenerator(
					palette);
			ethernetPubSubGenerator.reset(61550);
			specificGeneratorFactory.addGenerator(ethernetPubSubGenerator);
			specificGeneratorFactory.addGenerator(new CanPubSubGenerator(
					palette));
			Analyzer analyzer = new Analyzer();
			CommunicationModel model = analyzer.analyze(selectedApplication);
			ProtocolSelector.doAutomatedProtocolSelection(model);
			CommFBGenerator generator = new CommFBGenerator(
					specificGeneratorFactory);
			generator.removeGeneratedElements(selectedApplication);
			generator.setTransferedData(TransferedData.EXACT);
			generator.generate(model);
		} else {
			message = "No application selected.";
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		selectedApplication = null;
		if (selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			Object selectedObject = structuredSelection.getFirstElement();
			if (selectedObject instanceof Application) {
				selectedApplication = (Application) selectedObject;
			}
		}
	}
}
