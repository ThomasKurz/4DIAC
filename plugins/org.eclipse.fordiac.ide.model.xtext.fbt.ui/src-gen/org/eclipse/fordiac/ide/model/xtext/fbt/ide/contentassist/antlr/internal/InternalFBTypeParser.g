/*
 * generated by Xtext 2.11.0
 */
parser grammar InternalFBTypeParser;

options {
	tokenVocab=InternalFBTypeLexer;
	superClass=AbstractInternalContentAssistParser;
}

@header {
package org.eclipse.fordiac.ide.model.xtext.fbt.ide.contentassist.antlr.internal;
import java.util.Map;
import java.util.HashMap;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.eclipse.fordiac.ide.model.xtext.fbt.services.FBTypeGrammarAccess;

}
@members {
	private FBTypeGrammarAccess grammarAccess;
	private final Map<String, String> tokenNameToValue = new HashMap<String, String>();
	
	{
	}

	public void setGrammarAccess(FBTypeGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		String result = tokenNameToValue.get(tokenName);
		if (result == null)
			result = tokenName;
		return result;
	}
}

// Entry rule entryRuleLibraryElement
entryRuleLibraryElement
:
{ before(grammarAccess.getLibraryElementRule()); }
	 ruleLibraryElement
{ after(grammarAccess.getLibraryElementRule()); } 
	 EOF 
;

// Rule LibraryElement
ruleLibraryElement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getLibraryElementAccess().getNameAssignment()); }
		(rule__LibraryElement__NameAssignment)
		{ after(grammarAccess.getLibraryElementAccess().getNameAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LibraryElement__NameAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLibraryElementAccess().getNameIDTerminalRuleCall_0()); }
		RULE_ID
		{ after(grammarAccess.getLibraryElementAccess().getNameIDTerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}
