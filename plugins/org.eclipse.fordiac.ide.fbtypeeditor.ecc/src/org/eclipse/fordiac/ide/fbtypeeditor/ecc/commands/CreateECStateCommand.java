/*******************************************************************************
 * Copyright (c) 2008, 2009, 2015, 2016 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.commands.Command;

/**
 * The Class CreateECStateCommand.
 */
public class CreateECStateCommand extends Command {

	/** The location. */
	private final Point location;

	/** The ec state. */
	private final ECState ecState;

	/** The parent. */
	private final ECC parent;

	/**
	 * Instantiates a new creates the ec state command.
	 * 
	 * @param ecState the ec state
	 * @param location the location
	 * @param parent the parent
	 */
	public CreateECStateCommand(final ECState ecState, final Point location,
			final ECC parent) {
		super();
		this.location = location;
		this.ecState = ecState;
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		ecState.setX(location.x);
		ecState.setY(location.y);
		ecState.setName(NameRepository.getUniqueECCStateName(ecState, parent, ecState.getName()));
		if (parent.getECState().size() == 0) {
			parent.setStart(ecState);
		}
		parent.getECState().add(ecState);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		parent.getECState().remove(ecState);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Co mmand#redo()
	 */
	@Override
	public void redo() {
		parent.getECState().add(ecState);
	}
}
