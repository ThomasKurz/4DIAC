/*
* generated by Xtext
*/
package org.eclipse.fordiac.ide.model.structuredtext.ui.labeling

import com.google.inject.Inject
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#labelProvider
 */
class StructuredTextLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	def text(VarDeclaration decl)
		'''«decl.name» [«decl.typeName»]'''

	def image(VarDeclaration decl) {
		if(decl.eContainer instanceof BasicFBType && (decl.eContainer as BasicFBType).internalVars.contains(decl)) {
			FordiacImage.ICON_Data.imageDescriptor
		}
		else if(decl.isIsInput) {
			FordiacImage.ICON_DataInput.imageDescriptor
		}
		else {
			FordiacImage.ICON_DataOutput.imageDescriptor
		}
	}
}
