package org.kerflyn.context;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.Rule;

import org.junit.rules.ExpectedException;

public class WhenNullContext {
    
    @Rule
	public ExpectedException thrown = ExpectedException.none();

    @Test
	public void thenTheParentIsNullAlso() {
	Context context = Contexts.NULL;
	assertThat(context.getParent(), is(Contexts.NULL));
    }

    @Test
	public void thenComplainOnRegister() {
	thrown.expect(UnsupportedOperationException.class);

	Context context = Contexts.NULL;
	context.register("object", "hello");
    }

    @Test
	public void thenComplainOnGettingObject() {
	thrown.expect(UnsupportedOperationException.class);

	Context context = Contexts.NULL;
	context.get(Object.class);
    }

}

// End
