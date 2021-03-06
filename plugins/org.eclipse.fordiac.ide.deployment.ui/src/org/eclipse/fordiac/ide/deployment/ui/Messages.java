/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2015 Profactor GbmH, fortiss GmbH
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
package org.eclipse.fordiac.ide.deployment.ui;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.deployment.ui.messages"; //$NON-NLS-1$

	/** The Annotation marker access_ labe l_ error annotation. */
	public static String AnnotationMarkerAccess_LABEL_ErrorAnnotation;

	/** The Annotation marker access_ labe l_ warning annotation. */
	public static String AnnotationMarkerAccess_LABEL_WarningAnnotation;

	/** The Download selection tree view_ collaps e_ all. */
	public static String DownloadSelectionTreeView_COLLAPSE_ALL;

	/** The Download selection tree view_ expan d_ all. */
	public static String DownloadSelectionTreeView_EXPAND_ALL;

	/** The Download selection tree view_ labe l_ popup menu. */
	public static String DownloadSelectionTreeView_LABEL_PopupMenu;

	/** The Download selection tree view_ refresh. */
	public static String DownloadSelectionTreeView_Refresh;

	/** The Error annotation_ download error. */
	public static String ErrorAnnotation_DownloadError;

	/** The Log listener_ malformed error. */
	public static String LogListener_MalformedError;

	/** The Log listener_ returned error. */
	public static String LogListener_ReturnedError;

	/** The Mode_ download button label. */
	public static String Mode_DownloadButtonLabel;

	/** The Output_ clear action label. */
	public static String Output_ClearActionLabel;

	/** The Output_ clear description. */
	public static String Output_ClearDescription;

	/** The Output_ clear tooltip. */
	public static String Output_ClearTooltip;

	/** The Output_ comment. */
	public static String Output_Comment;

	/** The Output_ download error. */
	public static String Output_DownloadError;

	/** The Output_ download warning. */
	public static String Output_DownloadWarning;
	
	/** Title of the create bootfile wizard */  
	public static String FordiacCreateBootfilesWizard_LABEL_Window_Title;
	
	/** Page name of the create bootfile wizard page*/  
	public static String FordiacCreateBootfilesWizard_PageName;
	
	/** Description of the create bootfile page */  
	public static String FordiacCreateBootfilesWizard_PageDESCRIPTION;
	
	/** Title of the create bootfile wizard page*/  
	public static String FordiacCreateBootfilesWizard_PageTITLE;
		
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
