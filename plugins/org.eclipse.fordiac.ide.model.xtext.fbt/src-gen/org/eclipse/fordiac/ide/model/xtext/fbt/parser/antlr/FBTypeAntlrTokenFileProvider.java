/*
 * generated by Xtext 2.11.0
 */
package org.eclipse.fordiac.ide.model.xtext.fbt.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class FBTypeAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("org/eclipse/fordiac/ide/model/xtext/fbt/parser/antlr/internal/InternalFBTypeParser.tokens");
	}
}
