package kyo7701.packageutil.generator;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * Template-based generator using Mustache.
 * Expects:
 * - "template": String (the mustache template)
 * - "model": Map<String, Object> (model data)
 *
 * Returns the rendered template.
 */
public class TemplateGenerator implements CodeGenerator {
    @Override
    public String generate(GenerationContext context) throws Exception {
        String template = context.get("template");
        Map<String, Object> model = context.get("model");

        if (template == null) {
            throw new IllegalArgumentException("template is required in the context");
        }

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile(new StringReader(template), "inline-template");

        StringWriter writer = new StringWriter();
        m.execute(writer, model == null ? Map.of() : model).flush();

        return writer.toString();
    }
}