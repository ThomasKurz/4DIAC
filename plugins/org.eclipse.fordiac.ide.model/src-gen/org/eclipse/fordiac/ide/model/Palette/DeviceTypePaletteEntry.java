/********************************************************************************
 * Copyright (c) 2008, 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.Palette;

import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Device Type Palette Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getDeviceTypePaletteEntry()
 * @model
 * @generated
 */
public interface DeviceTypePaletteEntry extends PaletteEntry {
	
	DeviceType getDeviceType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='if((null != type) && (type instanceof DeviceType)){\r\n\tsuper.setType(type);\r\n}else{\r\n\tsuper.setType(null);\r\n\tif(null != type){\r\n\t\t<%org.eclipse.core.runtime.Status%> exception = new Status(<%org.eclipse.core.runtime.IStatus%>.ERROR, Activator.PLUGIN_ID, \"tried to set no DeviceType as type entry for DeviceTypePaletteEntry\");\r\n\t\tActivator.getDefault().getLog().log(exception);\r\n\t}\r\n}'"
	 * @generated
	 */
	void setType(LibraryElement type);

} // DeviceTypePaletteEntry
