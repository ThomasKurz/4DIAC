/*******************************************************************************
 * Copyright (c) 2012 fortiss GmbH
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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * The Class ChangeFunctionCommand.
 */
public class ChangeFunctionCommand extends ChangeIdentificationCommand {
	
	/** The new Function value. */
	private String newFunction;

	/** The old Function value. */
	private String oldFunction;

	public ChangeFunctionCommand(LibraryElement type,
			final String newFunction) {
		super(type);
		this.newFunction = newFunction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldFunction = identification.getFunction();
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		identification.setFunction(oldFunction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		identification.setFunction(newFunction);
	}

}
