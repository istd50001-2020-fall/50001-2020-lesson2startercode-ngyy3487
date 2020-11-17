package com.example.norman_lee.myapplication;

public class Utils {
    static void checkInvalidInputs (String value){
        Double valuedouble = Double.valueOf(value);
        if (valuedouble <=0){
            throw new IllegalArgumentException();
        }
    }
}
