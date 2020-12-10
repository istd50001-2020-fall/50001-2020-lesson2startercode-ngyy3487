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
        assertEquals(new BigDecimal(defaultExchangeRate), new ExchangeRate().getExchangeRate());
    }

    @Test
    public void exchangeRate(){
        String exchangeRate = "7.80000";
        assertEquals(new BigDecimal(exchangeRate), new ExchangeRate(exchangeRate).getExchangeRate());
    }

    @Test
    public void exchangeRateHomeForeign(){
        String home = "65.00000";
        String foreign = "5.00000";
        BigDecimal result = new BigDecimal("13.00000");
        assertEquals(result, new ExchangeRate(home,foreign).getExchangeRate());
    }

    @Test
    public void calculateAmount(){
        ExchangeRate exrate = new ExchangeRate();
        assertEquals("2.95",exrate.calculateAmount("1").toString());
    }

    @Test (expected = NumberFormatException.class)
    public void emptyString(){
        String exrate = "xhdx";
        ExchangeRate exchangeRate = new ExchangeRate(exrate);
    }
}