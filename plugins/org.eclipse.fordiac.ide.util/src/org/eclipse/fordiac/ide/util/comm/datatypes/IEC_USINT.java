/*******************************************************************************
 * Copyright (c) 2011, 2013, 2014 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Oliver Hummer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.comm.datatypes;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.fordiac.ide.util.Activator;

public class IEC_USINT extends IEC_INT {
	
	protected static final int BOUNDS_MASK = 0xFF00;
	
	public IEC_USINT(){
		super();
	}
	
	public IEC_USINT(int initial){
		super(initial);
	}
	
	public IEC_USINT(DataInputStream in) {
		super(in);
	}

	@Override
	public void decodeValueFrom(DataInputStream in) {
		try {
			value = (short) in.readUnsignedByte();
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}
	
	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.USINT));
		return retval;
	}

	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut=new ByteArrayOutputStream();
		DataOutputStream DOS=new DataOutputStream(myOut);
		
		try {
			DOS.writeByte(value);
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return myOut.toByteArray();
	}
	
	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_USINT)source).getValue();
			retval=true; 
		}
		return retval;
	}
	
}
