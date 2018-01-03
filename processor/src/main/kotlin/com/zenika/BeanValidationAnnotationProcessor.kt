package com.zenika

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.tools.Diagnostic

class BeanValidationAnnotationProcessor : AbstractProcessor() {

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        print(Diagnostic.Kind.WARNING, "Processing ...")
        val classes = roundEnv.rootElements.toList().mapNotNull { element -> convertElementToClass(element) }

        // TODO serialize classes
        return true
    }

    private fun convertElementToClass(rootElement: Element): com.zenika.Class? {
        print(Diagnostic.Kind.WARNING, "${rootElement.simpleName} | ${rootElement.kind.name}")

        val fields = rootElement.enclosedElements.toList()
                .filter { element: Element -> ElementKind.FIELD == element.kind }
                .mapNotNull { element: Element -> convertElementToField(element) }


        // for data class => Constructor with annotations
        val constructorFields = rootElement.enclosedElements.toList()
                .filter { element: Element -> ElementKind.CONSTRUCTOR == element.kind }
                .map { element -> ( element as ExecutableElement) }
                .flatMap { executableElement -> executableElement.parameters }
                .mapNotNull { element: Element -> convertElementToField(element)  }


        val allFields = (fields union constructorFields).toList()
        
        /*val allFields = constructorFields.toMutableList()
        fields.forEach { field: Field ->
            val common : Field? = constructorFields.firstOrNull{other: Field -> field.name == other.name && field.type == other.type}
            if (common != null) {
                val validators = common.validators.toMutableList()
                validators.addAll(field.validators)
                allFields.remove(common)
                allFields.add(Field(field.name, field.type, validators))
            } else {
                allFields.add(field)
            }
        }*/



        return if (!allFields.isEmpty()) {
            Class(rootElement.simpleName.toString(), rootElement.enclosingElement.toString(), allFields)
        } else null
    }


    private fun convertElementToField(enclosedElement: Element): Field? {
        print(Diagnostic.Kind.WARNING, "  $enclosedElement")
        val validators = enclosedElement.annotationMirrors.toList()
                .filter { annotationMirror -> annotationMirror.annotationType.asElement().toString().startsWith("javax.validation.") }
                .map { annotationMirror -> getValidator(annotationMirror) }

        return if (!validators.isEmpty()) {
            Field(enclosedElement.toString(), getType(enclosedElement), validators)
        } else null

    }

    private fun getValidator(annotationMirror: AnnotationMirror): Field.Validator {
        print(Diagnostic.Kind.WARNING, "     $annotationMirror")
        return Field.Validator(
                annotationMirror.annotationType.toString(),
                annotationMirror.elementValues.entries.toList()
                        .map { entry ->
                            Field.Validator.Arg(sanitizeArgKey(entry.key.toString()),
                                    sanitizeArgValue(entry.value.value.toString()))
                        }
        )
    }

    private fun getType(element: Element): String {
        val type = element.asType().toString()
        return type.substringBefore('.', type)
//        val last = type.lastIndexOf('.')
//        return if (last > 0) type.substring(last + 1) else type
    }

    private fun sanitizeArgValue(value: String): String {
        return value.replace("\"", "\\\"").replace("{", "").replace("}", "")
    }

    private fun sanitizeArgKey(key: String): String {
        return key.replace("()", "")
    }

    private fun print(msg: String): Unit {
        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, msg)
    }
    private fun print(diagnostic: Diagnostic.Kind, msg: String): Unit {
        processingEnv.messager.printMessage(diagnostic, msg)
    }

    override fun getSupportedSourceVersion() = SourceVersion.RELEASE_8

    /*
        override fun getSupportedAnnotationTypes() = TODO()

        override fun getSupportedOptions() = TODO()
    */
    override fun getSupportedAnnotationTypes() = setOf("javax.validation.*")
    // TODO filter "javax.validation.*"

}

