/*******************************************************************************
 * Copyright (c) 2012, 2015, 2016 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.monitoring;

import java.util.ArrayList;

import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.monitoring.Breakpoints;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory;
import org.eclipse.fordiac.ide.model.monitoring.PortElement;

public abstract class AbstractMonitoringManager {

	protected final Breakpoints breakpoints = MonitoringFactory.eINSTANCE
			.createBreakpoints();

	/** The monitoring listeners. */
	protected final ArrayList<IMonitoringListener> monitoringListeners = new ArrayList<IMonitoringListener>();

	/**
	 * Register IMonitoringListener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void registerMonitoringListener(IMonitoringListener listener) {
		if (!monitoringListeners.contains(listener)) {
			monitoringListeners.add(listener);
		}
	}


	public void addBreakpointsAdapter(EContentAdapter adapter) {
		if (!breakpoints.eAdapters().contains(adapter)) {
			breakpoints.eAdapters().add(adapter);
		}
	}

	public void removeBreakpointsAdapter(EContentAdapter adapter) {
		breakpoints.eAdapters().remove(adapter);
	}

	ArrayList<IMonitoringListener> watchesAdapter = new ArrayList<IMonitoringListener>();
	
	public void addWatchesAdapter(IMonitoringListener adapter) {
		if (!watchesAdapter.contains(adapter)) {
			watchesAdapter.add(adapter);
		}
	}

	public void removeWatchesAdapter(IMonitoringListener adapter) {
		watchesAdapter.remove(adapter);
	}
	
	public void notifyWatchesAdapterPortAdded(PortElement port) {
		for (IMonitoringListener adapter : watchesAdapter) {
			adapter.notifyAddPort(port);
		}
	}
	public void notifyWatchesAdapterPortRemoved(PortElement port) {
		for (IMonitoringListener adapter : watchesAdapter) {
			adapter.notifyRemovePort(port);
		}
	}
	
	public abstract void disableSystem(String system);
	
	public abstract void enableSystem(String system);
}
