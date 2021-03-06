/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editparts;

import java.util.List;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.util.IPropertyChangeListener;

/**
 * This class implements an EditPart for an "VirtualInOutput". It is required if
 * a connection is "brocken" when mapped. (fbs distributed to different
 * resources)
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class VirtualInOutputEditPart extends AbstractViewEditPart implements
		NodeEditPart {

	@Override
	public void activate() {
		super.activate();
		updatePos();
	}

	private EContentAdapter adapter;

	@Override
	protected EContentAdapter getContentAdapter() {
		if (adapter == null) {
			adapter = new EContentAdapter() {

				@Override
				public void notifyChanged(final Notification notification) {
					super.notifyChanged(notification);
					refreshSourceConnections();
					refreshTargetConnections();
					refreshVisuals();
					refreshTooltip();
				}

			};
		}
		return adapter;
	}

	private void refreshTooltip() {
		getFigure().setToolTip(new VirtualIOTooltipFigure());

	}

	@Override
	protected void refreshVisuals() {

		super.refreshVisuals();
	}

	/** The oldx. */
	int oldx = 0;

	/** The oldy. */
	int oldy = 0;

	private void updatePos() {
		if (getParent() instanceof FBNetworkContainerEditPart) {
			FBNetworkContainerEditPart fbnce = (FBNetworkContainerEditPart) getParent();
			IInterfaceElement element = fbnce.getMainInterfaceElementView(getModel());
			Object o = getViewer().getEditPartRegistry().get(element);
			if (o instanceof InterfaceEditPartForResourceFBs) {
				InterfaceEditPartForResourceFBs iep = (InterfaceEditPartForResourceFBs) o;

				String label = ((Label) getFigure()).getText();

				Rectangle bounds = iep.getFigure().getBounds();
				int x = 0;
				if (!isInput()) {
					x = bounds.x - 20 - FigureUtilities.getTextWidth(label, getFigure().getFont());
				} else {
					x = bounds.x + bounds.width + 1;
				}
				int y = bounds.y;
				if (x != oldx && y != oldy) {
					//TODO model refactoring - implement when all views are deleted
//					if (getCastedModel().getPosition() != null) {
//						pos = getCastedModel().getPosition();
//					}
//					pos.setX(x);
//					pos.setY(bounds.y);
//					getCastedModel().setPosition(pos);
					oldx = x;
					oldy = y;
				}
			}
		}
	}

	/**
	 * Gets the casted model.
	 * 
	 * @return the casted model
	 */
	@Override
	public IInterfaceElement getModel() {
		return (IInterfaceElement) super.getModel();
	}

	/**
	 * Checks if is input.
	 * 
	 * @return true, if is input
	 */
	public boolean isInput() {
		return getModel().isIsInput();
	}

	/**
	 * Checks if is event.
	 * 
	 * @return true, if is event
	 */
	public boolean isEvent() {
		return getModel() instanceof Event;
	}

	/**
	 * Checks if is variable.
	 * 
	 * @return true, if is variable
	 */
	public boolean isVariable() {
		return getModel() instanceof VarDeclaration;
	}

//	private IInterfaceElement getIInterfaceElement() {
//		return getCastedModel().getIInterfaceElement();
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// overwrite the rename edit policy - no rename possible
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#understandsRequest(org.eclipse
	 * .gef.Request)
	 */
	@Override
	public boolean understandsRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_MOVE) {
			return false;
		}
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT
				|| request.getType() == RequestConstants.REQ_OPEN) {
			return false;
		}
		return super.understandsRequest(request);
	}

	@Override
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT
				|| request.getType() == RequestConstants.REQ_OPEN) {
			return;
		}
		super.performRequest(request);
	}

	/**
	 * The Class VirtualInputOutputFigure.
	 */
	public class VirtualInputOutputFigure extends Label {

		/**
		 * Instantiates a new virtual input output figure.
		 */
		public VirtualInputOutputFigure() {
			super();
			// FB fb = (FB) getCastedModel().getIInterfaceElement().eContainer()
			// .eContainer();
			// setText(fb.getName() + "." + getCastedModel().getLabel());
			// setBorder(new MarginBorder(0, 5, 0, 5));
			// setBorder(new ConnectorBorder());
			setOpaque(false);
			if (isInput()) {
				setIcon(FordiacImage.ICON_LinkOutput.getImage());
				setLabelAlignment(PositionConstants.LEFT);
				setTextAlignment(PositionConstants.LEFT);
			} else {
				setIcon(FordiacImage.ICON_LinkInput.getImage());
				setLabelAlignment(PositionConstants.RIGHT);
				setTextAlignment(PositionConstants.RIGHT);
			}
			setToolTip(new VirtualIOTooltipFigure());
			setSize(-1, -1);
		}

	}

	private class VirtualIOTooltipFigure extends Figure {
		public VirtualIOTooltipFigure() {

			setLayoutManager(new BorderLayout());
			IFigure leftCol = new Figure();
			IFigure rightCol = new Figure();
			leftCol.setLayoutManager(new ToolbarLayout());
			rightCol.setLayoutManager(new ToolbarLayout());
			add(leftCol, BorderLayout.LEFT);
			add(rightCol, BorderLayout.CENTER);
			add(new Label(getModel().getName()), BorderLayout.TOP);

			FBNetworkElement fbNetElement = getModel().getFBNetworkElement();
			if (fbNetElement == null) {
				return;
			}
			if (fbNetElement.getResource() == null) {
				return;
			}

			Resource res = fbNetElement.getResource();
			if (res == null) {
				return;
			}
			Device dev = res.getDevice();
			if (dev == null) {
				return;
			}

			add(new Label(dev.getName() + "." + res.getName() + "."
					+ fbNetElement.getName() + "." + getModel().getName()),BorderLayout.TOP);

		}
	}

	@Override
	protected IFigure createFigureForModel() {
		IFigure f = new VirtualInputOutputFigure();
		return f; // new VirtualInputOutputFigure();
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	protected List<?> getModelSourceConnections() {
		return getModel().getOutputConnections();
	}

	@Override
	protected List<?> getModelTargetConnections() {
		return getModel().getInputConnections();
	}

	@Override
	public Label getNameLabel() {
		return null;
	}

	@Override
	public IPropertyChangeListener getPreferenceChangeListener() {
		return null;
	}
}
