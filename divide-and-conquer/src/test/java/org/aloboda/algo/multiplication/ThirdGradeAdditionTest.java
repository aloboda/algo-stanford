package org.aloboda.algo.multiplication;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("DuplicateStringLiteralInspection")
public class ThirdGradeAdditionTest {
    private final ThirdGradeAddition addition = new ThirdGradeAddition();

    @Test
    public void when_add_symmetric_uneven_numbers_then_calculate_correctyl() {
        final String result = this.addition.add("124", "986").toString();
        // Then...
        assertThat(result, is("1110"));
    }

    @Test
    public void when_add_shorter_number_to_longer_then_calculate_correctyl() {
        final String result = this.addition.add("123445", "15").toString();
        // Then...
        assertThat(result, is("123460"));
    }

    @Test
    public void when_add_longer_number_to_shorter_then_calculate_correctyl() {
        final String result = this.addition.add("15", "123445").toString();
        // Then...
        assertThat(result, is("123460"));
    }

    @Test
    public void when_add_asymmetric_small_numbers_with_front_zeros_then_calculate_correctyl() {
        final String result = this.addition.add("015", "22").toString();
        // Then...
        assertThat(result, is("37"));
    }
}
