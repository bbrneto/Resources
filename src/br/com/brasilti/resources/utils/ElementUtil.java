package br.com.brasilti.resources.utils;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class ElementUtil {

	private ElementUtil() {

	}

	public static List<Element> getEnumConstants(Element enumeration) {
		List<Element> enumConstants = new ArrayList<Element>();

		List<? extends Element> enclosedElements = enumeration.getEnclosedElements();
		for (Element enclosedElement : enclosedElements) {
			if (enclosedElement.getKind().equals(ElementKind.ENUM_CONSTANT)) {
				enumConstants.add(enclosedElement);
			}
		}

		return enumConstants;
	}

}
