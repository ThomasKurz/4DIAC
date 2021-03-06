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

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getFile <em>File</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLastModificationTimestamp <em>Last Modification Timestamp</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry()
 * @model abstract="true"
 * @generated
 */
public interface PaletteEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File</em>' attribute.
	 * @see #setFile(IFile)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry_File()
	 * @model dataType="org.eclipse.fordiac.ide.model.Palette.IFile"
	 * @generated
	 */
	IFile getFile();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getFile <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated
	 */
	void setFile(IFile value);

	/**
	 * Returns the value of the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Modification Timestamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Modification Timestamp</em>' attribute.
	 * @see #setLastModificationTimestamp(long)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry_LastModificationTimestamp()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Long" transient="true" ordered="false"
	 * @generated
	 */
	long getLastModificationTimestamp();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLastModificationTimestamp <em>Last Modification Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Modification Timestamp</em>' attribute.
	 * @see #getLastModificationTimestamp()
	 * @generated
	 */
	void setLastModificationTimestamp(long value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getPaletteEntry <em>Palette Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(LibraryElement)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry_Type()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getPaletteEntry
	 * @model opposite="paletteEntry" resolveProxies="false" required="true"
	 * @generated
	 */
	LibraryElement getType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(LibraryElement value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if(null != eContainer()){\n  return (PaletteGroup)eContainer();\n}\nreturn null;'"
	 * @generated
	 */
	PaletteGroup getGroup();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return getFile().getProjectRelativePath().toOSString();'"
	 * @generated
	 */
	String getProjectRelativeTypePath();

	
} // PaletteEntry
