/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getEventConnection()
 * @model
 * @generated
 */
public interface EventConnection extends Connection {
	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (Event)super.getSource();'"
	 * @generated
	 */
	Event getSource();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (Event)super.getDestination();'"
	 * @generated
	 */
	Event getDestination();

} // EventConnection
