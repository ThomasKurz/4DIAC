/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.comm;

import java.util.List;

import org.eclipse.fordiac.ide.util.comm.channels.ChannelManager;
import org.eclipse.fordiac.ide.util.comm.channels.IChannel;
import org.eclipse.fordiac.ide.util.comm.channels.IIecReceivable;
import org.eclipse.fordiac.ide.util.comm.channels.IIecSender;
import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_ANY;
import org.eclipse.fordiac.ide.util.comm.exceptions.CommException;


public class TCPClient extends IIecNetCommRcv implements IIecSender,IIecReceivable {

	public TCPClient() {
		super();
	}
	
	public boolean Initialize(String pa_sID) {
		boolean retval = Initialize(pa_sID, IChannel.TCP);
		if (retval) {
			m_bInitialized=true;
			m_sID=pa_sID;
		}
		return retval;
	}
	
	public void SendIECData(List<IEC_ANY> sendData) throws CommException{
			ChannelManager.send(m_sID, IChannel.TCP, sendData);
		
	}
}