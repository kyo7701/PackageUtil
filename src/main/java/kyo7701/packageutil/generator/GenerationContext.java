package kyo7701.packageutil.generator;

import java.util.Collections;
import java.util.Map;

/**
 * Simple wrapper to hold generation inputs.
 * Use keys to pass various values needed by generators (packageName, className, fields, template, etc.).
 */
public class GenerationContext {
    private final Map<String, Object> data;

    public GenerationContext(Map<String, Object> data) {
        this.data = data == null ? Collections.emptyMap() : data;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) data.get(key);
    }

    public Map<String, Object> getAll() {
        return data;
    }
}