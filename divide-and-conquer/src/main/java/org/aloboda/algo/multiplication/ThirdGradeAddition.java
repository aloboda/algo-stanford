package org.aloboda.algo.multiplication;

import com.google.common.base.Preconditions;
import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.Deque;
import java.util.LinkedList;

import static org.aloboda.algo.multiplication.NumberUtil.addFrontZeros;

public class ThirdGradeAddition {

    CharSequence subtract(final CharSequence value, final CharSequence subtrahend) {
        final boolean valuePositive = value.charAt(0) != '-';
        final boolean subtrahendPositive = subtrahend.charAt(0) != '-';
        if (valuePositive && subtrahendPositive) { // both positive
            return subtractPositives(value, subtrahend);
        } else if (valuePositive) { // value=+ and subtrahend=-
            return addPositives(value, inverseSign(subtrahend));
        } else if (subtrahendPositive) { // value=- and subtrahend=+
            return inverseSign(addPositives(subtrahend, inverseSign(value)));
        } else { // both negative
            return inverseSign(subtractPositives(inverseSign(value), inverseSign(subtrahend)));
        }
    }

    CharSequence add(final CharSequence value, final CharSequence augend) {
        final boolean valuePositive = value.charAt(0) != '-';
        final boolean augendPositive = augend.charAt(0) != '-';
        if (valuePositive && augendPositive) { // both positive
            return addPositives(value, augend);
        } else if (valuePositive) { // value=+ and augend=- :
            return subtractPositives(value, inverseSign(augend));
        } else if (augendPositive) { // value=- and augend=+
            return subtractPositives(augend, inverseSign(value));
        } else { // both negative
            return inverseSign(addPositives(inverseSign(value), inverseSign(augend)));
        }
    }

    private static CharSequence inverseSign(final CharSequence number) {
        if (number.charAt(0) == '-') {
            return number.toString().substring(1);
        }
        return '-' + number.toString();
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

    private static boolean isFirstNumberLarger(final CharSequence number1, final CharSequence number2) {
        final int length = number1.length();
        Preconditions.checkArgument(number2.length() == length, "numbers should be of equal size");
        for (int i = 0; i < length; i++) {
            final byte digit1 = extractDigit(number1, i);
            final byte digit2 = extractDigit(number2, i);
            if (digit1 > digit2) {
                return true;
            } else if (digit1 < digit2) {
                return false;
            }
        }
        return false;
    }

    private static CharSequence addForSameSize(final CharSequence value, final CharSequence augend) {
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

    private static CharSequence subtractForSameSize(final CharSequence value, final CharSequence subtrahend) {
        boolean decrement = false;
        final Deque<Character> digits = new LinkedList<>();
        for (int index = value.length() - 1; index >= 0; index--) {
            final byte a = extractDigit(value, index);
            final byte b = extractDigit(subtrahend, index);
            final boolean firstDigit = index == 0;
            int digitSubtraction = a - b - (decrement ? 1 : 0);
            decrement = false;
            if (digitSubtraction < 0) {
                // if the last digit
                if (!firstDigit) {
                    decrement = true;
                    digitSubtraction = 10 + digitSubtraction;
                    digits.addFirst(String.valueOf(digitSubtraction).charAt(0));
                } else {
                    digits.addFirst(String.valueOf(digitSubtraction * -1).charAt(0));
                    digits.addFirst('-');
                }
            } else {
                digits.addFirst(String.valueOf(digitSubtraction).charAt(0));
            }
        }
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

    private static byte extractDigit(final CharSequence number, final int index) {
        try {
            return Byte.parseByte(String.valueOf(number.charAt(index)));
        } catch (final NumberFormatException e) {
            throw new IllegalStateException(String.format("Can't extract digit: %s", number.charAt(index)));
        }
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

}
