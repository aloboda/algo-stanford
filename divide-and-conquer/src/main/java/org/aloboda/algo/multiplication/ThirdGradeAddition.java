package org.aloboda.algo.multiplication;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class ThirdGradeAddition {

    CharSequence add(final CharSequence value, final CharSequence augend) {
        final Tuple2<CharSequence, CharSequence> valueAndAugend = alignToHaveTheSameSize(value, augend);
        return this.addForSameSize(valueAndAugend._1, valueAndAugend._2);
    }

    private CharSequence addForSameSize(final CharSequence value, final CharSequence augend) {
        boolean increment = false;
        final Deque<Character> numbers = new LinkedList<>();
        for (int index = value.length() - 1; index >= 0; index--) {
            final byte a = extractDigit(value, index);
            final byte b = extractDigit(augend, index);

            final int sum = a + b + (increment ? 1 : 0);
            increment = sum >= 10;
            final boolean firstDigit = index == 0;
            final char digitToAdd = String.valueOf(sum % 10).charAt(0);
            if (increment && firstDigit) {
                numbers.addFirst(digitToAdd);
                numbers.addFirst('1');
            } else {
                if (!firstDigit || digitToAdd != '0') {
                    numbers.addFirst(digitToAdd);
                }
            }
        }
        final StringBuilder result = new StringBuilder(numbers.size());
        for (final char number : numbers) {
            result.append(number);
        }

        return result.toString();
    }

    private static byte extractDigit(final CharSequence number, final int index) {
        return Byte.parseByte(String.valueOf(number.charAt(index)));
    }

    private static Tuple2<CharSequence, CharSequence> alignToHaveTheSameSize(
            final CharSequence number1, final CharSequence number2
    ) {
        final int delta = number1.length() - number2.length();
        if (delta > 0) {
            return Tuple.of(number1, addFrontZeros(number2, delta));
        } else {
            return Tuple.of(addFrontZeros(number1, -delta), number2);
        }
    }

    private static String addFrontZeros(final CharSequence number, final int zerosToAdd) {
        final char[] frontZeros = new char[zerosToAdd];
        Arrays.fill(frontZeros, '0');
        return new String(frontZeros) + number;
    }
}
