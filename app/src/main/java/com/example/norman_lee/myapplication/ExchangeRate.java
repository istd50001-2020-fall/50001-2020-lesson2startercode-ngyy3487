package com.example.norman_lee.myapplication;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ExchangeRate {

    private BigDecimal exchangeRate;
    private static String defaultRate = "2.95000";
    private static final int DEFAULT_PRECISION = 5;
    private int precision = DEFAULT_PRECISION;
    private MathContext mathContext;


    ExchangeRate(){
        exchangeRate = new BigDecimal(defaultRate);
        instantiateMathContext(DEFAULT_PRECISION);
    }

    ExchangeRate(String exchangeRate){
        this.exchangeRate = new BigDecimal(exchangeRate);
        instantiateMathContext(DEFAULT_PRECISION);
    }

    ExchangeRate(String home, String foreign) {

        instantiateMathContext(DEFAULT_PRECISION);
        //TODO 3.13a The constructor initializes exchangeRate by calculating the exchangeRate
        BigDecimal homeno = new BigDecimal(home);
        BigDecimal foreignno = new BigDecimal(foreign);
        exchangeRate = homeno.divide(foreignno).setScale(5,BigDecimal.ROUND_UP);
    }

    BigDecimal getExchangeRate(){
        return exchangeRate;
    }

    BigDecimal calculateAmount(String foreign){
        BigDecimal foreigncurrency = new BigDecimal(foreign);
        BigDecimal homecurrency = foreigncurrency.multiply(this.exchangeRate);
        return homecurrency.setScale(2,BigDecimal.ROUND_UP);
    }

    void setPrecision(int precision){
        this.precision = precision;
        instantiateMathContext(precision);
    }

    private void instantiateMathContext(int precision){
        mathContext = new MathContext(precision, RoundingMode.HALF_UP);
    }
}
