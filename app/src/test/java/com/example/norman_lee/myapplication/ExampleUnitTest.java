package com.example.norman_lee.myapplication;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //TODO 5.4 Write unit tests to check the ExchangeRate class
    @Test
    public void exchangeRateDefaultRate(){
        String defaultExchangeRate = "2.95000";
        assertEquals( new BigDecimal(defaultExchangeRate), new ExchangeRate().getExchangeRate() );
    }

    @Test
    public void exchangeRateNewRate(){
        String newexchangerate = "37.00000";
        assertEquals(new BigDecimal(newexchangerate), new ExchangeRate("37.00000"));
    }

    @Test
    public void exchangeRateHomeForeign(){
        String home = "49";
        String foreign = "7";
        assertEquals(new BigDecimal("7.00000"), new ExchangeRate(home,foreign));
    }

    @Test
    public void testCalcAmt(){
        assertEquals(new BigDecimal("2.95000"), new ExchangeRate().calculateAmount("1"));
    }

    @Test
    public void emptystr(){
        assertEquals(new NumberFormatException(), new ExchangeRate(""));
    }
}