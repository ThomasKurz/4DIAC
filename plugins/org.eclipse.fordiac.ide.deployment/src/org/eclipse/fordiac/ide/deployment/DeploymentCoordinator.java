/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.deployment.exceptions.CreateConnectionException;
import org.eclipse.fordiac.ide.deployment.exceptions.CreateFBInstanceException;
import org.eclipse.fordiac.ide.deployment.exceptions.CreateResourceInstanceException;
import org.eclipse.fordiac.ide.deployment.exceptions.DisconnectException;
import org.eclipse.fordiac.ide.deployment.exceptions.InvalidMgmtID;
import org.eclipse.fordiac.ide.deployment.exceptions.StartException;
import org.eclipse.fordiac.ide.deployment.exceptions.WriteFBParameterException;
import org.eclipse.fordiac.ide.deployment.exceptions.WriteResourceParameterException;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * The Class DeploymentCoordinator.
 */
public class DeploymentCoordinator {

	private final Hashtable<Device, ArrayList<VarDeclaration>> deployedDeviceProperties = new Hashtable<Device, ArrayList<VarDeclaration>>();

	/**
	 * Adds the device property.
	 * 
	 * @param dev
	 *            the dev
	 * @param property
	 *            the property
	 */
	public void addDeviceProperty(Device dev, VarDeclaration property) {
		if (deployedDeviceProperties.containsKey(dev)) {
			ArrayList<VarDeclaration> temp = deployedDeviceProperties.get(dev);
			if (!temp.contains(property)) {
				temp.add(property);
			}
		} else {
			ArrayList<VarDeclaration> temp = new ArrayList<VarDeclaration>();
			temp.add(property);
			deployedDeviceProperties.put(dev, temp);
		}
	}

	/**
	 * Sets the device properties.
	 * 
	 * @param dev
	 *            the dev
	 * @param properties
	 *            the properties
	 */
	public void setDeviceProperties(Device dev,
			ArrayList<VarDeclaration> properties) {
		deployedDeviceProperties.put(dev, properties);
	}

	/**
	 * Removes the device property.
	 * 
	 * @param dev
	 *            the dev
	 * @param property
	 *            the property
	 */
	public void removeDeviceProperty(Device dev, VarDeclaration property) {
		if (deployedDeviceProperties.containsKey(dev)) {
			ArrayList<VarDeclaration> temp = deployedDeviceProperties.get(dev);
			if (temp.contains(property)) {
				temp.remove(property);
			}
		}
	}

	/**
	 * Gets the selected device properties.
	 * 
	 * @param dev
	 *            the dev
	 * 
	 * @return the selected device properties
	 */
	public ArrayList<VarDeclaration> getSelectedDeviceProperties(Device dev) {
		return deployedDeviceProperties.get(dev);
	}

	/** The instance. */
	private static DeploymentCoordinator instance;

	/**
	 * Instantiates a new deployment coordinator.
	 */
	private DeploymentCoordinator() {
		// empty private constructor
	}

	/**
	 * Gets the single instance of DeploymentCoordinator.
	 * 
	 * @return single instance of DeploymentCoordinator
	 */
	public static DeploymentCoordinator getInstance() {
		if (instance == null) {
			instance = new DeploymentCoordinator();
		}
		return instance;
	}

	/** The deployment executors. */
	private ArrayList<IDeploymentExecutor> deploymentExecutors = null;

	/** The device management communication handlers. */
	private ArrayList<AbstractDeviceManagementCommunicationHandler> deviceMangementCommunicationHandlers = null;

	/** The listener. */
	private final IDeploymentListener listener = new IDeploymentListener() {

		@Override
		public void responseReceived(String response, String source) {
			responseReceivedX(response, source);
		}

		@Override
		public void postCommandSent(String command, String destination) {
			postCommandSentX(command, destination);
		}

		@Override
		public void postCommandSent(String message) {
			postCommandSentX(message);
		}

		@Override
		public void finished() {
			finishedX();
		}

		@Override
		public void postCommandSent(String info, String destination,
				String command) {
			postCommandSentX(info, destination, command);
		}

	};

	/**
	 * Gets the mG r_ id.
	 * 
	 * @param resource
	 *            the resource
	 * 
	 * @return the mG r_ id
	 */
	private String getMGR_ID(final Resource resource) {
		return getMGR_ID(resource.getDevice());
	}

	/**
	 * Gets the mG r_ id.
	 * 
	 * @param dev
	 *            the dev
	 * 
	 * @return the mG r_ id
	 */
	public static String getMGR_ID(final Device dev) {
		for(VarDeclaration varDecl : dev.getVarDeclarations()) {
			if (varDecl.getName().equalsIgnoreCase("MGR_ID")) {
				String val = getVariableValue(varDecl, dev.getAutomationSystem(), dev);
				if(null != val){				
					return val;
				}
			}
		}
		return "";
	}

	/**
	 * The Class DownloadRunnable.
	 */
	class DownloadRunnable implements IRunnableWithProgress {

		private final List<Device> devices;
		private final List<ResourceDeploymentData> resources;

		/** the Communication handler */
		AbstractDeviceManagementCommunicationHandler overrideDevMgmCommHandler;

		/**
		 * DownloadRunnable constructor.
		 * 
		 * @param work
		 *            the amount of download operations
		 * @param selection
		 *            the selection
		 * @param overrideDevMgmCommHandler
		 *            if not null this device management communication should be
		 *            used instead the one derived from the device profile.
		 */
		public DownloadRunnable(
				final List<Device> devices,
				final List<ResourceDeploymentData> resources,
				AbstractDeviceManagementCommunicationHandler overrideDevMgmCommHandler) {
			this.devices = devices;
			this.resources = resources;
			this.overrideDevMgmCommHandler = overrideDevMgmCommHandler;
		}

		/**
		 * Runs the check.
		 * 
		 * @param monitor
		 *            the progress monitor
		 * 
		 * @throws InvocationTargetException
		 *             the invocation target exception
		 * @throws InterruptedException
		 *             the interrupted exception
		 */
		public void run(final IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			monitor.beginTask(Messages.DeploymentCoordinator_LABEL_PerformingDownload,
					calculateWorkAmount());

			for (ResourceDeploymentData resDepData : resources) {
				if (monitor.isCanceled()) {
					throw new InterruptedException(Messages.DeploymentCoordinator_LABEL_DownloadAborted);
				}

				IDeploymentExecutor executor = getDeploymentExecutor(resDepData.res.getDevice(), overrideDevMgmCommHandler);

				if (executor != null) {
					executor.getDevMgmComHandler().addDeploymentListener(listener);
					executor.getDevMgmComHandler().resetTypes();
					try {
						deployResource(monitor, resDepData, executor);
					} catch (final Exception e) {
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								final Shell shell = Display.getDefault().getActiveShell();
								
								MessageDialog.openError(shell, "Major Download Error", 
										"Resource: " + resDepData.res.getDevice().getName() + "." + resDepData.res.getName() + "\n" +
										"MGR_ID: " + getMGR_ID(resDepData.res) + "\n" +		
										"Problem: "+ e.getMessage());
							}
						});
					}
					executor.getDevMgmComHandler().removeDeploymentListener(listener);

				} else {
					printUnsupportedDeviceProfileMessageBox(resDepData.res.getDevice(), resDepData.res);
				}
			}
			for (Device device : devices){
				configureDevice(monitor, device);
				if (monitor.isCanceled()) {
					throw new InterruptedException(Messages.DeploymentCoordinator_LABEL_DownloadAborted);
				}
			}
			// Thread.sleep(500);
			finishedX();
			monitor.done();
		}

		private int calculateWorkAmount() {
			int retVal = devices.size() + resources.size();
			for (ResourceDeploymentData resDepData : resources) {
				retVal += countResourceParams(resDepData.res);
				retVal += resDepData.fbs.size() + resDepData.connections.size() + resDepData.subAppInterfaceCrossingConns.size();
				//TODO count variables of Fbs
			}
			return retVal;
		}
		
		private int countResourceParams(final Resource res) {
			int work = 0;
			for (VarDeclaration varDecl : res.getVarDeclarations()) {
				if (varDecl.getValue() != null
						&& varDecl.getValue().getValue() != null
						&& varDecl.getValue().getValue().equals("")) {
					work++;
				}
			}
			return work;
		}

		protected void deployResource(final IProgressMonitor monitor, final ResourceDeploymentData resDepData, IDeploymentExecutor executor)
				throws InvalidMgmtID, UnknownHostException, IOException, CreateResourceInstanceException,
				WriteResourceParameterException, CreateFBInstanceException, WriteFBParameterException,
				CreateConnectionException, StartException, DisconnectException {
			Resource res = resDepData.res;
			if (!res.isDeviceTypeResource()) {
				executor.getDevMgmComHandler().connect(getMGR_ID(res));
				executor.createResource(res);
				monitor.worked(1);
				for (VarDeclaration varDecl : res.getVarDeclarations()) {
					String val = getVariableValue(varDecl, res.getAutomationSystem(), res);
					if(null != val){
						executor.writeResourceParameter(res, varDecl.getName(), val);
						monitor.worked(1);
					}
				}

				createFBInstance(resDepData, executor, monitor);				
				deployConnections(resDepData, executor, monitor);
				deploySubAppIfCrossingConnections(resDepData, executor, monitor);

				if (!devices.contains(res.getDevice())) {
					executor.startResource(res);
				} else {
					// resource is started when device is
					// started
				}
				executor.getDevMgmComHandler().disconnect();
			}
		}

		private void configureDevice(final IProgressMonitor monitor, Device device) {
			IDeploymentExecutor executor = getDeploymentExecutor(device, overrideDevMgmCommHandler);
			ArrayList<VarDeclaration> parameters = getSelectedDeviceProperties(device);

			if (executor != null && parameters != null && parameters.size() > 0) {
				try {
					executor.getDevMgmComHandler().addDeploymentListener(listener);
					String mgrid = getMGR_ID(device);
					executor.getDevMgmComHandler().connect(mgrid);
					for (VarDeclaration varDeclaration : parameters) {
						String value = getVariableValue(varDeclaration, device.getAutomationSystem(), device);
						if (null != value) {
							executor.writeDeviceParameter(device, varDeclaration.getName(), value);
						}
					}
					executor.startDevice(device);
					executor.getDevMgmComHandler().disconnect();
					executor.getDevMgmComHandler().removeDeploymentListener(
							listener);
					monitor.worked(1);
				} catch  (Exception e) {
					//TODO model refactoring - show error message to user
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}
		}

		private void deployConnections(final ResourceDeploymentData resDepData, final IDeploymentExecutor executor,  IProgressMonitor monitor) throws CreateConnectionException {
			for (Connection con : resDepData.connections) {
				//TODO model refactoring - if one connection endpoint is part of resource find inner endpoint  
				if (!con.isResTypeConnection()) {
					executor.createConnection(resDepData.res, con.getSource(), con.getDestination());
					monitor.worked(1);
				}
				if(monitor.isCanceled()){
					break;
				}
			}
		}
		
		private void deploySubAppIfCrossingConnections(final ResourceDeploymentData resDepData, final IDeploymentExecutor executor,  IProgressMonitor monitor) throws CreateConnectionException {
			for (ResourceDeploymentData.SubAppInterfaceCrossingConnection subAppIfCrossignConn : resDepData.subAppInterfaceCrossingConns) {
				executor.createConnection(resDepData.res, subAppIfCrossignConn.source, subAppIfCrossignConn.destination);
				monitor.worked(1);
				if(monitor.isCanceled()){
					break;
				}
			}
		}

		private void createFBInstance(final ResourceDeploymentData resDepData, final IDeploymentExecutor executor,
				final IProgressMonitor monitor)
				throws CreateFBInstanceException, WriteFBParameterException {
			Resource res = resDepData.res;
			for (FB fb : resDepData.fbs) {
				if(!fb.isResourceTypeFB()){
					executor.createFBInstance(fb, res);
					monitor.worked(1);
					InterfaceList interfaceList = fb.getInterface();
					if (interfaceList != null) {
						for (VarDeclaration varDecl : interfaceList.getInputVars()) {
							String val = getVariableValue(varDecl, res.getAutomationSystem(), fb);
							if(null != val){
								executor.writeFBParameter(res, val, fb, varDecl);
								monitor.worked(1);
							}
						}
					}
				}
			}
		}

	}

	private static String getVariableValue(VarDeclaration varDecl, AutomationSystem system, ConfigurableObject object) {
		Value value = varDecl.getValue();
		if (null != value && null != value.getValue() && !"".equals(value.getValue())){
			String val = value.getValue();
			if (val.contains("%")) {
				String replaced = SystemManager.INSTANCE.getReplacedString(system, object, val);
				if (replaced != null) {
					val = replaced;
				}
			}
			return val;
		}		
		return null;	
	}

	/**
	 * Count work creating f bs.
	 * 
	 * @param res
	 *            the res
	 * @param fbs
	 *            the fbs
	 * @param subApps
	 *            the sub apps
	 * 
	 * @return the int
	 */
	private int countWorkCreatingFBs(final FBNetwork fbNetwork) {
		int work = 0;
		
		for (FBNetworkElement element : fbNetwork.getNetworkElements()) {
			if(element instanceof SubApp){
				work += countWorkCreatingFBs(((SubApp) element).getSubAppNetwork());				
			} else if(element instanceof FB){
				work++;
				FB fb = (FB)element;
				InterfaceList interfaceList = fb.getInterface();
				if (interfaceList != null) {
					for (VarDeclaration varDecl : interfaceList.getInputVars()) {
						if (varDecl.getInputConnections().size() == 0) {
							Value value = varDecl.getValue();
							if (value != null && value.getValue() != null) {
								work++;
							}
						}
					}
				}				
			}
		}
		return work;
	}

	public static void printUnsupportedDeviceProfileMessageBox(
			final Device device, final Resource res) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {				
				MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.ICON_ERROR | SWT.OK);
				String resName = "";
				if (null != res) {
					resName = res.getName();
				}

				if(null != device.getProfile() && !device.getProfile().equals("")){
					messageBox.setMessage(MessageFormat.format(Messages.DeploymentCoordinator_MESSAGE_DefinedProfileNotSupported,
								new Object[] { (null != device.getProfile()) ? device.getProfile() : "",
										device.getName(), resName }));
				}else{
					messageBox.setMessage(MessageFormat.format(Messages.DeploymentCoordinator_MESSAGE_ProfileNotSet,
							new Object[] {device.getName(), resName }));
				}
				
				messageBox.open();
			}
		});
			
	}

	/**
	 * Perform deployment.
	 * 
	 * @param selection
	 *            the selection
	 * @param overrideDevMgmCommHandler
	 *            if not null this device management communication should be
	 *            used instead the one derived from the device profile.
	 */
	public void performDeployment(
			final Object[] selection,
			AbstractDeviceManagementCommunicationHandler overrideDevMgmCommHandler) {
		List<Device> devices = new ArrayList<>();
		List<ResourceDeploymentData> resDepData = new ArrayList<>();
		
		for (int i = 0; i < selection.length; i++) {
			Object object = selection[i];
			if (object instanceof Resource) {
				resDepData.add(new ResourceDeploymentData((Resource) object));				
			} else if(object instanceof Device){
				ArrayList<VarDeclaration> parameters = getSelectedDeviceProperties((Device)object);
				if (parameters != null && parameters.size() > 0) {
					devices.add((Device)object);
				}
			}
		}
		DownloadRunnable download = new DownloadRunnable(devices, resDepData, overrideDevMgmCommHandler);
		Shell shell = Display.getDefault().getActiveShell();
		try {
			new ProgressMonitorDialog(Display.getDefault().getActiveShell()).run(true, true, download);
		} catch (InvocationTargetException ex) {
			MessageDialog.openError(shell, "Error", ex.getMessage());
		} catch (InterruptedException ex) {
			MessageDialog.openInformation(shell,
					Messages.DeploymentCoordinator_LABEL_DownloadAborted,
					ex.getMessage());
		}
	}

	public void performDeployment(final Object[] selection) {
		performDeployment(selection, null);
	}

	/**
	 * Gets the deployment executor.
	 * 
	 * @param device
	 *            the device for which a deployment executor should be get
	 * @param overrideComHandler
	 *            if not null this com handler will be given to the executor
	 * 
	 * @return the deployment executor
	 */
	public IDeploymentExecutor getDeploymentExecutor(
			final Device device,
			final AbstractDeviceManagementCommunicationHandler overrideComHandler) {
		if (null == deploymentExecutors) {
			deploymentExecutors = loadDeploymentExecutors();
		}

		for (IDeploymentExecutor idepExec : deploymentExecutors) {
			if (idepExec.supports(device.getProfile())) {
				idepExec.setDeviceManagementCommunicationHandler((null != overrideComHandler) ? overrideComHandler
						: getDevMgmCommunicationHandler(device));
				return (null != idepExec.getDevMgmComHandler()) ? idepExec
						: null;
			}
		}

		return null;
	}

	public IDeploymentExecutor getDeploymentExecutor(final Device device) {
		return getDeploymentExecutor(device, null);
	}

	public static ArrayList<IDeploymentExecutor> loadDeploymentExecutors() {
		ArrayList<IDeploymentExecutor> deploymentExecutors = new ArrayList<IDeploymentExecutor>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				Activator.PLUGIN_ID, "downloadexecutor"); //$NON-NLS-1$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IDeploymentExecutor) {
					deploymentExecutors.add((IDeploymentExecutor) object);
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError(Messages.DeploymentCoordinator_ERROR_Message, corex);
			}
		}
		return deploymentExecutors;
	}

	private AbstractDeviceManagementCommunicationHandler getDevMgmCommunicationHandler(
			Device device) {
		if (null == deviceMangementCommunicationHandlers) {
			loadDeviceManagementCommunicationHandlers();
		}

		// TODO currently we only have one communication handler. Extend this
		// here
		if (deviceMangementCommunicationHandlers.size() > 0) {
			return deviceMangementCommunicationHandlers.get(0);
		}
		return null;
	}

	private void loadDeviceManagementCommunicationHandlers() {
		deviceMangementCommunicationHandlers = new ArrayList<AbstractDeviceManagementCommunicationHandler>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				Activator.PLUGIN_ID, "devicemanagementcommunicationhandler"); //$NON-NLS-1$

		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof AbstractDeviceManagementCommunicationHandler) {
					deviceMangementCommunicationHandlers
							.add((AbstractDeviceManagementCommunicationHandler) object);
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError(Messages.DeploymentCoordinator_ERROR_Message, corex);
			}
		}
	}

	/** The listeners. */
	private final ArrayList<IDeploymentListener> listeners = new ArrayList<IDeploymentListener>();

	/**
	 * Response received x.
	 * 
	 * @param response
	 *            the response
	 * @param source
	 *            the source
	 */
	private void responseReceivedX(final String response, final String source) {
		for (Iterator<IDeploymentListener> iterator = listeners.iterator(); iterator
				.hasNext();) {
			IDeploymentListener listener = iterator.next();
			listener.responseReceived(response, source);
		}
	}

	/**
	 * Post command sent x.
	 * 
	 * @param command
	 *            the command
	 * @param destination
	 *            the destination
	 */
	private void postCommandSentX(final String command, final String destination) {
		for (Iterator<IDeploymentListener> iterator = listeners.iterator(); iterator
				.hasNext();) {
			IDeploymentListener listener = iterator.next();
			listener.postCommandSent(command, destination);
		}
	}

	private void postCommandSentX(final String info, final String destination,
			final String command) {
		for (Iterator<IDeploymentListener> iterator = listeners.iterator(); iterator
				.hasNext();) {
			IDeploymentListener listener = iterator.next();
			listener.postCommandSent(info, destination, command);
		}
	}

	/**
	 * Post command sent x.
	 * 
	 * @param message
	 *            the message unformatted
	 */
	private void postCommandSentX(final String message) {
		for (Iterator<IDeploymentListener> iterator = listeners.iterator(); iterator
				.hasNext();) {
			IDeploymentListener listener = iterator.next();
			listener.postCommandSent(message);
		}
	}

	/**
	 * Finished x.
	 */
	private void finishedX() {
		for (Iterator<IDeploymentListener> iterator = listeners.iterator(); iterator
				.hasNext();) {
			IDeploymentListener listener = iterator.next();
			listener.finished();
		}
	}

	/**
	 * Adds the deployment listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addDeploymentListener(final IDeploymentListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Removes the deployment listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeDeploymentListener(final IDeploymentListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/**
	 * Enable output.
	 * 
	 * @param executor
	 *            the executor
	 */
	public void enableOutput(
			AbstractDeviceManagementCommunicationHandler handler) {
		handler.addDeploymentListener(listener);
	}

	/**
	 * Flush.
	 */
	public void flush() {
		finishedX();
	}

	/**
	 * Disable output.
	 * 
	 * @param executor
	 *            the executor
	 */
	public void disableOutput(
			AbstractDeviceManagementCommunicationHandler handler) {
		handler.removeDeploymentListener(listener);
	}

}
