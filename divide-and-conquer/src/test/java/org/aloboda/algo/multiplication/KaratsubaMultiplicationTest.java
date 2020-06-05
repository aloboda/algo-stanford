package org.aloboda.algo.multiplication;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class KaratsubaMultiplicationTest {
    private final KaratsubaMultiplication multiplier = new KaratsubaMultiplication();

    @Test
    public void when_multiply_small_ints_then_multiply_correctly() {
        // When...
        final int result = multiplier.multiply(1, 2);
        // Then...
        assertThat(result, Matchers.is(2));

    }
}
