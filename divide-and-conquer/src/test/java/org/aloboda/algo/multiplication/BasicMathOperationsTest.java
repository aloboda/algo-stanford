package org.aloboda.algo.multiplication;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("DuplicateStringLiteralInspection")
public class BasicMathOperationsTest {
    private final BasicMathOperations operations = new BasicMathOperations();

    @Test
    public void when_add_positive_numbers_of_equal_size_then_calculate_correctyl() {
        assertThat(this.operations.add("124", "986"), is("1110"));
        assertThat(this.operations.add("986", "124"), is("1110"));
        assertThat(this.operations.add("10000", "10001"), is("20001"));
    }

    @Test
    public void when_add_positive_numbers_of_different_size_then_calculate_correctly() {
        assertThat(this.operations.add("123445", "15"), is("123460"));
        assertThat(this.operations.add("15", "123445"), is("123460"));
        assertThat(this.operations.add("1", "1000000001"), is("1000000002"));
    }

    @Test
    public void when_add_negative_numbers_of_equal_size_then_calculate_correctly() {
        assertThat(this.operations.add("-124", "-986"), is("-1110"));
        assertThat(this.operations.add("-986", "-124"), is("-1110"));
        assertThat(this.operations.add("-10000", "-10001"), is("-20001"));
    }

    @Test
    public void when_add_negative_numbers_of_different_size_then_calculate_correctly() {
        assertThat(this.operations.add("-123445", "-15"), is("-123460"));
        assertThat(this.operations.add("-15", "-123445"), is("-123460"));
        assertThat(this.operations.add("-1", "-1000000001"), is("-1000000002"));
    }

    @Test
    public void when_add_different_sign_numbers_then_calculate_correctly() {
        assertThat(this.operations.add("-124", "1110"), is("986"));
        assertThat(this.operations.add("-1110", "124"), is("-986"));
        assertThat(this.operations.add("-10000", "10001"), is("1"));
        assertThat(this.operations.add("10000", "-10001"), is("-1"));
    }

    @Test
    public void when_subtract_uneven_numbers() {
        assertThat(this.operations.subtract("1110", "986"), is("124"));
        assertThat(this.operations.subtract("986", "1110"), is("-124"));

        assertThat(this.operations.subtract("986", "-1110"), is("2096"));
        assertThat(this.operations.subtract("-986", "1110"), is("-2096"));

        assertThat(this.operations.subtract("-1110", "-986"), is("-124"));
        assertThat(this.operations.subtract("-986", "-1110"), is("124"));
    }

    @Test
    public void when_subtraction_leads_to_zero_then_return_zero() {
        assertThat(this.operations.subtract("10001", "10001"), is("0"));
        assertThat(this.operations.subtract("-10001", "-10001"), is("0"));
        assertThat(this.operations.subtract("0", "0"), is("0"));
    }
}
