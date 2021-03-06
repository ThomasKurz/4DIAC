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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.model.monitoring.PortElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Port Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.PortElementImpl#getFb <em>Fb</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.PortElementImpl#getInterfaceElement <em>Interface Element</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.PortElementImpl#getResource <em>Resource</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.PortElementImpl#getDevice <em>Device</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.PortElementImpl#getSystem <em>System</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.PortElementImpl#getHierarchy <em>Hierarchy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PortElementImpl extends EObjectImpl implements PortElement {
	/**
	 * The cached value of the '{@link #getFb() <em>Fb</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFb()
	 * @generated
	 * @ordered
	 */
	protected FB fb;

	/**
	 * The cached value of the '{@link #getInterfaceElement() <em>Interface Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterfaceElement()
	 * @generated
	 * @ordered
	 */
	protected IInterfaceElement interfaceElement;

	/**
	 * The cached value of the '{@link #getResource() <em>Resource</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected Resource resource;

	/**
	 * The cached value of the '{@link #getDevice() <em>Device</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDevice()
	 * @generated
	 * @ordered
	 */
	protected Device device;

	/**
	 * The cached value of the '{@link #getSystem() <em>System</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystem()
	 * @generated
	 * @ordered
	 */
	protected AutomationSystem system;

	/**
	 * The cached value of the '{@link #getHierarchy() <em>Hierarchy</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHierarchy()
	 * @generated
	 * @ordered
	 */
	protected EList<String> hierarchy;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PortElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MonitoringPackage.Literals.PORT_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FB getFb() {
		if (fb != null && fb.eIsProxy()) {
			InternalEObject oldFb = (InternalEObject)fb;
			fb = (FB)eResolveProxy(oldFb);
			if (fb != oldFb) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MonitoringPackage.PORT_ELEMENT__FB, oldFb, fb));
			}
		}
		return fb;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FB basicGetFb() {
		return fb;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFb(FB newFb) {
		FB oldFb = fb;
		fb = newFb;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.PORT_ELEMENT__FB, oldFb, fb));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IInterfaceElement getInterfaceElement() {
		if (interfaceElement != null && interfaceElement.eIsProxy()) {
			InternalEObject oldInterfaceElement = (InternalEObject)interfaceElement;
			interfaceElement = (IInterfaceElement)eResolveProxy(oldInterfaceElement);
			if (interfaceElement != oldInterfaceElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MonitoringPackage.PORT_ELEMENT__INTERFACE_ELEMENT, oldInterfaceElement, interfaceElement));
			}
		}
		return interfaceElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IInterfaceElement basicGetInterfaceElement() {
		return interfaceElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInterfaceElement(IInterfaceElement newInterfaceElement) {
		IInterfaceElement oldInterfaceElement = interfaceElement;
		interfaceElement = newInterfaceElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.PORT_ELEMENT__INTERFACE_ELEMENT, oldInterfaceElement, interfaceElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource getResource() {
		if (resource != null && resource.eIsProxy()) {
			InternalEObject oldResource = (InternalEObject)resource;
			resource = (Resource)eResolveProxy(oldResource);
			if (resource != oldResource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MonitoringPackage.PORT_ELEMENT__RESOURCE, oldResource, resource));
			}
		}
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource basicGetResource() {
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResource(Resource newResource) {
		Resource oldResource = resource;
		resource = newResource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.PORT_ELEMENT__RESOURCE, oldResource, resource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Device getDevice() {
		if (device != null && device.eIsProxy()) {
			InternalEObject oldDevice = (InternalEObject)device;
			device = (Device)eResolveProxy(oldDevice);
			if (device != oldDevice) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MonitoringPackage.PORT_ELEMENT__DEVICE, oldDevice, device));
			}
		}
		return device;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Device basicGetDevice() {
		return device;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDevice(Device newDevice) {
		Device oldDevice = device;
		device = newDevice;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.PORT_ELEMENT__DEVICE, oldDevice, device));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AutomationSystem getSystem() {
		if (system != null && system.eIsProxy()) {
			InternalEObject oldSystem = (InternalEObject)system;
			system = (AutomationSystem)eResolveProxy(oldSystem);
			if (system != oldSystem) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MonitoringPackage.PORT_ELEMENT__SYSTEM, oldSystem, system));
			}
		}
		return system;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AutomationSystem basicGetSystem() {
		return system;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystem(AutomationSystem newSystem) {
		AutomationSystem oldSystem = system;
		system = newSystem;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.PORT_ELEMENT__SYSTEM, oldSystem, system));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getHierarchy() {
		if (hierarchy == null) {
			hierarchy = new EDataTypeUniqueEList<String>(String.class, this, MonitoringPackage.PORT_ELEMENT__HIERARCHY);
		}
		return hierarchy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPortString() {
				String hierarchy = ""; 
				for (String element : getHierarchy()) {
					hierarchy += element; 
					hierarchy += "."; 
				}
		
				String adapter = "";
				if (interfaceElement.eContainer().eContainer() instanceof AdapterFB) {
					adapter += ((PortElementImpl)eContainer()).interfaceElement.getName();
					adapter += ".";
				}
		
				return device.getName() + "."
						+ resource.getName() + "." + hierarchy +  fb.getName() + "."
						+ adapter + interfaceElement.getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MonitoringPackage.PORT_ELEMENT__FB:
				if (resolve) return getFb();
				return basicGetFb();
			case MonitoringPackage.PORT_ELEMENT__INTERFACE_ELEMENT:
				if (resolve) return getInterfaceElement();
				return basicGetInterfaceElement();
			case MonitoringPackage.PORT_ELEMENT__RESOURCE:
				if (resolve) return getResource();
				return basicGetResource();
			case MonitoringPackage.PORT_ELEMENT__DEVICE:
				if (resolve) return getDevice();
				return basicGetDevice();
			case MonitoringPackage.PORT_ELEMENT__SYSTEM:
				if (resolve) return getSystem();
				return basicGetSystem();
			case MonitoringPackage.PORT_ELEMENT__HIERARCHY:
				return getHierarchy();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MonitoringPackage.PORT_ELEMENT__FB:
				setFb((FB)newValue);
				return;
			case MonitoringPackage.PORT_ELEMENT__INTERFACE_ELEMENT:
				setInterfaceElement((IInterfaceElement)newValue);
				return;
			case MonitoringPackage.PORT_ELEMENT__RESOURCE:
				setResource((Resource)newValue);
				return;
			case MonitoringPackage.PORT_ELEMENT__DEVICE:
				setDevice((Device)newValue);
				return;
			case MonitoringPackage.PORT_ELEMENT__SYSTEM:
				setSystem((AutomationSystem)newValue);
				return;
			case MonitoringPackage.PORT_ELEMENT__HIERARCHY:
				getHierarchy().clear();
				getHierarchy().addAll((Collection<? extends String>)newValue);
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
			case MonitoringPackage.PORT_ELEMENT__FB:
				setFb((FB)null);
				return;
			case MonitoringPackage.PORT_ELEMENT__INTERFACE_ELEMENT:
				setInterfaceElement((IInterfaceElement)null);
				return;
			case MonitoringPackage.PORT_ELEMENT__RESOURCE:
				setResource((Resource)null);
				return;
			case MonitoringPackage.PORT_ELEMENT__DEVICE:
				setDevice((Device)null);
				return;
			case MonitoringPackage.PORT_ELEMENT__SYSTEM:
				setSystem((AutomationSystem)null);
				return;
			case MonitoringPackage.PORT_ELEMENT__HIERARCHY:
				getHierarchy().clear();
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
			case MonitoringPackage.PORT_ELEMENT__FB:
				return fb != null;
			case MonitoringPackage.PORT_ELEMENT__INTERFACE_ELEMENT:
				return interfaceElement != null;
			case MonitoringPackage.PORT_ELEMENT__RESOURCE:
				return resource != null;
			case MonitoringPackage.PORT_ELEMENT__DEVICE:
				return device != null;
			case MonitoringPackage.PORT_ELEMENT__SYSTEM:
				return system != null;
			case MonitoringPackage.PORT_ELEMENT__HIERARCHY:
				return hierarchy != null && !hierarchy.isEmpty();
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
		result.append(" (hierarchy: ");
		result.append(hierarchy);
		result.append(')');
		return result.toString();
	}

} //PortElementImpl
