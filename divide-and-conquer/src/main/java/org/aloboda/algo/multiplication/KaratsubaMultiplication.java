package org.aloboda.algo.multiplication;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.vavr.Tuple;
import io.vavr.Tuple2;

import static org.aloboda.algo.multiplication.NumberUtil.addTrailZeros;
import static org.aloboda.algo.multiplication.NumberUtil.alignToHaveTheSameSize;

/**
 * Karatsuba Multiplication algorithm for large integer(of any length) numbers.
 */
public class KaratsubaMultiplication {
    private final BasicMathOperations basicMath;

    @VisibleForTesting
    KaratsubaMultiplication(final BasicMathOperations basicMath) {
        this.basicMath = basicMath;
    }

    /**
     * Instantiates this service
     */
    public KaratsubaMultiplication() {
        this(new BasicMathOperations());
    }

    /**
     * @param multiplier   multiplicand to be multiplied by this
     * @param multiplicand base number
     * @return product of passed params
     */
    public CharSequence multiply(final CharSequence multiplier, final CharSequence multiplicand) {
        final Tuple2<CharSequence, CharSequence> multiplierAndMultiplicand = alignToHaveTheSameSize(multiplier, multiplicand);
        return this.multiplyForSameSize(multiplierAndMultiplicand._1, multiplierAndMultiplicand._2);
    }

    private CharSequence multiplyForSameSize(final CharSequence multiplier, final CharSequence multiplicand) {
        final int digitsCount = multiplier.length();
        Preconditions.checkArgument(digitsCount == multiplicand.length(),
                "terms should be of the same size, multiplier: %s digits, multiplicand: %s", digitsCount, multiplicand.length());
        if (digitsCount <= 2) {
            return String.valueOf(Integer.parseInt(multiplier.toString()) * Integer.parseInt(multiplicand.toString()));
        }
        final Tuple2<CharSequence, CharSequence> abTuple = splitInHalf(multiplier);
        final Tuple2<CharSequence, CharSequence> cdTuple = splitInHalf(multiplicand);
        final CharSequence ac = this.multiply(abTuple._1, cdTuple._1);
        final CharSequence bd = this.multiply(abTuple._2, cdTuple._2);
        final CharSequence tuplesProduct = this.multiply(
                this.basicMath.add(abTuple._1, abTuple._2),
                this.basicMath.add(cdTuple._1, cdTuple._2)
        );
        final CharSequence sumAdBc = this.basicMath.subtract(
                this.basicMath.subtract(tuplesProduct, ac),
                bd
        );
        final int length = digitsCount % 2 == 0 ? digitsCount : digitsCount + 1;
        final CharSequence firstPart = addTrailZeros(ac, length);
        final int halfLength = length / 2;
        final CharSequence secondPart = addTrailZeros(sumAdBc, halfLength);
        return this.basicMath.add(
                this.basicMath.add(firstPart, secondPart),
                bd
        );
    }

    private static Tuple2<CharSequence, CharSequence> splitInHalf(final CharSequence number) {
        final int length = number.length();
        final int halfPoint = length / 2;
        return Tuple.of(number.subSequence(0, halfPoint), number.subSequence(halfPoint, length));
    }

}
