package com.nnk.springboot.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordUtilsTest {

    @Test
    void given_nullPassword_when_isValid_then_returnFalse() {
        assertThat(PasswordUtils.isValid(null)).isFalse();
    }

    @Test
    void given_blankPassword_when_isValid_then_returnFalse() {
        assertThat(PasswordUtils.isValid("   ")).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Sh0rt!",        // too short (< 8 chars)
            "alllower1!",    // no uppercase
            "ALLUPPER1!",    // no lowercase
            "NoDigitHere!",  // no digit
            "NoSpecial123"   // no special character
    })
    void given_weakPassword_when_isValid_then_returnFalse(String password) {
        assertThat(PasswordUtils.isValid(password)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Admin1!XX",
            "Secure#9Pass",
            "P@ssw0rd",
            "Valid$1Password"
    })
    void given_validPassword_when_isValid_then_returnTrue(String password) {
        assertThat(PasswordUtils.isValid(password)).isTrue();
    }
}
