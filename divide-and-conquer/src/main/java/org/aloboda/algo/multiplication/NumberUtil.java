package org.aloboda.algo.multiplication;

import java.util.Arrays;

class NumberUtil {
    static String addFrontZeros(final CharSequence number, final int zerosToAdd) {
        final char[] frontZeros = new char[zerosToAdd];
        Arrays.fill(frontZeros, '0');
        return new String(frontZeros) + number;
    }

    static String addTrailZeros(final CharSequence number, final int zerosToAdd) {
        final char[] frontZeros = new char[zerosToAdd];
        Arrays.fill(frontZeros, '0');
        return number + new String(frontZeros);
    }
}
