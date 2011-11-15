package br.com.brasilti.resources.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ElementUtilTest {

	private List enclosedElements;

	private Element element;

	@Before
	public void setUp() {
		this.enclosedElements = new ArrayList();

		this.element = mock(Element.class);
		when(this.element.getKind()).thenReturn(ElementKind.ENUM);
		when(this.element.getEnclosedElements()).thenReturn(this.enclosedElements);
	}

	@Test
	public void deveRetornarUmaListaVaziaQuandoAEnumEstiverVazia() {
		List<Element> enumConstants = ElementUtil.getEnumConstants(this.element);
		assertTrue(enumConstants.isEmpty());
	}

	@Test
	public void deveRetornarUmaListaVaziaQuandoAEnumNaoTiverConstantes() {
		Element field = mock(Element.class);
		when(field.getKind()).thenReturn(ElementKind.FIELD);

		this.enclosedElements.add(field);

		List<Element> enumConstants = ElementUtil.getEnumConstants(this.element);
		assertTrue(enumConstants.isEmpty());
	}

	@Test
	public void deveRetornarUmaListaPreenchidaQuandoAEnumTiverConstantes() {
		Element enumConstant = mock(Element.class);
		when(enumConstant.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);

		Element field = mock(Element.class);
		when(field.getKind()).thenReturn(ElementKind.FIELD);

		this.enclosedElements.add(enumConstant);
		this.enclosedElements.add(field);

		List<Element> enumConstants = ElementUtil.getEnumConstants(this.element);
		assertEquals(1, enumConstants.size());
		assertEquals(ElementKind.ENUM_CONSTANT, enumConstants.get(0).getKind());
	}

	@After
	public void tearDown() {
		this.enclosedElements = null;
	}

}
