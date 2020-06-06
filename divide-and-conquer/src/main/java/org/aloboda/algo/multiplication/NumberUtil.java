package org.aloboda.algo.multiplication;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.Arrays;

class NumberUtil {
    static final char MINUS = '-';
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

    static Tuple2<CharSequence, CharSequence> alignToHaveTheSameSize(
            final CharSequence number1, final CharSequence number2
    ) {
        final int lengthDelta = number1.length() - number2.length();
        if (lengthDelta > 0) {
            return Tuple.of(number1, addFrontZeros(number2, lengthDelta));
        } else {
            return Tuple.of(addFrontZeros(number1, -lengthDelta), number2);
        }
    }

    static boolean isPositive(final CharSequence number) {
        return number.charAt(0) != MINUS;
    }

    static CharSequence inverseSign(final CharSequence number) {
        if (number.length() == 0 || (number.length() == 1 && number.charAt(0) == '0')) {
            return "0";
        }
        if (number.charAt(0) == MINUS) {
            return number.toString().substring(1);
        }
        return MINUS + number.toString();
    }


    static byte toDigit(final CharSequence number, final int index) {
        //noinspection NumericCastThatLosesPrecision
        return (byte) Character.getNumericValue(number.charAt(index));
    }

    static char toChar(final int digit) {
        return Character.forDigit(digit, 10);
    }
}
