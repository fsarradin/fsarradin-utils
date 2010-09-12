package org.kerflyn.context;

public class Contexts {

    public static final Context NULL = new NullContextImpl();

    private static class NullContextImpl implements Context {
	public Context getParent() { return this; }
	public void register(String name, Object o) {}
	public Object get(String name) { return null; }
	public <T> T get(String name, Class<T> contract) { return null; }
	public <T> T get(Class<T> contract) { return null; }
    }

}

// End
