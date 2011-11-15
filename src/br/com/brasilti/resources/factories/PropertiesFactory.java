package br.com.brasilti.resources.factories;

import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;

import br.com.brasilti.resources.annotations.Template;
import br.com.brasilti.resources.utils.ElementUtil;

public class PropertiesFactory {

	private static final String PARAMETER_PATTERN = "\\{[^\\{\\}]*\\}";

	private PropertiesFactory() {

	}

	public static Properties create(Element enumeration) {
		Properties properties = new Properties();

		List<Element> enumConstants = ElementUtil.getEnumConstants(enumeration);
		for (Element enumConstant : enumConstants) {
			String key = enumConstant.getSimpleName().toString();
			key = key.replace("_", ".").toLowerCase();

			Template annotation = enumConstant.getAnnotation(Template.class);
			String value = annotation.value();

			Pattern pattern = Pattern.compile(PARAMETER_PATTERN);
			Matcher matcher = pattern.matcher(value);

			int i = 0;
			while (matcher.find()) {
				value = value.replace(matcher.group(), "{" + i++ + "}");
			}

			properties.put(key, value);
		}

		return properties;
	}

}
