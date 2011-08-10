package org.kerflyn.context;

public interface Context {

    Context getParent();

    void register(String name, Object o);

    /**
     * Get an object from its name.
     */
    Object get(String name);

    /**
     * Get an object from its name and check if it is an instance of the
     * given contract.
     */
    <T> T get(String name, Class<T> contract);

    /**
     * Get the first instance that fits the given contract.
     */
    <T> T get(Class<T> contract);

}

// End
