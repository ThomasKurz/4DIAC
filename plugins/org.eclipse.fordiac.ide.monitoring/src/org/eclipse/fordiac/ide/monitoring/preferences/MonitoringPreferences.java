/*******************************************************************************
 * Copyright (c) 2012 Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.preferences;

import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


public class MonitoringPreferences extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public MonitoringPreferences() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Settings for the Monitoring Plug-In.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		ColorFieldEditor watchColor = new ColorFieldEditor(
				PreferenceConstants.P_WATCH_COLOR, "Watch Color",
				getFieldEditorParent());
		addField(watchColor);
		ColorFieldEditor forceColor = new ColorFieldEditor(
				PreferenceConstants.P_FORCE_COLOR, "Force Color",
				getFieldEditorParent());
		addField(forceColor);

		IntegerFieldEditor polling = new IntegerFieldEditor(
				PreferenceConstants.P_POLLING_INTERVAL, "Polling interval in ms",
				getFieldEditorParent(), 300);
		polling.setValidRange(1, 60000);

		addField(polling);

		IntegerFieldEditor connectionTimeout = new IntegerFieldEditor(
				PreferenceConstants.P_RESPONSE_TIMEOUT, "Response Timout in ms",
				getFieldEditorParent(), 3000);
		connectionTimeout.setValidRange(1, 60000);

		addField(connectionTimeout);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}