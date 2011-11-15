package br.com.brasilti.resources.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.brasilti.resources.annotations.Template;
import br.com.brasilti.resources.enums.ErrorEnum;
import br.com.brasilti.resources.exceptions.ResourceException;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ElementValidatorTest {

	private Element element;

	private ElementValidator validator;

	@Before
	public void setUp() {
		Template template = mock(Template.class);
		when(template.value()).thenReturn("Value");

		Element enumConstant = mock(Element.class);
		when(enumConstant.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
		when(enumConstant.getAnnotation(Template.class)).thenReturn(template);

		Element field = mock(Element.class);
		when(field.getKind()).thenReturn(ElementKind.FIELD);

		List enclosedElements = new ArrayList();
		enclosedElements.add(enumConstant);
		enclosedElements.add(field);

		this.element = mock(Element.class);
		when(this.element.getKind()).thenReturn(ElementKind.ENUM);
		when(this.element.getEnclosedElements()).thenReturn(enclosedElements);

		this.validator = new ElementValidator();
	}

	@Test(expected = ResourceException.class)
	public void deveLancarExcecaoQuandoOElementoNaoForUmaEnumException() throws ResourceException {
		when(this.element.getKind()).thenReturn(ElementKind.CLASS);

		this.validator.validate(this.element);
	}

	@Test
	public void deveLancarExcecaoQuandoOElementoNaoForUmaEnum() {
		when(this.element.getKind()).thenReturn(ElementKind.CLASS);

		try {
			this.validator.validate(this.element);
		} catch (ResourceException e) {
			assertEquals(ErrorEnum.INVALID_ELEMENT_KIND.getMessage(this.element.getSimpleName()), e.getMessage());
		}
	}

	@Test(expected = ResourceException.class)
	public void deveLancarExcecaoQuandoAConstanteDaEnumNaoEstiverAnotadaException() throws ResourceException {
		Element enclosedElement = mock(Element.class);
		when(enclosedElement.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);

		List enclosedElements = new ArrayList();
		enclosedElements.add(enclosedElement);

		when(this.element.getEnclosedElements()).thenReturn(enclosedElements);

		this.validator.validate(this.element);
	}

	@Test
	public void deveLancarExcecaoQuandoAConstanteDaEnumNaoEstiverAnotada() {
		Element enclosedElement = mock(Element.class);
		when(enclosedElement.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);

		List enclosedElements = new ArrayList();
		enclosedElements.add(enclosedElement);

		when(this.element.getEnclosedElements()).thenReturn(enclosedElements);

		try {
			this.validator.validate(this.element);
		} catch (ResourceException e) {
			assertEquals(ErrorEnum.INVALID_ELEMENT.getMessage(this.element.getSimpleName()), e.getMessage());
		}
	}

	@Test(expected = ResourceException.class)
	public void deveLancarExcecaoQuandoOValorDaAnotacaoDaConstanteDaEnumForVazioException() throws ResourceException {
		Template template = mock(Template.class);
		when(template.value()).thenReturn("");

		Element enclosedElement = mock(Element.class);
		when(enclosedElement.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
		when(enclosedElement.getAnnotation(Template.class)).thenReturn(template);

		List enclosedElements = new ArrayList();
		enclosedElements.add(enclosedElement);

		when(this.element.getEnclosedElements()).thenReturn(enclosedElements);

		this.validator.validate(this.element);
	}

	@Test
	public void deveLancarExcecaoQuandoOValorDaAnotacaoDaConstanteDaEnumForVazio() {
		Template template = mock(Template.class);
		when(template.value()).thenReturn("");

		Element enclosedElement = mock(Element.class);
		when(enclosedElement.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
		when(enclosedElement.getAnnotation(Template.class)).thenReturn(template);

		List enclosedElements = new ArrayList();
		enclosedElements.add(enclosedElement);

		when(this.element.getEnclosedElements()).thenReturn(enclosedElements);

		try {
			this.validator.validate(this.element);
		} catch (ResourceException e) {
			assertEquals(ErrorEnum.INVALID_VALUE.getMessage(enclosedElement.getSimpleName()), e.getMessage());
		}
	}

	@Test
	public void naoDeveLancarExcecaoQuandoOElementoForUmaEnumValida() {
		try {
			this.validator.validate(this.element);
		} catch (ResourceException e) {
			fail(e.getMessage());
		}
	}

	@After
	public void tearDown() {
		this.validator = null;
	}

}
