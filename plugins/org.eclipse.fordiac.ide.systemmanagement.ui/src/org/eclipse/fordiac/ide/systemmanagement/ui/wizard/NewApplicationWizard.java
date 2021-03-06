/*******************************************************************************
 * Copyright (c) 2009 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.fordiac.ide.application.commands.NewAppCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.ui.Activator;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.util.IOpenListener;
import org.eclipse.fordiac.ide.util.OpenListenerManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

/**
 * The Class NewApplicationWizard.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class NewApplicationWizard extends Wizard implements INewWizard {

	/** The Constant NEW_IEC61499_APP_DESC. */
	private static final String NEW_IEC61499_APP_DESC = Messages.NewApplicationWizard_Description;

	/** The Constant NEW_IEC61499_APPLICATION. */
	private static final String NEW_IEC61499_APPLICATION = Messages.NewApplicationWizard_Title;

	/** The page. */
	protected NewApplicationPage page;

	private AutomationSystem system;

	/**
	 * Instantiates a new new application wizard.
	 */
	public NewApplicationWizard() {
		setWindowTitle(Messages.NewApplicationWizard_Title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem system = page.getSelectedSystem();
		
		NewAppCommand cmd = getApplicationCreateCommand(system);
				
		//TODO check how to get the command stack here
		//getCommandStack().execute(cmd);
		
		IWorkbench workbench = PlatformUI.getWorkbench();
		IOperationHistory operationHistory = workbench.getOperationSupport().getOperationHistory();
		IUndoContext undoContext = workbench.getOperationSupport().getUndoContext();
		cmd.addContext(undoContext);
		
		//new UndoRedoActionGroup(, undoContext, true);

		try {
			operationHistory.execute(cmd, null, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
			Application app = cmd.getApplication();
			if(page.getOpenApplication() && (null != app)){
				openApplicationEditor(app);
			}
			
		} catch (ExecutionException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return true;
	}

	private static void openApplicationEditor(Application app) {
		IOpenListener openListener = OpenListenerManager.getInstance()
				.getDefaultOpenListener(app.getClass(), app);
		if (openListener != null) {
			openListener.run(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	@Override
	public void addPages() {
		page = new NewApplicationPage(NEW_IEC61499_APPLICATION);
		page.setTitle(NEW_IEC61499_APPLICATION);
		page.setDescription(NEW_IEC61499_APP_DESC);
		addPage(page);
		page.setSystem(system);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(final IWorkbench workbench,
			final IStructuredSelection selection) {
		if (selection != null) {
			if (selection.getFirstElement() instanceof AutomationSystem) {
				this.system = (AutomationSystem) selection.getFirstElement();
			} else if(selection.getFirstElement() instanceof Application){
				this.system = ((Application) selection.getFirstElement()).getAutomationSystem();
			}
			else{	
				this.system = null;
			}
		} else
			this.system = null;

	}
	
	protected NewAppCommand getApplicationCreateCommand(org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem system) {
		return new NewAppCommand(system, page.getFileName(), Messages.NewApplicationWizard_Comment);
	}

}
