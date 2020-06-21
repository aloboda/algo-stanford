package org.aloboda.algo.quicksort;

import io.vavr.NotImplementedError;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents QuickSort Algorithm
 */
public class QuickSortAlgorithm {
    /**
     * Sorts array in place and returns total number of comparisons
     *
     * @param array array to sort
     * @return total number of comparisons(simplified => add m-1 for each subarray)
     */
    public long sort(final int[] array) {
        return sort(array, PivotType.RANDOM);
    }

    public long sort(final int[] array, final PivotType pivotType) {
        return sort(array, pivotType, 0, array.length);
    }

    private long sort(
            final int[] array, final PivotType pivotType, final int start, final int end
    ) {
        final int arrayLengthInFocus = end - start;
        if (arrayLengthInFocus <= 2) {
            if (arrayLengthInFocus == 2) {
                if (array[start] >= array[end - 1]) {
                    swapElements(array, start, end - 1);
                }
                return 1;
            }
            return 0;
        }
        final int pivotIndex = selectPivot(array, pivotType, start, end);
        final int pivotInOrderIndex = partitionAroundPivot(array, pivotIndex, start, end);
        final long comparisonsInFirstSubRoutine = sort(array, pivotType, start, pivotInOrderIndex);
        final long comparisonsInSecondSubRoutine = sort(array, pivotType, pivotInOrderIndex + 1, end);
        return comparisonsInFirstSubRoutine + comparisonsInSecondSubRoutine + (end - start - 1);
    }

    private static void swapElements(final int[] array, final int index1, final int index2) {
        final int tempValue = array[index1];
        array[index1] = array[index2];
        array[index2] = tempValue;
    }

    private static int partitionAroundPivot(final int[] array, final int pivotIndex, final int start, final int end) {
        if (pivotIndex != start) {
            swapElements(array, pivotIndex, start);
        }
        final int pivotValue = array[start];
        int lastIndexOfSmallerThanPivot = start; // i.e. i
        for (int lastIndexOfExploredArray = start + 1; lastIndexOfExploredArray < end; lastIndexOfExploredArray++) {
            final int currentValue = array[lastIndexOfExploredArray];
            if (currentValue < pivotValue) {
                swapElements(array, lastIndexOfExploredArray, lastIndexOfSmallerThanPivot + 1);
                lastIndexOfSmallerThanPivot++;
            }

        }
        swapElements(array, lastIndexOfSmallerThanPivot, start);
        return lastIndexOfSmallerThanPivot;
    }

    private int selectPivot(final int[] array, final PivotType pivotType, final int start, final int end) {
        switch (pivotType) {
            case FIRST:
                return start;
            case LAST:
                return end - 1;
            case MEDIAN:
                final int startValue = array[start];
                final int endValue = array[end - 1];
                final int length = end - start;

                final int medianIndex = start + (length - 1) / 2;
                final int medianValue = array[medianIndex];
                final int[] medianArray = {startValue, endValue, medianValue};
                sort(medianArray, PivotType.LAST);
                if (medianArray[1] == startValue) {
                    return start;
                }
                if (medianArray[1] == endValue) {
                    return end - 1;
                }
                return medianIndex;
            case RANDOM:
                return ThreadLocalRandom.current().nextInt(start, end - 1);
            default:
                throw new NotImplementedError();
        }
    }


    public enum PivotType {
        FIRST,
        LAST,
        MEDIAN,
        RANDOM
    }
}
