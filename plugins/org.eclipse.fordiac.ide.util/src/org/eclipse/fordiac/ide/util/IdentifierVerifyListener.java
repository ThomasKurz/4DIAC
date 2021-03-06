/*******************************************************************************
 * Copyright (c) 2009, 2011, 2014, 2017 Profactor GbmH, fortiss GmbH
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
package org.eclipse.fordiac.ide.util;

import org.eclipse.fordiac.ide.model.IdentifierVerifyer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

/**
 * The listener interface for receiving identifierVerify events.
 * The class that is interested in processing a identifierVerify
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addIdentifierVerifyListener<code> method. When
 * the identifierVerify event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see IdentifierVerifyEvent
 */
public class IdentifierVerifyListener implements VerifyListener {

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.VerifyListener#verifyText(org.eclipse.swt.events.VerifyEvent)
	 */
	@Override
	public void verifyText(VerifyEvent event) {
		if (!event.doit) { // other verifylisteners which are first can
			// already
			// set it
			return;
		}
		// Get the character typed
		char myChar = event.character;
		String text = ((Text) event.widget).getText();
		if (event.keyCode == SWT.DEL || event.keyCode == SWT.BS) {
			return;
		}
		if ((myChar == '_' || IdentifierVerifyer.isIdentifierChar(myChar)) && text.length() == 0) {
			event.doit = true;
		} else if (myChar == SWT.NULL) {
			event.doit = true;
		} else if ((IdentifierVerifyer.isIdentifierChar(myChar) || Character.isDigit(myChar) || myChar == '_')
				&& text.length() >= 1) {
			event.doit = true;
		} else {
			event.doit = false;
		}
	}

	
	
}
