package org.aloboda.algo.quicksort;

import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import org.aloboda.algo.quicksort.QuickSortAlgorithm.PivotType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.aloboda.algo.quicksort.QuickSortAlgorithm.PivotType.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class QuickSortAlgorithmTest {

    private final QuickSortAlgorithm quickSort = new QuickSortAlgorithm();

    @Test
    public void when_sort_trivial_even_array_and_pivot_is_first_then_array_is_sorted() {
        // Given...
        final int[] array = {6, 3, 2, 4, 5, 1};
        // When...
        this.quickSort.sort(array, FIRST);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5, 6}), Matchers.is(true));
    }

    @Test
    public void when_sort_trivial_even_array_and_pivot_is_last_then_array_is_sorted() {
        // Given...
        final int[] array = {6, 3, 2, 4, 5, 1};
        // When...
        this.quickSort.sort(array, LAST);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5, 6}), Matchers.is(true));
    }

    @Test
    public void when_sort_trivial_even_array_and_pivot_is_medium_then_array_is_sorted() {
        // Given...
        final int[] array = {6, 3, 2, 4, 5, 1};
        // When...
        this.quickSort.sort(array, MEDIAN);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5, 6}), Matchers.is(true));
    }
    @Test
    public void when_sort_trivial_even_array_and_pivot_is_random_then_array_is_sorted() {
        // Given...
        final int[] array = {6, 3, 2, 4, 5, 1};
        // When...
        this.quickSort.sort(array, RANDOM);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5, 6}), Matchers.is(true));
    }

    @Test
    public void when_sort_trivial_odd_array_then_array_is_sorted() {
        // Given...
        final int[] array = {4, 2, 3, 5, 1};

        // When...
        this.quickSort.sort(array, FIRST);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5}), Matchers.is(true));
    }

    @Test
    public void when_sort_trivial_sorted_array_then_array_is_still_sorted() {
        // Given...
        final int[] array = {1, 2, 3, 4, 5};
        // When...
        this.quickSort.sort(array, FIRST);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5}), Matchers.is(true));
    }

    @Test
    public void when_sort_trivial_reverse_sorted_array_then_array_is_sorted() {
        // Given...
        final int[] array = {5, 4, 3, 2, 1};
        // When...
        this.quickSort.sort(array, FIRST);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5}), Matchers.is(true));
    }

    @Test
    public void when_sort_trivial_reverse_sorted_array_using_median_then_array_is_sorted() {
        // Given...
        final int[] array = {5, 4, 3, 2, 1};

        // When...
        this.quickSort.sort(array, MEDIAN);
        // Then...
        assertThat(Arrays.equals(array, new int[]{1, 2, 3, 4, 5}), Matchers.is(true));
    }

    @Test
    public void when_sorting_large_array_using_median_pivot_then_it_is_sorted() {
        // Given...
        final int[] array = readFromResource("quicksort/large_sample.txt");
        // When...
        final long comparisonsCount = this.quickSort.sort(array, MEDIAN);
        // Then...
        assertThat(comparisonsCount, Matchers.is(138382L));
    }

    @Test
    public void when_sorting_large_array_using_first_element_as_pivot_then_it_is_sorted() {
        // Given...
        final int[] array = readFromResource("quicksort/large_sample.txt");
        // When...
        final long comparisonsCount = this.quickSort.sort(array, FIRST);
        // Then...
        assertThat(comparisonsCount, Matchers.is(162085L));
    }

    @Test
    public void when_sorting_large_array_using_last_element_as_pivot_then_it_is_sorted() {
        // Given...
        final int[] array = readFromResource("quicksort/large_sample.txt");
        // When...
        final long comparisonsCount = this.quickSort.sort(array, LAST);
        // Then...
        assertThat(comparisonsCount, Matchers.is(164123L));
    }

    /**
     * Test Case #1 for 10 items https://github.com/beaunus/stanford-algs/blob/master/testCases/course1/assignment3Quicksort/input_dgrcode_09_10.txt
     */
    @Test
    public void when_test_counts_on_10_length_array_test_case_1_first_then_calculate_it_correctly() {
        // Given...
        final int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // When...
        sortAndCheckComparisons(new int[]{4, 5, 8, 6, 10, 2, 3, 1, 7, 9}, expected, FIRST, 25L);
        sortAndCheckComparisons(new int[]{4, 5, 8, 6, 10, 2, 3, 1, 7, 9}, expected, LAST, 27L);
        sortAndCheckComparisons(new int[]{4, 5, 8, 6, 10, 2, 3, 1, 7, 9}, expected, MEDIAN, 23L);
    }

    /**
     * Test Case #1 for 5 items https://github.com/beaunus/stanford-algs/blob/master/testCases/course1/assignment3Quicksort/input_dgrcode_01_5.txt
     */
    @Test
    public void when_test_counts_on_5_length_array_test_case_1_first_then_calculate_it_correctly() {
        // Given...
        final int[] expected = {1, 2, 3, 4, 5};
        sortAndCheckComparisons(new int[]{3, 2, 1, 4, 5}, expected, FIRST, 6L);
        sortAndCheckComparisons(new int[]{3, 2, 1, 4, 5}, expected, LAST, 10L);
        sortAndCheckComparisons(new int[]{3, 2, 1, 4, 5}, expected, MEDIAN, 6L);
    }

    /**
     * Test Case #2 for 5 items https://github.com/beaunus/stanford-algs/blob/master/testCases/course1/assignment3Quicksort/input_dgrcode_02_5.txt
     */
    @Test
    public void when_test_counts_on_other_5_length_array_test_case_then_calculate_it_correctly_2() {
        // Given...
        final int[] expected = {1, 2, 3, 4, 5};
        // When...
        sortAndCheckComparisons(new int[]{4, 3, 2, 5, 1}, expected, FIRST, 7L);
        sortAndCheckComparisons(new int[]{4, 3, 2, 5, 1}, expected, LAST, 8L);
        sortAndCheckComparisons(new int[]{4, 3, 2, 5, 1}, expected, MEDIAN, 6L);
    }

    private void sortAndCheckComparisons(
            final int[] array, final int[] expected, final PivotType pivotType,
            final long comparisonsExpected
    ) {
        final long comparisonsFoFirstPivot = this.quickSort.sort(array, pivotType);
        assertThat(Arrays.equals(array, expected), Matchers.is(true));
        assertThat(comparisonsFoFirstPivot, Matchers.is(comparisonsExpected));
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
