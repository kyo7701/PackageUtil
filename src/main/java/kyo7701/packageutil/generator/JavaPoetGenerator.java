package kyo7701.packageutil.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.Map;

/**
 * A small JavaPoet-based generator that expects the following keys in GenerationContext:
 * - "packageName": String
 * - "className": String
 * - "fields": Map<String, String> mapping fieldName -> fully-qualified or simple type name
 *
 * It returns the generated Java source as a String.
 */
public class JavaPoetGenerator implements CodeGenerator {
    @Override
    public String generate(GenerationContext context) throws Exception {
        String packageName = context.get("packageName");
        String className = context.get("className");
        Map<String, String> fields = context.get("fields");

        if (packageName == null || className == null) {
            throw new IllegalArgumentException("packageName and className are required in the context");
        }

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC);

        if (fields != null) {
            for (Map.Entry<String, String> e : fields.entrySet()) {
                String fieldName = e.getKey();
                String typeName = e.getValue();

                ClassName type;
                try {
                    type = ClassName.bestGuess(typeName);
                } catch (Exception ex) {
                    type = ClassName.bestGuess("java.lang.Object");
                }

                FieldSpec field = FieldSpec.builder(type, fieldName, Modifier.PRIVATE).build();
                classBuilder.addField(field);

                MethodSpec getter = MethodSpec.methodBuilder("get" + capitalize(fieldName))
                        .addModifiers(Modifier.PUBLIC)
                        .returns(type)
                        .addStatement("return this.$N", fieldName)
                        .build();
                classBuilder.addMethod(getter);

                MethodSpec setter = MethodSpec.methodBuilder("set" + capitalize(fieldName))
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(type, fieldName)
                        .addStatement("this.$N = $N", fieldName, fieldName)
                        .build();
                classBuilder.addMethod(setter);
            }
        }

        JavaFile javaFile = JavaFile.builder(packageName, classBuilder.build())
                .skipJavaLangImports(true)
                .build();

        return javaFile.toString();
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}