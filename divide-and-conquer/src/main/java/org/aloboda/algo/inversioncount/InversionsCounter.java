package org.aloboda.algo.inversioncount;

import lombok.Value;

/**
 * The algorithm to calculate number of inversions in the given array.
 * Number of inversions defines how close the array to its sorted version.
 * It can be used to compare for example, top 100 books that 2 users read and see how close their list is.
 * <p>
 * The algorithm is using Divide and Conquer Merge sort with some addon to calculate the inversions.
 */
public class InversionsCounter {
    /**
     * @param array array of distinct int in arbitrary order
     * @return number of inversions
     * <p>
     * NOTE:
     * Inversion is a number of pairs[i,j] of array indices i<j and {@code array[i]>array[j]}
     * For example: for sorted array, the result is 0.
     */
    public long countInversions(final int[] array) {
        return internalCountInversions(array, 0, array.length).inversionsCount;
    }

    private InversionResult internalCountInversions(final int[] array, final int startIndex, final int endIndex) {
        final int arrayLength = endIndex - startIndex;
        if (arrayLength == 1) {
            return new InversionResult(new int[]{array[startIndex]}, 0);
        }
        if (arrayLength == 2) {
            if (array[startIndex] > array[endIndex - 1]) {
                return new InversionResult(new int[]{array[startIndex], array[endIndex - 1]}, 0);
            }
        }
        final boolean even = arrayLength % 2 == 0;
        final int halfArrayLength = even ? arrayLength / 2 : arrayLength / 2 + 1;
        final InversionResult firstPart = this.internalCountInversions(array, startIndex, startIndex + halfArrayLength);
        final InversionResult secondPart = this.internalCountInversions(array, startIndex + halfArrayLength, endIndex);
        return mergeSortedArrays(firstPart, secondPart);
    }

    private static InversionResult mergeSortedArrays(final InversionResult first, final InversionResult second) {
        int firstIndex = 0;
        int secondIndex = 0;
        long inversionsCount = first.inversionsCount + second.inversionsCount;
        final int[] result = new int[first.result.length + second.result.length];
        for (int i = 0; i < result.length; i++) {
            final int value;
            if (firstIndex < first.result.length && (secondIndex == second.result.length || first.result[firstIndex] <= second.result[secondIndex])) {
                value = first.result[firstIndex];
                firstIndex++;
            } else {
                value = second.result[secondIndex];
                secondIndex++;
                if (firstIndex < first.result.length) {
                    inversionsCount += first.result.length - firstIndex;
                }

            }
            result[i] = value;

        }
        return new InversionResult(result, inversionsCount);
    }

    @Value
    private static class InversionResult {
        private final int[] result;
        private final long inversionsCount;

    }
}
