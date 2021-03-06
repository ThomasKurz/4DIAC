/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.monitoring.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.model.monitoring.PortElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringBaseElementImpl#getPort <em>Port</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringBaseElementImpl#isOffline <em>Offline</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MonitoringBaseElementImpl extends EObjectImpl implements MonitoringBaseElement {
	/**
	 * The cached value of the '{@link #getPort() <em>Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPort()
	 * @generated
	 * @ordered
	 */
	protected PortElement port;

	/**
	 * The default value of the '{@link #isOffline() <em>Offline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOffline()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OFFLINE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isOffline() <em>Offline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOffline()
	 * @generated
	 * @ordered
	 */
	protected boolean offline = OFFLINE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MonitoringBaseElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MonitoringPackage.Literals.MONITORING_BASE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PortElement getPort() {
		if (port != null && port.eIsProxy()) {
			InternalEObject oldPort = (InternalEObject)port;
			port = (PortElement)eResolveProxy(oldPort);
			if (port != oldPort) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MonitoringPackage.MONITORING_BASE_ELEMENT__PORT, oldPort, port));
			}
		}
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PortElement basicGetPort() {
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPort(PortElement newPort) {
		PortElement oldPort = port;
		port = newPort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.MONITORING_BASE_ELEMENT__PORT, oldPort, port));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOffline() {
		return offline;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOffline(boolean newOffline) {
		boolean oldOffline = offline;
		offline = newOffline;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.MONITORING_BASE_ELEMENT__OFFLINE, oldOffline, offline));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPortString() {
		String hierarchy = ""; 
				for (String element : getPort().getHierarchy()) {
					hierarchy += element; 
					hierarchy += "."; 
				}
				
				String adapter = "";
				if (getPort().getInterfaceElement().eContainer().eContainer() instanceof AdapterFB) {
					adapter += ((PortElementImpl)getPort().eContainer()).getInterfaceElement().getName();
					adapter += ".";
				}
				
				return hierarchy +  getPort().getFb().getName() + "." +
						adapter + getPort().getInterfaceElement().getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResourceString() {
		return getPort().getResource().getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFBString() {
		return getPort().getFb().getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQualifiedString() {
		String hierarchy = ""; 
		for (String element : getPort().getHierarchy()) {
			hierarchy += element; 
			hierarchy += "."; 
		}
		
		String adapter = "";
		if (getPort().getInterfaceElement().eContainer().eContainer() instanceof AdapterFB) {
			adapter += ((PortElementImpl)getPort().eContainer()).getInterfaceElement().getName();
			adapter += ".";
		}
		
		return hierarchy +  getPort().getFb().getName() + "." +
				adapter + getPort().getInterfaceElement().getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MonitoringPackage.MONITORING_BASE_ELEMENT__PORT:
				if (resolve) return getPort();
				return basicGetPort();
			case MonitoringPackage.MONITORING_BASE_ELEMENT__OFFLINE:
				return isOffline();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MonitoringPackage.MONITORING_BASE_ELEMENT__PORT:
				setPort((PortElement)newValue);
				return;
			case MonitoringPackage.MONITORING_BASE_ELEMENT__OFFLINE:
				setOffline((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MonitoringPackage.MONITORING_BASE_ELEMENT__PORT:
				setPort((PortElement)null);
				return;
			case MonitoringPackage.MONITORING_BASE_ELEMENT__OFFLINE:
				setOffline(OFFLINE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MonitoringPackage.MONITORING_BASE_ELEMENT__PORT:
				return port != null;
			case MonitoringPackage.MONITORING_BASE_ELEMENT__OFFLINE:
				return offline != OFFLINE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (offline: ");
		result.append(offline);
		result.append(')');
		return result.toString();
	}
} //MonitoringBaseElementImpl
