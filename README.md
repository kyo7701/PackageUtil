# Code generation helpers (proposed)

This small addition provides:
- A CodeGenerator interface and GenerationContext to standardize generation inputs.
- JavaPoet-based generator for structured Java code generation.
- Mustache template-based generator for text/template-based generation.
- A demo (GeneratorsDemo) showing usage.

Getting started:
1. Add dependencies (JavaPoet and Mustache) to your build (see pom.xml snippet above).
2. Use JavaPoetGenerator when you want type-safe, structured generation (classes, fields, getters/setters).
3. Use TemplateGenerator when you want flexible text templates (file formats, quick classes, or non-Java outputs).
4. Extend the API by adding additional generators (e.g., an AnnotationProcessor-driven generator, a Gradle/Maven plugin, or a CLI wrapper).

Ideas for further simplification and automation:
- Provide a fluent Builder for GenerationContext to avoid manually assembling maps.
- Add unit tests and snapshot tests for generated output.
- Add a command-line entrypoint that reads a YAML/JSON spec and writes generated files to disk.
- Integrate with your build (Maven plugin or Gradle task) so generated sources are automatically compiled.
