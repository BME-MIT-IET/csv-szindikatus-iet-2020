package com.complexible.common.csv.provider;

import com.google.common.io.BaseEncoding;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;


public abstract class ValueProvider {

    private final String placeholder = UUID.randomUUID().toString();

    private boolean hashed;

    public String provide(int rowIndex, String[] row) {
        String value = provideValue(rowIndex, row);
        if (value != null && hashed) {
            String hash = hash(value);
            value = BaseEncoding.base32Hex().omitPadding().lowerCase().encode(hash.getBytes());
        }
        return value;
    }

    public String hash(String message) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(message);        
     }

     protected abstract String provideValue(int rowIndex, String[] row);

    public String getPlaceholder() {
        return placeholder;
    }

    public boolean isHashed() {
        return hashed;
    }

    public void setHashed(boolean hashed) {
        this.hashed = hashed;
    }
}
