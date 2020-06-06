package org.aloboda.algo.multiplication;

import com.google.common.annotations.VisibleForTesting;
import io.vavr.Tuple;
import io.vavr.Tuple2;

import static org.aloboda.algo.multiplication.NumberUtil.addTrailZeros;

public class KaratsubaMultiplication {
    private final ThirdGradeAddition addition;

    @VisibleForTesting
    KaratsubaMultiplication(final ThirdGradeAddition addition) {
        this.addition = addition;
    }

    KaratsubaMultiplication() {
        this(new ThirdGradeAddition());
    }

    public CharSequence multiply(final CharSequence multiplier, final CharSequence multiplicand) {
        if (multiplier.length() <= 4 && multiplicand.length() <= 4) {
            return String.valueOf(Integer.parseInt(multiplier.toString()) * Integer.parseInt(multiplicand.toString()));
        }
        final Tuple2<CharSequence, CharSequence> abTuple = splitInHalf(multiplier);
        final Tuple2<CharSequence, CharSequence> cdTuple = splitInHalf(multiplicand);
        final CharSequence ac = this.multiply(abTuple._1, cdTuple._1);
        final CharSequence bd = this.multiply(abTuple._2, cdTuple._2);
        final CharSequence tuplesProduct = this.multiply(
                this.addition.add(abTuple._1, abTuple._2),
                this.addition.add(cdTuple._1, cdTuple._2)
        );
        final CharSequence sumAdBc = this.addition.subtract(
                this.addition.subtract(tuplesProduct, ac),
                bd
        );
        final int length = multiplier.length() % 2 == 0 ? multiplier.length() : multiplier.length() + 1;
        final CharSequence firstPart = addTrailZeros(ac, length);
        final int halfLength = length / 2;
        final CharSequence secondPart = addTrailZeros(sumAdBc, halfLength);
        return this.addition.add(
                this.addition.add(firstPart, secondPart),
                bd
        );
    }

    private static Tuple2<CharSequence, CharSequence> splitInHalf(final CharSequence number) {
        final int length = number.length();
        final int halfPoint = length / 2;
        return Tuple.of(number.subSequence(0, halfPoint), number.subSequence(halfPoint, length));
    }

}
