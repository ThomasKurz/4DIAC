/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.gef.commands.Command;

public class AttributeChangeCommand extends Command {
	private Attribute attribute;
	private String name;
	private String nameOld;
	private String value;
	private String valueOld;
	private String comment;
	private String commentOld;
	private BaseType1 type;
	private BaseType1 typeOld;
	
	public AttributeChangeCommand(Attribute attribute, String name, String value, BaseType1 type, String comment) {
		this.attribute = attribute;
		this.comment = comment;
		this.value = value;
		this.name = name;
		this.type = type;
	}
	
	@Override
	public boolean canExecute() {
		return null != attribute && (null != name || null != value || null != comment || (null == attribute.getAttributeDeclaration() && null != type));
	}

	@Override
	public void execute() {
		nameOld = attribute.getName();
		valueOld = attribute.getValue();
		commentOld = attribute.getComment();
		typeOld = attribute.getType();
		redo();
	}

	@Override
	public void undo() {
		if(null != name) {			
			attribute.setName(nameOld);
		}
		if(null != comment) {			
			attribute.setComment(commentOld);
		}
		if(null != value) {			
			attribute.setValue(valueOld);
		}
		if(null != type) {			
			attribute.setType(typeOld);
		}
	}

	@Override
	public void redo() {
		if(null != name) {			
			attribute.setName(name);
		}
		if(null != comment) {			
			attribute.setComment(comment);
		}
		if(null != value) {			
			attribute.setValue(value);
		}
		if(null != type) {			
			attribute.setType(type);
		}
	}

}
