package org.aloboda.algo.inversioncount;

import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InversionsCounterTest {
    private final InversionsCounter inversionsCounter = new InversionsCounter();

    @Test
    public void when_calculate_inversions_for_trivial_even_dataset_then_return_correct_result() {
        // Given...
        final int[] input = {1, 3, 5, 2, 4, 6};
        // When...
        final long inversionsCount = this.inversionsCounter.countInversions(input);
        // Then...
        assertThat(inversionsCount, is(3L));
    }

    @Test
    public void when_calculate_inversions_for_trivial_odd_dataset_then_return_correct_result() {
        // Given...
        final int[] input = {1, 3, 4, 5, 2, 4, 6};
        // When...
        final long inversionsCount = this.inversionsCounter.countInversions(input);
        // Then...
        assertThat(inversionsCount, is(4L));
    }

    @Test
    public void when_calc_for_large_dataset_then_return_correct_result() {
        // Given...
        final int[] input = readFromResource("inversion_count/large_sample.txt");
        // When...
        final long inversionsCount = this.inversionsCounter.countInversions(input);
        // Then...
        assertThat(inversionsCount, is(2391398879L));

    }

    @SuppressWarnings("UnstableApiUsage")
    private static int[] readFromResource(@SuppressWarnings("SameParameterValue") final String resourcePath) {
        try {
            final CharSource charSource = Resources.asCharSource(Resources.getResource(resourcePath), StandardCharsets.UTF_8);
            try (final Stream<String> lines = charSource.lines()) {
                return lines.mapToInt(Integer::parseInt).toArray();
            }
        } catch (final IOException e) {
            throw new IllegalStateException(String.format("Unable to read from resource: %s", resourcePath));
        }
    }
}
