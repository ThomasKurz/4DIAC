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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>FB Network</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getNetworkElements <em>Network Elements</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getDataConnections <em>Data Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getEventConnections <em>Event Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getAdapterConnections <em>Adapter Connections</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getFBNetwork()
 * @model
 * @generated
 */
public interface FBNetwork extends EObject {
	/**
	 * Returns the value of the '<em><b>Network Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Network Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Network Elements</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getFBNetwork_NetworkElements()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='FB' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<FBNetworkElement> getNetworkElements();

	/**
	 * Returns the value of the '<em><b>Data Connections</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.DataConnection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Connections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Connections</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getFBNetwork_DataConnections()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='DataConnections' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<DataConnection> getDataConnections();

	/**
	 * Returns the value of the '<em><b>Event Connections</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.EventConnection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Connections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Connections</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getFBNetwork_EventConnections()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<EventConnection> getEventConnections();

	/**
	 * Returns the value of the '<em><b>Adapter Connections</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.model.libraryElement.Connection}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adapter Connections</em>' containment reference
	 * list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Adapter Connections</em>' containment
	 *         reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getFBNetwork_AdapterConnections()
	 * @model containment="true" extendedMetaData=
	 *        "kind='element' name='AdapterConnections' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<AdapterConnection> getAdapterConnections();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model connectionRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if (connection instanceof EventConnection) {\n\tgetEventConnections().add((EventConnection)connection);\n}\nif (connection instanceof DataConnection) {\n\tgetDataConnections().add((DataConnection)connection);\n}\nif (connection instanceof AdapterConnection) {\n\tgetAdapterConnections().add((AdapterConnection)connection);\n}'"
	 * @generated
	 */
	void addConnection(Connection connection);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model connectionRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if (connection instanceof EventConnection) {\n\tgetEventConnections().remove((EventConnection)connection);\n}\nif (connection instanceof DataConnection) {\n\tgetDataConnections().remove((DataConnection)connection);\n}\nif (connection instanceof AdapterConnection) {\n\tgetAdapterConnections().remove((AdapterConnection)connection);\n}'"
	 * @generated
	 */
	void removeConnection(Connection connection);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return eContainer() instanceof Application;'"
	 * @generated
	 */
	boolean isApplicationNetwork();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return eContainer() instanceof SubApp;'"
	 * @generated
	 */
	boolean isSubApplicationNetwork();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return eContainer() instanceof Resource;'"
	 * @generated
	 */
	boolean isResourceNetwork();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return eContainer() instanceof CompositeFBType;'"
	 * @generated
	 */
	boolean isCFBTypeNetwork();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if(isApplicationNetwork() || isSubApplicationNetwork()){\n\treturn getApplication().getAutomationSystem();\n}\nif(isResourceNetwork()){\n\treturn ((Resource)eContainer()).getAutomationSystem();\n}else if(isCFBTypeNetwork()){\n\treturn ((CompositeFBType)eContainer()).getPaletteEntry().getGroup().getPallete().getAutomationSystem();\n}\nreturn null;'"
	 * @generated
	 */
	AutomationSystem getAutomationSystem();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if(isApplicationNetwork()){\r\n\t// no null check is need as this is already done in isApplicationNetwork\r\n\treturn (Application)eContainer();\r\n}else if(isSubApplicationNetwork()){\r\n\treturn ((SubApp) eContainer()).getFbNetwork().getApplication();\r\n}\r\nreturn null;\t\t\r\n'"
	 * @generated
	 */
	Application getApplication();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nameDataType="org.eclipse.emf.ecore.xml.type.String" nameRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='for (FBNetworkElement element : getNetworkElements()) {\n\tif((element instanceof FB) && (element.getName().equals(name))){\n\t\treturn (FB)element;\n\t}\n}\nreturn null;'"
	 * @generated
	 */
	FB getFBNamed(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nameDataType="org.eclipse.emf.ecore.xml.type.String" nameRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='for (FBNetworkElement element : getNetworkElements()) {\n\tif(element instanceof SubApp){ \n\t\tif(element.getName().equals(name)){\n\t\t\treturn (SubApp)element; \n\t\t}\n\t\t\n\t\tSubApp retVal = ((SubApp)element).getSubAppNetwork().getSubAppNamed(name);\n\t\tif(retVal != null){\n\t\t\treturn retVal;\n\t\t}\n\t}\n}\t\n\nreturn null;'"
	 * @generated
	 */
	SubApp getSubAppNamed(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nameDataType="org.eclipse.emf.ecore.xml.type.String" nameRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='for (FBNetworkElement element : getNetworkElements()) {\n\tif(element.getName().equals(name)){\n\t\treturn element;\n\t}\n}\nreturn null;'"
	 * @generated
	 */
	FBNetworkElement getElementNamed(String name);

} // FBNetwork
