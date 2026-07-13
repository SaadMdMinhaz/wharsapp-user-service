package com.whatsapp.userservice.util;

import java.util.regex.Pattern;

public final class PhoneValidator {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$");

    private PhoneValidator() {
    }

    /**
     * Validates a phone number using E.164 format.
     *
     * @param phoneNumber the phone number to validate
     * @return true if the phone number matches E.164 format
     */
    public static boolean isValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phoneNumber.trim()).matches();
    }
}
