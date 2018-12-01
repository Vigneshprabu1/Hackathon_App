package com.example.ss4.hackathons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shinelogics on 4/16/2016.
 */
public class Utility {

    private static Pattern pattern;
    private static Matcher matcher;
    //Email Pattern
    private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public static boolean validate(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email.trim());
        return matcher.matches();

    }

    public static boolean isNotNull(String txt){
        return txt!=null && txt.trim().length()>0 ? true: false;
    }

    public static boolean passwordValidation(String password, String confirmPassword){

        return password.equals(confirmPassword) ? true: false;
    }
}
