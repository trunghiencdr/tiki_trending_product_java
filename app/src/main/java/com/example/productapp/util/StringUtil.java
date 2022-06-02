package com.example.productapp.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class StringUtil {
    public static String formatCurrency(double number){
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(number);
    }

}
