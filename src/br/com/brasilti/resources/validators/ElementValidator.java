package br.com.brasilti.resources.validators;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import br.com.brasilti.resources.annotations.Template;
import br.com.brasilti.resources.enums.ErrorEnum;
import br.com.brasilti.resources.exceptions.ResourceException;
import br.com.brasilti.resources.utils.ElementUtil;

public class ElementValidator {

	public void validate(Element element) throws ResourceException {
		if (!element.getKind().equals(ElementKind.ENUM)) {
			throw new ResourceException(ErrorEnum.INVALID_ELEMENT_KIND, element.getSimpleName());
		}

		List<Element> enumConstants = ElementUtil.getEnumConstants(element);
		for (Element enumConstant : enumConstants) {
			Template annotation = enumConstant.getAnnotation(Template.class);
			if (annotation == null) {
				throw new ResourceException(ErrorEnum.INVALID_ELEMENT, element.getSimpleName());
			}

			if (annotation.value().isEmpty()) {
				throw new ResourceException(ErrorEnum.INVALID_VALUE, enumConstant.getSimpleName());
			}
		}
	}

}
