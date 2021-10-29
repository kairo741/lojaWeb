package com.kairo.lojaWeb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDouble implements Converter<String, Double> {

    @Override
    public Double convert(String s) {
        s = s.trim();
        if (s.length() > 0) {
            s = s.replace(".", "").replace(",", ".");
            return Double.parseDouble(s);
        }
        return 0.;
    }
}
