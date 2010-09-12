package org.kerflyn.context;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class WhenNullContext {

    @Test
	public void thenTheParentIsNullAlso() {
	Context context = Contexts.NULL;
	assertThat(context.getParent(), is(Contexts.NULL));
    }

}

// End
