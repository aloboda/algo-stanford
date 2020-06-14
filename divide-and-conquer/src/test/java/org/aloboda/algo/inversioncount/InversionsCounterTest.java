package org.aloboda.algo.inversioncount;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InversionsCounterTest {
    private final InversionsCounter inversionsCounter = new InversionsCounter();

    @Test
    public void when_calculate_inversions_for_trivial_even_dataset_then_return_correct_result() {
        // Given...
        final int[] input = {1, 3, 5, 2, 4, 6};
        // When...
        final int result = this.inversionsCounter.countInversions(input);
        // Then...
        assertThat(result, is(3));
    }

    @Test
    public void when_calculate_inversions_for_trivial_odd_dataset_then_return_correct_result() {
        // Given...
        final int[] input = {1, 3, 4, 5, 2, 4, 6};
        // When...
        final int result = this.inversionsCounter.countInversions(input);
        // Then...
        assertThat(result, is(4));
    }
}
