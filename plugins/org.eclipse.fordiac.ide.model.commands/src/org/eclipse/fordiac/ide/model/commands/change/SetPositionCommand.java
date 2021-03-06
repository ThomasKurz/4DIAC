/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
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

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.ui.IEditorPart;

public class SetPositionCommand extends Command {
	private final Rectangle newBounds;
	private Rectangle oldBounds;
	private final ChangeBoundsRequest request;
	private final PositionableElement positionableElement;
	private IEditorPart editor;

	@Override
	public boolean canUndo() {
		return editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}

	public SetPositionCommand(final PositionableElement positionableElement,
			final ChangeBoundsRequest req, final Rectangle newBounds) {
		this.positionableElement = positionableElement;
		this.request = req;
		this.newBounds = newBounds.getCopy();
		setLabel(Messages.ViewSetPositionCommand_LABEL_Move);
	}

	@Override
	public boolean canExecute() {
		Object type = request.getType();
		// make sure the Request is of a type we support: (Move or
		// Move_Children)
		// e.g. a FB moves within an application
		return RequestConstants.REQ_MOVE.equals(type)
				|| RequestConstants.REQ_MOVE_CHILDREN.equals(type)
				|| RequestConstants.REQ_ALIGN_CHILDREN.equals(type);
	}

	/**
	 * Sets the new Position of the affected UIFB.
	 */
	@Override
	public void execute() {
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
		setLabel(getLabel() + "(" + editor.getTitle() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		oldBounds = new Rectangle(positionableElement.getX(), positionableElement.getY(), -1, -1);
		redo();
	}

	@Override
	public void redo() {
		setPosition(newBounds);
	}

	@Override
	public void undo() {
		setPosition(oldBounds);
	}

	private void setPosition(Rectangle bounds) {
		positionableElement.setX(bounds.x);
		positionableElement.setY(bounds.y);
	}
}
