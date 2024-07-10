package com.minimalcode.financemanager;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BigDecimal one = BigDecimal.valueOf(1.00);
        BigDecimal two = BigDecimal.valueOf(2.00);
        System.out.println(one.subtract(two));
    }
}
