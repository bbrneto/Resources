package br.com.brasilti.resources.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;

import org.junit.Before;
import org.junit.Test;

import br.com.brasilti.resources.annotations.Template;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PropertiesFactoryTest {

	private Template template;

	private List enclosedElements;

	private Element element;

	@Before
	public void setUp() {
		this.template = mock(Template.class);
		when(this.template.value()).thenReturn("Value {PARAM01} {PARAM02}.");

		Name name = mock(Name.class);
		when(name.toString()).thenReturn("ENUM_CONSTANT_01");

		Element enumConstant = mock(Element.class);
		when(enumConstant.getSimpleName()).thenReturn(name);
		when(enumConstant.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
		when(enumConstant.getAnnotation(Template.class)).thenReturn(this.template);

		Element field = mock(Element.class);
		when(field.getKind()).thenReturn(ElementKind.FIELD);

		this.enclosedElements = new ArrayList();
		this.enclosedElements.add(enumConstant);
		this.enclosedElements.add(field);

		this.element = mock(Element.class);
		when(this.element.getKind()).thenReturn(ElementKind.ENUM);
		when(this.element.getEnclosedElements()).thenReturn(this.enclosedElements);
	}

	@Test
	public void deveCriarUmPropertiesVazioQuandoAEnumEstiverVazia() {
		this.element = mock(Element.class);
		when(this.element.getKind()).thenReturn(ElementKind.ENUM);

		Properties properties = PropertiesFactory.create(this.element);

		assertTrue(properties.isEmpty());
	}

	@Test
	public void deveCriarUmPropertiesQuandoOValorDaAnotacaoAtTemplateNaoTiverParametro() {
		when(this.template.value()).thenReturn("Value.");

		Properties properties = PropertiesFactory.create(this.element);

		assertEquals(1, properties.size());
		assertEquals("Value.", properties.getProperty("enum.constant.01"));
	}

	@Test
	public void deveCriarUmPropertiesQuandoOValorDaAnotacaoAtTemplateTiverParametroVazio() {
		when(this.template.value()).thenReturn("Value {}.");

		Properties properties = PropertiesFactory.create(this.element);

		assertEquals(1, properties.size());
		assertEquals("Value {0}.", properties.getProperty("enum.constant.01"));
	}

	@Test
	public void deveCriarUmPropertiesQuandoOValorDaAnotacaoAtTemplateTiverUmParametro() {
		when(this.template.value()).thenReturn("Value {PARAM}.");

		Properties properties = PropertiesFactory.create(this.element);

		assertEquals(1, properties.size());
		assertEquals("Value {0}.", properties.getProperty("enum.constant.01"));
	}

	@Test
	public void deveCriarUmPropertiesQuandoOValorDaAnotacaoAtTemplateTiverMaisDeUmParametro() {
		Properties properties = PropertiesFactory.create(this.element);

		assertEquals(1, properties.size());
		assertEquals("Value {0} {1}.", properties.getProperty("enum.constant.01"));
	}

	@Test
	public void deveCriarUmPropertiesQuandoAEnumTiverMaisDeUmaConstante() {
		Template template = mock(Template.class);
		when(template.value()).thenReturn("Value {PARAM01}.");

		Name name = mock(Name.class);
		when(name.toString()).thenReturn("ENUM_CONSTANT_02");

		Element enumConstant = mock(Element.class);
		when(enumConstant.getSimpleName()).thenReturn(name);
		when(enumConstant.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
		when(enumConstant.getAnnotation(Template.class)).thenReturn(template);

		this.enclosedElements.add(enumConstant);

		Properties properties = PropertiesFactory.create(this.element);

		assertEquals(2, properties.size());
		assertEquals("Value {0} {1}.", properties.getProperty("enum.constant.01"));
		assertEquals("Value {0}.", properties.getProperty("enum.constant.02"));
	}

}
