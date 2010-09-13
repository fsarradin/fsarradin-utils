package org.kerflyn.context;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Map;


public class Contexts {

    public static final Context NULL = new NullContextImpl();

    public static Context create() {
	return new ContextImpl(NULL);
    }

    private static class ContextImpl implements Context {
	
	private final Context parent;
	private final Map<String, Object> objects;

	public ContextImpl(Context parent) {
	    this.parent = parent;
	    objects = new HashMap<String, Object>();
	}

	public Context getParent() { return parent; }

	public void register(String name, Object o) {
	    objects.put(name, o);
	}

	public Object get(String name) {
	    return objects.get(name);
	}

	public <T> T get(String name, Class<T> contract) {
	    return contract.cast(objects.get(name));
	}

	public <T> T get(Class<T> contract) {
	    for(Object o : objects.values()) {
		if (contract.isInstance(o)) {
		    return (T) o;
		}
	    }
	    throw new NoSuchElementException();
	}
	
    }
    
    private static class NullContextImpl implements Context {
	public Context getParent() { return this; }

	public void register(String name, Object o) {
	    throw new UnsupportedOperationException();
	}

	public Object get(String name) {
	    throw new NoSuchElementException();
	}

	public <T> T get(String name, Class<T> contract) {
	    throw new NoSuchElementException();
	}

	public <T> T get(Class<T> contract) {
	    throw new NoSuchElementException();
	}

    }

}

// End
