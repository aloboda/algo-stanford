package org.aloboda.algo.quicksort;

import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.aloboda.algo.quicksort.QuickSortAlgorithm.PivotType.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class QuickSortAlgorithmTest {

    @Test
    public void when_sort_trivial_even_array_and_pivot_is_first_then_array_is_sorted() {
        // Given...
        final int[] array = {6, 3, 2, 4, 5, 1};

        // When...
        final long comparisonsCount = new QuickSortAlgorithm().sort(array, FIRST);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5, 6}), Matchers.is(true));
        assertThat(comparisonsCount, Matchers.is(12L));
    }

    @Test
    public void when_sort_trivial_even_array_and_pivot_is_last_then_array_is_sorted() {
        // Given...
        final int[] array = {6, 3, 2, 4, 5, 1};

        // When...
        final long comparisonsCount = new QuickSortAlgorithm().sort(array, LAST);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5, 6}), Matchers.is(true));
        assertThat(comparisonsCount, Matchers.is(14L));
    }

    @Test
    public void when_sort_trivial_even_array_and_pivot_is_medium_then_array_is_sorted() {
        // Given...
        final int[] array = {6, 3, 2, 4, 5, 1};

        // When...
        final long comparisonsCount = new QuickSortAlgorithm().sort(array, MEDIAN);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5, 6}), Matchers.is(true));
        assertThat(comparisonsCount, Matchers.is(7L));
    }

    @Test
    public void when_sort_trivial_odd_array_then_array_is_sorted() {
        // Given...
        final int[] array = {4, 2, 3, 5, 1};

        // When...
        final long comparisonsCount = new QuickSortAlgorithm().sort(array, FIRST);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5}), Matchers.is(true));
        assertThat(comparisonsCount, Matchers.is(6L));
    }

    @Test
    public void when_sort_trivial_sorted_array_then_array_is_still_sorted() {
        // Given...
        final int[] array = {1, 2, 3, 4, 5};

        // When...
        final long comparisonsCount = new QuickSortAlgorithm().sort(array, FIRST);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5}), Matchers.is(true));
        assertThat(comparisonsCount, Matchers.is(9L));
    }

    @Test
    public void when_sort_trivial_reverse_sorted_array_then_array_is_sorted() {
        // Given...
        final int[] array = {5, 4, 3, 2, 1};

        // When...
        final long comparisonsCount = new QuickSortAlgorithm().sort(array, FIRST);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5}), Matchers.is(true));
        assertThat(comparisonsCount, Matchers.is(9L));
    }

    @Test
    public void when_sort_trivial_reverse_sorted_array_using_median_then_array_is_sorted() {
        // Given...
        final int[] array = {5, 4, 3, 2, 1};

        // When...
        final long comparisonsCount = new QuickSortAlgorithm().sort(array, MEDIAN);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5}), Matchers.is(true));
        assertThat(comparisonsCount, Matchers.is(6L));
    }
    //TODO:andrii.loboda:2020-06-21: test for all the different pivot strategies
    //TODO:andrii.loboda:2020-06-21: test on large number

    @Test
    public void when_sorting_large_array_using_median_pivot_then_it_is_sorted() {
        // Given...
        final int[] array = readFromResource("quicksort/large_sample.txt");
        // When...
        final long comparisonsCount = new QuickSortAlgorithm().sort(array, MEDIAN);
        // Then...
        assertThat(comparisonsCount, Matchers.is(145_770L));
    }

    @Test
    public void when_sorting_large_array_using_first_element_as_pivot_then_it_is_sorted() {
        // Given...
        final int[] array = readFromResource("quicksort/large_sample.txt");
        // When...
        final long comparisonsCount = new QuickSortAlgorithm().sort(array, FIRST);
        // Then...
        assertThat(comparisonsCount, Matchers.is(160_451L));
    }

    @Test
    public void when_sorting_large_array_using_last_element_as_pivot_then_it_is_sorted() {
        // Given...
        final int[] array = readFromResource("quicksort/large_sample.txt");
        // When...
        final long comparisonsCount = new QuickSortAlgorithm().sort(array, LAST);
        // Then...
        assertThat(comparisonsCount, Matchers.is(162_502L));
    }

    @SuppressWarnings("UnstableApiUsage")
    private static int[] readFromResource(@SuppressWarnings("SameParameterValue") final String resourcePath) {
        try {
            final CharSource charSource = Resources.asCharSource(Resources.getResource(resourcePath), StandardCharsets.UTF_8);
            try (final Stream<String> lines = charSource.lines()) {
                return lines.mapToInt(Integer::parseInt).toArray();
            }
        } catch (final IOException e) {
            //noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
            throw new IllegalStateException(String.format("Unable to read from resource: %s", resourcePath));
        }
    }
}
