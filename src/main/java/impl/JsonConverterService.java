package impl;

/**
 * The interface which helps converts Object into JSON string and
 * visa versa.
 */
public interface JsonConverterService {
    /**
     * Convert provided object into JSON string
     *
     * @param o the object to convert.
     * @return the valid json string.
     */
    String toJson(Object o);

    /**
     * Convert from JSON string into Java object.
     *
     * @param json  the json string.
     * @param clazz the clazz of the result object
     * @return the object to be returned
     * @throws IllegalArgumentException in case json string is null or has incorrect format.
     */
    Object fromJson(String json, Class<?> clazz);
}
