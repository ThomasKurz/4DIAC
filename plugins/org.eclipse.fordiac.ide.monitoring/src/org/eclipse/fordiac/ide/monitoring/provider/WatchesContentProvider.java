/*******************************************************************************
 * Copyright (c) 2012, 2014, 2015 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Gerd Kainz, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import java.util.ArrayList;

import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;


public class WatchesContentProvider implements ITreeContentProvider {

	private Viewer viewer;

	EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(
				final org.eclipse.emf.common.notify.Notification notification) {
			int featureID = notification.getFeatureID(MonitoringElement.class);
			if (featureID == MonitoringPackage.MONITORING_ELEMENT__CURRENT_VALUE
					&& notification.getNotifier() instanceof MonitoringElement) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						if(!viewer.getControl().isDisposed()){
							((TreeViewer) viewer).refresh(notification.getNotifier());
						}
					}
				});

			} else if (featureID == MonitoringPackage.MONITORING_ELEMENT__BREAKPOINT_ACTIVE) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						((TreeViewer) viewer).refresh();
					}
				});
			}
		}
	};
	ArrayList<MonitoringBaseElement> watchedElements = new ArrayList<MonitoringBaseElement>();

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof MonitoringElement) {
			return new Object[0];
		}
		// ArrayList<MonitoringElement> oldWatchedElements = new
		// ArrayList<MonitoringElement>();
		// oldWatchedElements.addAll(watchedElements);

		for (MonitoringBaseElement element : watchedElements) {
			if (element.eAdapters().contains(adapter)) {
				element.eAdapters().remove(adapter);
			}
		}

		watchedElements.clear();

		for (MonitoringBaseElement element  : MonitoringManager.getInstance().getElementsToMonitor()) {
			if (element != null && !watchedElements.contains(element)) {
				element.eAdapters().add(adapter);
				watchedElements.add(element);
			}
		}
		
		return watchedElements.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
	}

}
