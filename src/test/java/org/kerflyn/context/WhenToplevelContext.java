package org.kerflyn.context;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.Rule;

import org.junit.rules.ExpectedException;

public class WhenToplevelContext {
    
    @Rule
	public ExpectedException thrown = ExpectedException.none();

    @Test
	public void thenTheParentIsNull() {
	Context context = Contexts.create();
	assertThat(context.getParent(), is(Contexts.NULL));
    }

}

// End
