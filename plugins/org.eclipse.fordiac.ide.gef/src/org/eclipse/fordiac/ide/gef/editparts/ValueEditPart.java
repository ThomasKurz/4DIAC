/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012, 2014 - 2017 Profactor GbmH, fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.figures.ValueToolTipFigure;
import org.eclipse.fordiac.ide.gef.policies.ValueEditPartChangeEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

public class ValueEditPart extends AbstractGraphicalEditPart implements
		NodeEditPart {

	private DirectEditManager manager;

	private ValueFigure figure;
	private EditPart context;	
	private InterfaceEditPart parentPart;

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		super.activate();
		getModel().eAdapters().add(contentAdapter);
		
		@SuppressWarnings("rawtypes")
		Set set = getViewer().getEditPartRegistry().keySet();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = set.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			if (object instanceof IInterfaceElement) {
				if (((IInterfaceElement) object).equals(getModel().getVarDeclaration())) {

					EditPart part = (EditPart) getViewer()
							.getEditPartRegistry().get(object);
					if (part instanceof InterfaceEditPart) {
						parentPart = (InterfaceEditPart) part;
						IFigure f = parentPart.getFigure();
						f.addAncestorListener(new AncestorListener() {

							@Override
							public void ancestorRemoved(IFigure ancestor) {
							}

							@Override
							public void ancestorMoved(IFigure ancestor) {
								// calculatePos();
								refreshVisuals();

							}

							@Override
							public void ancestorAdded(IFigure ancestor) {
							}
						});
					}

				}
			}
		}
		refreshVisuals();
	}
	
	

	private Point calculatePos() {
		if (parentPart != null) {
			Rectangle bounds = parentPart.getFigure().getBounds();
			int x = 0;
			if (isInput()) {
				x = bounds.x - 2 - calculateWidth();
			} else {
				x = bounds.x + bounds.width + 2;
			}
			int y = bounds.y;
			return new Point(x, y);
		}
		return new Point(0, 0);
	}

	protected void refreshPosition() {
		if (getParent() != null) {
			Rectangle bounds = null;
			Point p = calculatePos();
			bounds = new Rectangle(p.x, p.y, calculateWidth(), -1);
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	private int calculateWidth() {
		int width = getFigure().getPreferredSize().width;
		width = Math.max(40, width);
		width = Math.min(width, 600);
		return width;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		super.deactivate();
		getModel().eAdapters().remove(contentAdapter);
		if (manager != null) {
			manager = null;
		}
	}
	
	private final EContentAdapter contentAdapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getValue_Value().equals(feature)) {
				refreshValue();
				refreshPosition();
			}
			super.notifyChanged(notification);
		}
	};

	/**
	 * The Class ValueFigure.
	 */
	private class ValueFigure extends Label {

		/**
		 * Instantiates a new value figure.
		 */
		public ValueFigure() {
			setText(getModel().getValue() != null ? getModel().getValue() : ""); //$NON-NLS-1$

			setOpaque(false);
			if (isInput()) {
				setLabelAlignment(PositionConstants.RIGHT);
				setTextAlignment(PositionConstants.CENTER);
				setTextPlacement(PositionConstants.EAST);
			} else {
				setLabelAlignment(PositionConstants.LEFT);
				setTextAlignment(PositionConstants.LEFT);
			}
			
			setMinimumSize(new Dimension(50, 1));
			setToolTip(new ValueToolTipFigure(getIInterfaceElement(), getModel()));
		}

	}
	
	/**
	 * Refresh value.
	 */
	void refreshValue() {
		if (getModel().getValue() != null) {
			getFigure().setText(getModel().getValue());
			setVisible(true);
		} else {
			setVisible(false);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		refreshValue();
		refreshPosition();
	}

	
	@Override
	public Label getFigure() {
		return (Label) super.getFigure();
	}

	/**
	 * Sets the visible.
	 * 
	 * @param visible the new visible
	 */
	public void setVisible(final boolean visible) {
		getFigure().setVisible(visible);
	}

	protected IFigure createFigureForModel() {
		if (figure == null) {
			figure = new ValueFigure();
		}
		return figure;
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
	}

	@Override
	protected void createEditPolicies() {
		// EditPolicy which allows the direct edit of the value
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new ValueEditPartChangeEditPolicy());
	}

	@Override
	public Value getModel() {
		return (Value) super.getModel();
	}

	/**
	 * Checks if is input.
	 * 
	 * @return true, if is input
	 */
	public boolean isInput() {
		// return getCastedModel().getIInterfaceElement().isIsInput();
		return true;
	}

	/**
	 * Checks if is event.
	 * 
	 * @return true, if is event
	 */
	public boolean isEvent() {
		// return getCastedModel().getIInterfaceElement() instanceof EventImpl;
		return false;
	}

	/**
	 * Checks if is variable.
	 * 
	 * @return true, if is variable
	 */
	public boolean isVariable() {
		return true;
		// return getCastedModel().getIInterfaceElement() instanceof
		// VarDeclarationImpl;
	}

	private IInterfaceElement getIInterfaceElement() {
		return getModel().getVarDeclaration();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new FixedAnchor(getFigure(), isInput());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	protected IFigure createFigure() {
		IFigure f = null;
		try {
			f = createFigureForModel();
		} catch (IllegalArgumentException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return f;
	}

	/**
	 * Gets the context.
	 * 
	 * @return the context
	 */
	public EditPart getContext() {
		return context;
	}

	/**
	 * Sets the context.
	 * 
	 * @param context the new context
	 */
	public void setContext(final EditPart context) {
		this.context = context;
	}

	/**
	 * Gets the manager.
	 * 
	 * @return the manager
	 */
	public DirectEditManager getManager() {
		if (manager == null) {
			Label l = getFigure();
			manager = new LabelDirectEditManager(this, TextCellEditor.class,
					new NameCellEditorLocator(l), l);
		}

		return manager;
	}

	/**
	 * performs the directEdit.
	 */
	public void performDirectEdit() {
		getManager().show();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef.Request)
	 */
	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT
				|| request.getType() == RequestConstants.REQ_OPEN) {
			performDirectEdit();

		} else {
			super.performRequest(request);
		}
	}

}
