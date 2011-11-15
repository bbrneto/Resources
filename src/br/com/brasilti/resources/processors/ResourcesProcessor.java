package br.com.brasilti.resources.processors;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import br.com.brasilti.resources.exceptions.ResourceException;
import br.com.brasilti.resources.validators.ElementValidator;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("br.com.brasilti.resources.annotations.Resource")
public class ResourcesProcessor extends AbstractProcessor {
	
	


	private ElementValidator validator;

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
		this.validator = new ElementValidator();

		Messager messager = this.processingEnv.getMessager();
		messager.printMessage(Kind.ERROR, "Passou aqui?");

		Set<? extends Element> elements = environment.getRootElements();
		for (Element element : elements) {
			// if (element.getKind() != ElementKind.ENUM) {
			// continue;
			// }
			
			// TODO Chamada para o validador.
			try {
				this.validator.validate(element);
			} catch (ResourceException e) {
				// e.printStackTrace();
				messager.printMessage(Kind.ERROR, e.getMessage(),element);
			}

			messager.printMessage(Kind.ERROR, element.getSimpleName());
			messager.printMessage(Kind.ERROR, element.getKind().name());
			messager.printMessage(Kind.ERROR, element.getClass().getName());

			// List<? extends Element> enclosedElements = element.getEnclosedElements();
			// messager.printMessage(Kind.ERROR, String.valueOf(enclosedElements.size()));
			// for (Element e : enclosedElements) {
			// messager.printMessage(Kind.ERROR, "Elemento");
			// messager.printMessage(Kind.ERROR, e.getSimpleName());
			// messager.printMessage(Kind.ERROR, e.getKind().name());
			// messager.printMessage(Kind.ERROR, e.getClass().getName());
			// }
		}

		return true;
	}
}
