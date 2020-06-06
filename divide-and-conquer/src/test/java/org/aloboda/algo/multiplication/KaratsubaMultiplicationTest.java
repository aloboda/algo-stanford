package org.aloboda.algo.multiplication;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class KaratsubaMultiplicationTest {
    private final KaratsubaMultiplication multiplier = new KaratsubaMultiplication();

    @Test
    public void when_multiply_small_ints_then_multiply_correctly() {
        // When...
        final String result = this.multiplier.multiply("1", "2").toString();
        // Then...
        assertThat(result, is("2"));
    }

    @Test
    public void when_multiply_power_of_two_ints_then_multiply_correctly() {
        // When...
        final String result = this.multiplier.multiply("1024", "1024").toString();
        // Then...
        assertThat(result, is("1048576"));
    }

    @Test
    public void when_multiply_even_numbers() {
        final String result = this.multiplier.multiply("1024", "1024").toString();
        // Then...
        assertThat(result, is("1048576"));
    }

    @Test
    public void when_multiply_uneven_numbers() {
        final String result = this.multiplier.multiply("102", "102").toString();
        // Then...
        assertThat(result, is("10404"));
    }

    @Test
    public void when_multiply_64bit_numbers_then_multiply_correctly() {
        final String multiplier = "3141592653589793238462643383279502884197169399374405820974944592";
        final String multiplicand = "2718281828459045235360287471352662497757247093779959574966967627";
        // When...
        final String result = this.multiplier.multiply(multiplier, multiplicand).toString();
        // Then...
        assertThat(result, is("8539734222673567065463550869546574495034888536014539576886863254480002314325618215605218551200611566988893585893052806582723184"));
    }

    @Test
    public void when_multiply_6dbit_numbers_then_multiply_correctly() {
        final String multiplier = "314159";
        final String multiplicand = "271828";
        // When...
        final String result = this.multiplier.multiply(multiplier, multiplicand).toString();
        // Then...
        assertThat(result, is("85397212652"));
    }

    @Test
    public void when_multiply_uneven_numbers_then_multiply_correctly() {
        assertThat(this.multiplier.multiply("77", "109"), is("8393"));
        assertThat(this.multiplier.multiply("109", "77"), is("8393"));
    }


}
