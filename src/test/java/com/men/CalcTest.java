package com.men;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalcTest {

    Calc underTest = new Calc();

    @Test
    void shouldSuccessSum () {
        int sum = underTest.sum(2, 3);
        assertEquals(5, sum);
    }

    @Test
    void shouldFailureSum () {
        assertNotEquals(5, underTest.sum(3,3));
    }
}