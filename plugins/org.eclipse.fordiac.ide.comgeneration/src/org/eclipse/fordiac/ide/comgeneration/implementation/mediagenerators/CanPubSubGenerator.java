/*******************************************************************************
 * Copyright (c) 2014 - 2015 Luka Lednicki, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators;

import org.eclipse.fordiac.ide.comgeneration.implementation.ChannelEnd;
import org.eclipse.fordiac.ide.comgeneration.implementation.CommunicationMediaInfo;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class CanPubSubGenerator extends AbstractMediaSpecificGenerator {
	public static String[] paletteEntrySourceLocal = {"net/PUBL_0", "net/PUBL_1", "net/PUBL_2", "net/PUBL_3", "net/PUBL_4"};
	public static String[] paletteEntryDestinationsLocal = {"net/SUBL_0", "net/SUBL_1", "net/SUBL_2", "net/SUBL_3", "net/SUBL_4"};
	public static String[] paletteEntrySource = {"net/PUBLISH_0", "net/PUBLISH_1", "net/PUBLISH_2", "net/PUBLISH_3", "net/PUBLISH_4"};
	public static String[] paletteEntryDestinations = {"net/SUBSCRIBE_0", "net/SUBSCRIBE_1", "net/SUBSCRIBE_2", "net/SUBSCRIBE_3", "net/SUBSCRIBE_4"};

	public static String PROTOCOL_ID = "CanPubSub";
	
	public static int DEFAULT_START_MESSAGE_ID = 500;
	
	
	private int startMessageId; 
	private int currentMessageId;
	
	public CanPubSubGenerator(Palette palette) {
		super(palette);
		startMessageId = DEFAULT_START_MESSAGE_ID;
		reset();
	}

	@Override
	public String getMediaType() {
		return "Ethernet";
	}

	@Override
	public String getProtocolId() {
		return PROTOCOL_ID;
	}
	
	public FBTypePaletteEntry getPaletteType(ChannelEnd end, int numDataPorts, boolean local) {
		String[] palletEntries;
		
		if (local) {
			palletEntries = (end == ChannelEnd.SOURCE) ? paletteEntrySourceLocal : paletteEntryDestinationsLocal;
		} else {
			palletEntries = (end == ChannelEnd.SOURCE) ? paletteEntrySource : paletteEntryDestinations;
		}
		
		String[] paletteEntryPath = palletEntries[numDataPorts].split("/");
		
		PaletteGroup group = palette.getRootGroup();
		
		for (int i = 0; i < paletteEntryPath.length; i++) {
			String currentPath = paletteEntryPath[i];
			if (i == paletteEntryPath.length - 1) {
				for (PaletteEntry entry : group.getEntries()) {
					if (entry instanceof FBTypePaletteEntry) {
						FBTypePaletteEntry fbTypeEntry = (FBTypePaletteEntry) entry;
						if (fbTypeEntry.getLabel().equals(currentPath)) {
							return fbTypeEntry;
						}
					}
				}
				
				System.err.println("FB type palette entry '" + currentPath + "' not found!");
				
			} else {
				boolean foundSubGroup = false;
				for (PaletteGroup subGroup : group.getSubGroups()) {
					if (subGroup.getLabel().equals(currentPath)) {
						group = subGroup;
						foundSubGroup = true;
						break;
					}
				}
				if (!foundSubGroup) {
					System.err.println("No subgroup '" + currentPath + "'!");
					return null;
				} 
			}
			
		}
		
		return null;
	}
	
	

	@Override
	public void configureFBs(FB sourceFB, FB destinationFB, CommunicationMediaInfo mediaInfo) {
		VarDeclaration sourceQI;
		VarDeclaration sourceId;
		VarDeclaration destinationQI;
		VarDeclaration destinationId;
		
		if (mediaInfo.getSourceLink().getDevice().equals(mediaInfo.getDestinationLink().getDevice())) {
			sourceQI = null;
			sourceId = sourceFB.getInterface().getInputVars().get(0);
			destinationQI = null;
			destinationId = destinationFB.getInterface().getInputVars().get(0);
		} else {
			sourceQI = sourceFB.getInterface().getInputVars().get(0);
			sourceId = sourceFB.getInterface().getInputVars().get(1);
			destinationQI = destinationFB.getInterface().getInputVars().get(0);
			destinationId = destinationFB.getInterface().getInputVars().get(1);
		}
		
		String sourceValue = sourceId.getValue().getValue(); 
		if (sourceValue == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("CAN:");
			sb.append(mediaInfo.getSegment().getName());
			sb.append(":");
			sb.append(currentMessageId);
			currentMessageId++;
			sourceValue = sb.toString();
			sourceId.getValue().setValue(sourceValue);
			if (sourceQI != null) {
				sourceQI.getValue().setValue("1");
			}
		}
		
		destinationId.getValue().setValue(sourceValue);
		if (destinationQI != null) {
			destinationQI.getValue().setValue("1");
		}
	}
	
	@Override
	public VarDeclaration getTargetInputData(int index, FB fb) {
		String dataName = "SD_" + (index + 1);
		for (VarDeclaration var : fb.getInterface().getInputVars()) {
			if (var.getName().equals(dataName)) {
				return var;
			}
		}
		return null;
	}

	@Override
	public VarDeclaration getTargetOutputData(int index, FB fb) {
		String dataName = "RD_" + (index + 1);
		for (VarDeclaration var : fb.getInterface().getInputVars()) {
			if (var.getName().equals(dataName)) {
				return var;
			}
		}
		return null;
	}

	@Override
	public void reset() {
		currentMessageId = startMessageId;
	}
	
	public void reset(int startMessageId) {
		this.startMessageId = startMessageId;
		reset();
	}

	@Override
	public boolean isSeparatedSource() {
		return false;
	}
}
