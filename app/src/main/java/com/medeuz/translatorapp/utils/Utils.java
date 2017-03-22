package com.medeuz.translatorapp.utils;


public class Utils {

    public enum CountryCode {

        RU ("ru"),
        EN ("en");

        private final String countryCode;

        CountryCode(String countryCode) {
            this.countryCode = countryCode;
        }
    }

}
