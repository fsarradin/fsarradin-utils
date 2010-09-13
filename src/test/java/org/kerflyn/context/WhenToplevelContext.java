package org.kerflyn.context;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;

import org.junit.rules.ExpectedException;

public class WhenToplevelContext {
    
    public Context context;
    public Object object;

    @Rule
	public ExpectedException thrown = ExpectedException.none();

    @Before
	public void setUp() {
	context = Contexts.create();
	object = java.util.Collections.emptyList();
    }

    @Test
	public void thenTheParentIsNull() {
	assertThat(context.getParent(), is(Contexts.NULL));
    }

    @Test
	public void thenGetObjectByName() {
	context.register("object", object);
	assertThat(context.get("object"), is(object));
    }

    @Test
	public void thenGetObjectByNameAndByContract() {
	context.register("object", object);
	assertThat(context.get("object", List.class), is(object));
	assertThat(context.get("object", Object.class), is(object));
    }

    @Test
	public void thenComplainOnBadContract() {
	thrown.expect(ClassCastException.class);

	context.register("object", object);
	context.get("object", String.class);
    }

    @Test
	public void thenGetObjectByContract() {
	context.register("object", object);
	assertThat(context.get(List.class), is(object));
	assertThat(context.get(Object.class), is(object));
    }

    @Test
	public void thenComplainOnNoElementForTheContract() {
	thrown.expect(NoSuchElementException.class);

	context.register("object", object);
	context.get(String.class);
    }

}

// End
