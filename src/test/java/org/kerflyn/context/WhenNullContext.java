package org.kerflyn.context;

import org.junit.Test;

public class WhenNullContext {

    @Test
	public void thenTheParentIsNullAlso() {
	Context context = Contexts.NULL;
    }

}

// End
