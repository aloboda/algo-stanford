package org.aloboda.algo.multiplication;

import com.google.common.base.Preconditions;
import io.vavr.Tuple2;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

import static org.aloboda.algo.multiplication.NumberUtil.*;

/**
 * Provides addition and subtraction operations for integer numbers(of any length)
 */
public class BasicMathOperations {
    /**
     * Subtraction for large integer(of any length) numbers
     * Naive third grade implementation
     *
     * @param number     text representation of a number from which subtraction takes place
     * @param subtrahend text representation of subtrahend(number that will be subtracted
     * @return subtraction result
     */
    public CharSequence subtract(final CharSequence number, final CharSequence subtrahend) {
        final boolean valuePositive = isPositive(number);
        final boolean subtrahendPositive = isPositive(subtrahend);
        if (valuePositive && subtrahendPositive) { // both positive
            return subtractPositives(number, subtrahend);
        }
        if (valuePositive) { // value=+ and subtrahend=-
            return addPositives(number, inverseSign(subtrahend));
        }
        if (subtrahendPositive) { // value=- and subtrahend=+
            return inverseSign(addPositives(subtrahend, inverseSign(number)));
        }
        // both negative
        return inverseSign(subtractPositives(inverseSign(number), inverseSign(subtrahend)));
    }

    /**
     * Addition for large integer(of any length) numbers
     * Naive third grade implementation
     *
     * @param number text representation of a number on which addition will be applied
     * @param augend text representation of augend(number that will be added to base one)
     * @return addition  result
     */
    public CharSequence add(final CharSequence number, final CharSequence augend) {
        final boolean valuePositive = isPositive(number);
        final boolean augendPositive = isPositive(augend);
        if (valuePositive && augendPositive) { // both positive
            return addPositives(number, augend);
        }
        if (valuePositive) { // number=+ and augend=- :
            return subtractPositives(number, inverseSign(augend));
        }
        if (augendPositive) { // number=- and augend=+
            return subtractPositives(augend, inverseSign(number));
        }
        // both negative
        return inverseSign(addPositives(inverseSign(number), inverseSign(augend)));
    }


    private static CharSequence addPositives(final CharSequence value, final CharSequence augend) {
        final Tuple2<CharSequence, CharSequence> valueAndAugend = alignToHaveTheSameSize(value, augend);
        return addForSameSize(valueAndAugend._1, valueAndAugend._2);
    }

    private static CharSequence subtractPositives(final CharSequence value, final CharSequence subtrahend) {
        final Tuple2<CharSequence, CharSequence> valueAndSubtrahend = alignToHaveTheSameSize(value, subtrahend);
        if (isFirstNumberLarger(valueAndSubtrahend._1, valueAndSubtrahend._2)) {
            return subtractForSameSize(valueAndSubtrahend._1, valueAndSubtrahend._2);
        } else {
            return inverseSign(subtractForSameSize(valueAndSubtrahend._2, valueAndSubtrahend._1));
        }

    }


    private static CharSequence addForSameSize(final CharSequence number, final CharSequence augend) {
        boolean increment = false;
        final Deque<Character> numbers = new LinkedList<>();
        for (int index = number.length() - 1; index >= 0; index--) {
            final byte a = toDigit(number, index);
            final byte b = toDigit(augend, index);

            final int sum = a + b + (increment ? 1 : 0);
            increment = sum >= 10;
            final boolean firstDigit = index == 0;
            final char digitToAdd = toChar(sum % 10);
            if (increment && firstDigit) {
                numbers.addFirst(digitToAdd);
                numbers.addFirst('1');
            } else {
                if (!firstDigit || digitToAdd != '0') {
                    numbers.addFirst(digitToAdd);
                }
            }
        }
        return asString(numbers);
    }

    private static CharSequence subtractForSameSize(final CharSequence number, final CharSequence subtrahend) {
        boolean shouldDecrement = false;
        final Deque<Character> digits = new LinkedList<>();
        for (int index = number.length() - 1; index >= 0; index--) {
            final byte digit1 = toDigit(number, index);
            final byte digit2 = toDigit(subtrahend, index);
            final boolean firstDigit = index == 0;
            int digitSubtraction = digit1 - digit2 - (shouldDecrement ? 1 : 0);
            shouldDecrement = false;
            if (digitSubtraction < 0) {
                // if the last digit
                if (!firstDigit) {
                    shouldDecrement = true;
                    digitSubtraction = 10 + digitSubtraction;
                    digits.addFirst(toChar(digitSubtraction));
                } else {
                    digits.addFirst(toChar(digitSubtraction * -1));
                    digits.addFirst(MINUS);
                }
            } else {
                digits.addFirst(toChar(digitSubtraction));
            }
        }
        return trailFrontZeros(digits);
    }

    private static CharSequence trailFrontZeros(final Collection<Character> digits) {
        final StringBuilder result = new StringBuilder(digits.size());
        boolean firstDigitPending = true;
        for (final char digit : digits) {
            if (firstDigitPending) {
                if (digit != '0') {
                    result.append(digit);
                    firstDigitPending = false;
                }
            } else {
                result.append(digit);
            }
        }

        return result.toString();
    }

    private static boolean isFirstNumberLarger(final CharSequence number1, final CharSequence number2) {
        final int length = number1.length();
        Preconditions.checkArgument(number2.length() == length, "numbers should be of equal size");
        for (int i = 0; i < length; i++) {
            final byte digit1 = toDigit(number1, i);
            final byte digit2 = toDigit(number2, i);
            if (digit1 > digit2) {
                return true;
            } else if (digit1 < digit2) {
                return false;
            }
        }
        return false;
    }

    private static CharSequence asString(final Collection<Character> numbers) {
        final StringBuilder result = new StringBuilder(numbers.size());
        for (final char digit : numbers) {
            result.append(digit);
        }

        return result.toString();
    }


}
