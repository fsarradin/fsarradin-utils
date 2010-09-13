package org.kerflyn.context;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;

import org.junit.rules.ExpectedException;

public class WhenAnyContext {
    
    public Context context;
    public Context parent;
    public Object object;

    @Rule
	public ExpectedException thrown = ExpectedException.none();

    @Before
	public void setUp() {
	parent = Contexts.create();
	context = Contexts.create(parent);
	object = java.util.Collections.emptyList();
    }

    @Test
	public void thenTheParentIsNotNull() {
	assertThat(context.getParent(), is(parent));
    }

    @Test
	public void thenGetObjectByName() {
	parent.register("object", object);
	assertThat(context.get("object"), is(object));
    }

    @Test
	public void thenGetObjectByNameAndByContract() {
	parent.register("object", object);
	assertThat(context.get("object", List.class), is(object));
	assertThat(context.get("object", Object.class), is(object));
    }

    @Test
	public void thenComplainOnBadContract() {
	thrown.expect(ClassCastException.class);

	parent.register("object", object);
	context.get("object", String.class);
    }

    @Test
	public void thenGetObjectByContract() {
	parent.register("object", object);
	assertThat(context.get(List.class), is(object));
	assertThat(context.get(Object.class), is(object));
    }

    @Test
	public void thenComplainOnNoElementForTheContract() {
	thrown.expect(NoSuchElementException.class);

	parent.register("object", object);
	context.get(String.class);
    }

}

// End
