package com.yosua.homie.libraries.utility;

import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.libraries.exception.InvalidPasswordException;
import org.passay.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PasswordHelper {

    private PasswordHelper() {}

    public static String encryptPassword(String rawPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    public static boolean matchPassword(String rawPassword, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(rawPassword, password);
    }

    public static boolean isPasswordValid(String password) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new UppercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new SpecialCharacterRule(1),
                new NumericalSequenceRule(3,false),
                new AlphabeticalSequenceRule(3,false),
                new QwertySequenceRule(3,false),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (!result.isValid()) {
            Set<String> errorList = new HashSet<>();
            for (RuleResultDetail ruleResultDetail : result.getDetails()) {
                errorList.add(ruleResultDetail.getErrorCode());
            }
            throw new InvalidPasswordException(ResponseCode.INVALID_PASSWORD.getCode(), ResponseCode.INVALID_PASSWORD.getMessage(), new ArrayList<>(errorList));
        }
        return true;
    }
}
