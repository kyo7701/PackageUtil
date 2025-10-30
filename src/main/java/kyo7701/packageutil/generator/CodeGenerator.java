package kyo7701.packageutil.generator;

/**
 * Generic interface for code generators.
 * Implementations may generate Java source, other languages, or any text output.
 */
public interface CodeGenerator {
    /**
     * Generate code/text using the provided context.
     *
     * @param context generation context / inputs
     * @return generated text (source file contents or rendered template)
     * @throws Exception on generation errors
     */
    String generate(GenerationContext context) throws Exception;
}