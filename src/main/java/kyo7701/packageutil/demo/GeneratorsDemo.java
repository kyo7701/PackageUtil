package kyo7701.packageutil.demo;

import kyo7701.packageutil.generator.GenerationContext;
import kyo7701.packageutil.generator.JavaPoetGenerator;
import kyo7701.packageutil.generator.TemplateGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Minimal demo showing how to use the JavaPoet and Template generators.
 */
public class GeneratorsDemo {
    public static void main(String[] args) throws Exception {
        // --- JavaPoet demo ---
        Map<String, String> fields = new HashMap<>();
        fields.put("id", "java.lang.String");
        fields.put("count", "int");

        Map<String, Object> jpContextMap = new HashMap<>();
        jpContextMap.put("packageName", "com.example.generated");
        jpContextMap.put("className", "GeneratedExample");
        jpContextMap.put("fields", fields);

        String javaSource = new JavaPoetGenerator().generate(new GenerationContext(jpContextMap));
        System.out.println("=== JavaPoet generated source ===");
        System.out.println(javaSource);

        // --- Template demo ---
        String template = "package {{packageName}};\n\npublic class {{className}} {\n{{#fields}}\n    private {{type}} {{name}};\n{{/fields}}\n}\n";

        Map<String, Object> model = new HashMap<>();
        model.put("packageName", "com.example.template");
        model.put("className", "TemplateExample");

        Map<String, Object> f1 = Map.of("type", "String", "name", "foo");
        Map<String, Object> f2 = Map.of("type", "int", "name", "bar");
        model.put("fields", new Object[] { f1, f2 });

        Map<String, Object> tmplCtx = new HashMap<>();
        tmplCtx.put("template", template);
        tmplCtx.put("model", model);

        String tplOut = new TemplateGenerator().generate(new GenerationContext(tmplCtx));
        System.out.println("=== Template generated source ===");
        System.out.println(tplOut);
    }
}