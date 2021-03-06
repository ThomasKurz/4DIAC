/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Ingo Hegny, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.export.utils.CompareEditorOpenerUtil;
import org.eclipse.fordiac.ide.export.utils.ICompareEditorOpener;
import org.eclipse.fordiac.ide.export.utils.IExportFilter;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.util.Utils;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class ExportFilter.
 */
public abstract class ExportFilter implements IExportFilter {

	protected Document document;

	protected Element docel;

	protected String destDir;

	protected PrintWriter pwCPP;

	protected PrintWriter pwH;

	protected String name;

	protected Map<String, VarDefinition> vars = new HashMap<String, VarDefinition>();

	protected int dataInCount;

	protected int dataOutCount;
	protected int internalCount;
	protected int FBCount;

	protected List<String> inputVars = new ArrayList<String>();
	protected List<String> outputVars = new ArrayList<String>();
	protected List<VarDefinition> internalVars = new ArrayList<VarDefinition>();
	
	protected List<FBDefinition> FBs = new ArrayList<FBDefinition>();

	protected Set<String> Internal2InterfaceEventConns = new HashSet<String>();

	protected List<Element> EventConn = new ArrayList<Element>();

	protected Set<String> EventConnHash = new HashSet<String>();

	protected List<Element> DataConn = new ArrayList<Element>();

	protected Set<String> DataConnHash = new HashSet<String>();

	protected Set<String> Interface2InternalDataConns = new HashSet<String>();

	protected Set<String> Internal2InterfaceDataConns = new HashSet<String>();

	protected List<String> forteEmitterErrors = new ArrayList<String>();

	protected List<String> forteEmitterWarnings = new ArrayList<String>();

	protected List<String> forteEmitterInfos = new ArrayList<String>();

	protected LibraryElement libraryType;

	/**
	 * The Class VarDefinition.
	 */
	public class VarDefinition {

		/** The name. */
		public String name;

		/** The type. */
		public String type;

		/** The array size. */
		public int arraySizeValue;
		

		/** The initial value. */
		public String initialValue;
		
		/** The number of the var e.g., 0 means the first input */
		public int count;
		
		/** defines if it is an input: 0, output: 1, or an internal var: 2 
		 * FIXME change to enum */
		public int inoutinternal;
		
		
		public VarDefinition(Element el, int count, int inoutinternal){
			name = el.getAttribute("Name");
			type = el.getAttribute("Type");
			String arraySize = el.getAttribute("ArraySize");
			int val = 0;
			
			if(null != arraySize && !"".equals(arraySize)){
				val = Integer.parseInt(arraySize);	
			}
			arraySizeValue = val;
			
			initialValue = el.getAttribute("InitialValue");
			this.count = count;
			this.inoutinternal = inoutinternal;
		}
	}

	protected class FBDefinition {
		public String name;

		public String type;

		public String[] paramName;

		public String[] paramValue;
	}

	protected class ConnDefinition {
		public String source;

		public String destination;
	}

	/**
	 * Converts the given Document element to LibraryElement2.dtd format if
	 * necessary. // OOONEIDA Workbench code!
	 * 
	 * @param docel
	 *          the docel
	 */
	public static void convertToLibraryElement2(final Element docel) {
		// Convert "EVENT" type names to empty strings
		NodeList evts = docel.getElementsByTagName("Event");
		for (int i = 0; i < evts.getLength(); i++) {
			Element el = (Element) evts.item(i);
			if (el.getAttribute("Type").equals("EVENT")) {
				el.setAttribute("Type", "");
			}
		}
		// Convert ECTransitions to Guard/Condition format
		NodeList eihdr = docel.getElementsByTagName("EventInputs");
		if (eihdr.getLength() < 1) {
			return;
		}
		NodeList eis = ((Element) eihdr.item(0)).getElementsByTagName("Event");
		String[] einames = new String[eis.getLength()];
		for (int j = 0; j < einames.length; j++) {
			einames[j] = ((Element) eis.item(j)).getAttribute("Name");
		}
		NodeList nodes = docel.getElementsByTagName("ECTransition");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element tran = (Element) nodes.item(i);
			String cond = tran.getAttribute("Condition").trim();
			if (cond.length() < 1) {
				break;
			}
			tran.removeAttribute("Condition");
			String einame = cond;
			int n = einame.indexOf(' ');
			if (n > 0) {
				einame = einame.substring(0, n);
			}
			n = einame.indexOf('&');
			if (n > 0) {
				einame = einame.substring(0, n);
			}
			for (int j = 0; j < einames.length; j++) {
				if (einame.equals(einames[j])) {
					tran.setAttribute("Event", einame);
					break;
				}
			}
			if (tran.getAttribute("Event").length() > 0) {
				cond = cond.substring(einame.length()).trim();
				if (cond.startsWith("&")) {
					cond = cond.substring(1);
				} else if (cond.startsWith("AND ")) {
					cond = cond.substring(4);
				}
			}
			cond = cond.trim();
			if (cond.length() > 0) {
				tran.setAttribute("Guard", cond);
			}
		}
	}

	/**
	 * Instantiates a new export filter.
	 */
	public ExportFilter() {

	}

	/**
	 * Instantiates a new export filter.
	 * 
	 * @param doc
	 *          the doc
	 * @param destDir
	 *          the dest dir
	 */
	public ExportFilter(final Document doc, final String destDir) {
		document = doc;
		docel = document.getDocumentElement();
		convertToLibraryElement2(docel);
		this.destDir = destDir;
		name = docel.getAttribute("Name");
	}

	@Override
	public void export(IFile typeFile, String destination, boolean forceOverwrite,
			LibraryElement type) throws org.eclipse.fordiac.ide.export.utils.ExportException {
		this.libraryType = type;
		export(typeFile, destination, forceOverwrite);
	}

	/**
	 * Start export.
	 * 
	 * @param overwrite
	 *          the overwrite
	 */
	public void startExport(final boolean overwrite) {
		// clear all vectors & hashtables
		vars = new HashMap<String, VarDefinition>();

		dataInCount = 0;

		dataOutCount = 0;
		internalCount = 0;
		FBCount = 0;

		inputVars = new ArrayList<String>();

		outputVars = new ArrayList<String>();
		internalVars = new ArrayList<VarDefinition>();
		FBs = new ArrayList<FBDefinition>();

		Internal2InterfaceEventConns = new HashSet<String>();

		EventConn = new ArrayList<Element>();

		EventConnHash = new HashSet<String>();

		DataConn = new ArrayList<Element>();

		DataConnHash = new HashSet<String>();

		Interface2InternalDataConns = new HashSet<String>();

		Internal2InterfaceDataConns = new HashSet<String>();

		String name = docel.getAttribute("Name");

		if (destDir != null) {
			String fName = destDir + File.separatorChar + name;
			try {
				File f = new File(fName + ".cpp");
				File h = new File(fName + ".h");
				if ((!overwrite) && (f.exists() || h.exists())) {
					ICompareEditorOpener opener = CompareEditorOpenerUtil.getOpener();
					String msg = MessageFormat
							.format(
									"Overwrite "
											+ name
											+ ".cpp"
											+ " and "
											+ name
											+ ".h. "
											+ ((opener != null) ? "\nMerge will create a backup of the original File and open an editor to merge the files manually!"
													: ""), new Object[] { f.getAbsolutePath() });
					
					MessageDialog msgDiag = new MessageDialog(Display.getDefault().getActiveShell(), "File Exists", null, msg, MessageDialog.QUESTION_WITH_CANCEL, 
							new String[] { JFaceResources.getString(IDialogLabelKeys.YES_LABEL_KEY), "Merge", 
										   JFaceResources.getString(IDialogLabelKeys.CANCEL_LABEL_KEY)}, 0);	
					
					int res = msgDiag.open();
					
					switch(res){ 
					case 0:
						pwCPP = new PrintWriter(new FileOutputStream(fName + ".cpp"));
						pwH = new PrintWriter(new FileOutputStream(fName + ".h"));
						export();
						pwCPP.close();
						pwH.close();
						break;
					case 1:
						performManualMerge(name, fName, f, h, opener);
						break;
					default:
						break;
					}
				} else {
					pwCPP = new PrintWriter(new FileOutputStream(fName + ".cpp"));
					pwH = new PrintWriter(new FileOutputStream(fName + ".h"));
					export();
					pwCPP.close();
					pwH.close();
				}
			} catch (IOException e) {
				forteEmitterErrors.add(" - " + fName + " " + e.getMessage());
			}
		}
	}

	private void performManualMerge(String name, String fName, File f, File h,
			ICompareEditorOpener opener) throws IOException,
			FileNotFoundException {
		// manually merge the files
		if (opener != null) {
			boolean diffs = false;
			File originalCpp = Utils.createBakFile(f); 
			File originalH = Utils.createBakFile(h); 
			File tempcpp = new File(fName + ".cpp");
			File temph = new File(fName + ".h");
			pwCPP = new PrintWriter(new FileOutputStream(tempcpp));
			pwH = new PrintWriter(new FileOutputStream(temph));
			export();
			pwCPP.close();
			pwH.close();

			opener.setName(name);
			opener.setTitle(name + ".cpp");
			opener.setNewFile(tempcpp);
			opener.setOriginalFile(originalCpp);
			if (opener.hasDifferences()) {
				opener.openCompareEditor();
				diffs = true;
			}

			opener.setTitle(name + ".h");
			opener.setNewFile(temph);
			opener.setOriginalFile(originalH);
			if (opener.hasDifferences()) {
				opener.openCompareEditor();
				diffs = true;
			}
			
			if(!diffs){
				//there where no differences inform the user
				MessageDialog.openInformation(Display.getDefault().getActiveShell(), "No Differences",
						"There where no differences between the orignal file and the newly generated one!");
			}
		}
	}

	private void export() {
		exportHeader();

		// determine the type of library entry to emit
		String type = docel.getTagName();
		if (type.equals("ResourceType")) {
			// emit the header for a resource type
			exportRES();
		} else if (type.equals("DeviceType")) {
			// emit the header for a device type
			exportDevice();
		} else if ((type.equals("FBType")) || (type.equals("AdapterType"))) {
			exportFB();
		}
		

		exportClosingCode();
	}

	private void exportRES() {
		exportResStarter();
		exportResConstructor();
	}

	private void exportDevice() {
		exportDeviceStarter();
		exportDeviceConstructor();
	}

	private void exportFB() {
		exportFBStarter();

		NodeList l1 = docel.getElementsByTagName("InterfaceList");
		org.w3c.dom.Node node;
		Element el;
		if (0 != l1.getLength()) {
			node = l1.item(0);
			if (node instanceof Element) {
				el = (Element) node;
				exportFBInterface();
			}
		}

		l1 = docel.getElementsByTagName("BasicFB");
		if (0 != l1.getLength()) {
			node = l1.item(0);
			if (node instanceof Element) {
				el = (Element) node;
				exportBasicFB(el.getChildNodes());
			}
		} else {
			l1 = docel.getElementsByTagName("FBNetwork");
			if (0 != l1.getLength()) {
				node = l1.item(0);
				if (node instanceof Element) {
					el = (Element) node;
					exportFBNetwork(el.getChildNodes());
				}
			} else if(libraryType instanceof FBType){
				exportSIFBExecuteEvent();
			}

		}

		exportFBConstructor();
	}

	private void exportFBInterface() {
		NodeList l1 = docel.getElementsByTagName("InputVars");
		org.w3c.dom.Node node;
		if (0 != l1.getLength()) {
			node = l1.item(0);
			exportVarInputs((Element) node);
		}

		l1 = docel.getElementsByTagName("OutputVars");
		if (0 != l1.getLength()) {
			node = l1.item(0);
			exportVarOutputs((Element) node);
		}

		l1 = docel.getElementsByTagName("EventInputs");
		if (0 != l1.getLength()) {
			node = l1.item(0);
			exportEvents("EventInput", ((Element) node).getChildNodes(), inputVars);
		}

		l1 = docel.getElementsByTagName("EventOutputs");
		if (0 != l1.getLength()) {
			node = l1.item(0);
			exportEvents("EventOutput", ((Element) node).getChildNodes(), outputVars);
		} else {
			handleNotPresentEOTag();
		}

		l1 = docel.getElementsByTagName("InternalVars");
		if (l1.getLength() != 0) {
			node = l1.item(0);
			exportVarInternals((Element) node);
		}
		exportFBInterfaceSpec();
	}

	private void exportFBNetworkConnections() {
		EventConn.clear();
		EventConnHash.clear();
		DataConn.clear();
		DataConnHash.clear();
		NodeList l1 = docel.getElementsByTagName("EventConnections");
		if (l1.getLength() > 0) {
			org.w3c.dom.Node node = l1.item(0);
			NodeList Conns = node.getChildNodes();
			for (int i = 0; i < Conns.getLength(); ++i) {
				node = Conns.item(i);
				if (node instanceof Element) {
					Element el = (Element) node;
					if (el.getNodeName().equals("Connection")) {
						EventConn.add(el);
						if (-1 == (el.getAttribute("Destination")).indexOf('.')) {
							Internal2InterfaceEventConns.add(el.getAttribute("Source"));
						} else {
							EventConnHash.add(el.getAttribute("Source"));
						}
					}
				}
			}
			exportEC();
		}

		l1 = docel.getElementsByTagName("DataConnections");
		if (l1.getLength() > 0) {
			Node node = l1.item(0);
			NodeList Conns = node.getChildNodes();
			for (int i = 0; i < Conns.getLength(); ++i) {
				node = Conns.item(i);
				if (node instanceof Element) {
					Element el = (Element) node;
					if (el.getNodeName().equals("Connection")) {
						DataConn.add(el);
						if (-1 == (el.getAttribute("Source")).indexOf('.')) {
							Interface2InternalDataConns.add(el.getAttribute("Source"));
						} else if (-1 == (el.getAttribute("Destination")).indexOf('.')) {
							Internal2InterfaceDataConns.add(el.getAttribute("Source"));
						} else {
							DataConnHash.add(el.getAttribute("Source"));
						}
					}
				}
			}
			exportDC();
		}
		exportFBNetworkInternalInterface();
	}

	private void exportBasicFB(final NodeList basicFBNodes) {
		Element eccNode = null;
		int len = basicFBNodes.getLength();
		for (int i = 0; i < len; ++i) {
			org.w3c.dom.Node node = basicFBNodes.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("ECC")) {
					eccNode = el;
				} else if (el.getNodeName().equals("Algorithm")) {
					NodeList nodes = el.getChildNodes();
					int alglen = nodes.getLength();
					for (int ii = 0; ii < alglen; ++ii) {
						org.w3c.dom.Node node2 = nodes.item(ii);
						if (node2 instanceof Element) {
							exportAlgorithm(el.getAttribute("Name"), ((Element) node2)
									.getNodeName(), ((Element) node2).getAttribute("Text"));
						}
					}
				}
			}
		}

		if (eccNode != null) {
			exportECC(eccNode);
		}
	}

	private void exportFBNetwork(final NodeList fbnNodes) {
		int len = fbnNodes.getLength();
		FBCount = 0;
		FBs.clear();
		for (int i = 0; i < len; i++) {
			org.w3c.dom.Node node = fbnNodes.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("FB")) {
					++FBCount;
					FBDefinition newFBDef = new FBDefinition();
					newFBDef.type = el.getAttribute("Type");
					newFBDef.name = el.getAttribute("Name");
					NodeList parameterNodes = el.getChildNodes();
					// FIX gebenh, 24th September 2008 -> dynamicaly create list
					// to avoid a fixed size array where elements can be null
					List<String> paramNames = new ArrayList<String>();
					List<String> paramValues = new ArrayList<String>();
					
					for (int j = 0; j < parameterNodes.getLength(); j++) {
						org.w3c.dom.Node nodeParam = parameterNodes.item(j);
						if (nodeParam instanceof Element) {
							Element parameter = (Element) nodeParam;
							paramNames.add(parameter.getAttribute("Name"));
							paramValues.add(parameter.getAttribute("Value"));
						}
					}
					newFBDef.paramName = paramNames
							.toArray(new String[paramNames.size()]);
					newFBDef.paramValue = paramValues.toArray(new String[paramNames
							.size()]);
					FBs.add(newFBDef);
				}
			}
		}
		exportFBVar();
		exportFBNetworkConnections();
		exportCompFBExecuteEventMethod();
		// exportFBManagedObjectMethods();
	}

	protected void exportVarInputs(final Element varInputs) {
		exportVarNameArrays("DataInput", varInputs.getChildNodes());
		NodeList l1 = varInputs.getChildNodes();
		int len = l1.getLength();
		dataInCount = 0;
		for (int i = 0; i < len; i++) {
			org.w3c.dom.Node node = l1.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("VarDeclaration")) {					
					VarDefinition newVarDef = new VarDefinition(el, dataInCount, 0);
					inputVars.add(newVarDef.name);
					vars.put(newVarDef.name, newVarDef);
					exportVariable(newVarDef);
					++dataInCount;
				}
			}
		}
	}

	private void exportVarOutputs(final Element varOutputs) {
		exportVarNameArrays("DataOutput", varOutputs.getChildNodes());
		NodeList l1 = varOutputs.getChildNodes();
		int len = l1.getLength();
		dataOutCount = 0;
		for (int i = 0; i < len; i++) {
			org.w3c.dom.Node node = l1.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("VarDeclaration")) {
					VarDefinition newVarDef = new VarDefinition(el, dataOutCount, 1);
					outputVars.add(newVarDef.name);
					vars.put(newVarDef.name, newVarDef);
					exportVariable(newVarDef);
					++dataOutCount;
				}
			}
		}
	}

	private void exportVarInternals(final Element varInternals) {
		exportVarNameArrays("Internals", varInternals.getChildNodes());
		NodeList l1 = varInternals.getChildNodes();
		int len = l1.getLength();
		internalCount = 0;
		for (int i = 0; i < len; i++) {
			org.w3c.dom.Node node = l1.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("VarDeclaration")) {
					VarDefinition newVarDef = new VarDefinition(el, internalCount, 2);
					internalVars.add(newVarDef);
					vars.put(newVarDef.name, newVarDef);
					exportVariable(newVarDef);
					++internalCount;
				}
			}
		}
	}

	protected abstract void exportHeader();

	protected abstract void exportFBStarter();

	protected abstract void exportCompFBExecuteEventMethod();

	protected abstract void exportSIFBExecuteEvent();

	protected abstract void exportFBNetworkInternalInterface();

	protected abstract void exportFBConstructor();

	protected abstract void exportFBManagedObjectMethods();

	protected abstract void exportResStarter();

	protected abstract void exportResConstructor();

	protected abstract void exportDeviceStarter();

	protected abstract void exportDeviceConstructor();

	protected abstract void exportClosingCode();

	protected abstract void exportVarNameArrays(String namePrefix, NodeList nodes);

	protected abstract void exportEvents(String namePrefix, NodeList nodes,
			List<String> varNames);

	protected abstract void exportAlgorithm(String algName, String type,
			String src);

	protected abstract void exportVariable(VarDefinition newVarDef);

	protected abstract void exportFBVar();

	protected abstract void exportEC();

	protected abstract void exportDC();

	protected abstract void exportECC(Element eccNode);

	protected abstract void exportFBInterfaceSpec();

	protected abstract void handleNotPresentEOTag();

	// protected abstract void exportAlgFBD(String name, NodeList FBDNodes); //
	// currently not supported

	/**
	 * Trim string.
	 * 
	 * @param value
	 *          the value
	 * 
	 * @return the string
	 */
	public static String trimSTRING(String value) {
		Character trimValue1 = new Character('"');
		Character trimValue2 = new Character('\'');

		if (value.startsWith(trimValue1.toString())
				|| value.startsWith(trimValue2.toString())) {
			value = value.substring(1);
		}
		if (value.endsWith(trimValue1.toString())
				|| value.endsWith(trimValue2.toString())) {
			value = value.substring(0, value.length() - 1);
		}

		return value != null ? value : "";
	}

	@Override
	public List<String> getErrors() {
		return forteEmitterErrors;
	}

	@Override
	public List<String> getWarnings() {
		return forteEmitterWarnings;
	}

	@Override
	public List<String> getInfos() {
		return forteEmitterInfos;
	}
}
