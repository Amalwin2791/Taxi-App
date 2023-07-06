package com.taxiapp.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static String validateAndRefetchInputIfInvalid(String input, String regex, String name){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()){
            return input;
        }else{
            return validateAndRefetchInputIfInvalid(InputReader.scanStringWithMsg("Please enter valid "+name),regex,name);
        }
    }
    public static String checkEmailIdIsValid(String email){
        return validateAndRefetchInputIfInvalid(email,"^[a-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$","Email-ID");
    }
    public static String validatePhoneNumber(String number){
        return validateAndRefetchInputIfInvalid(number,"(0|91)?[7-9][0-9]{9}","Phone Number");

    }
    public static String validateId(String number){
        return validateAndRefetchInputIfInvalid(number,"(0|91)?[7-9][0-9]{9}","ID");

    }
    public static String validateAccNumber(String accNumber){
        return validateAndRefetchInputIfInvalid(accNumber,"^\\d{9,18}$","Account Number");
    }
    public static String validatePin(String pin){
        return validateAndRefetchInputIfInvalid(pin,"^([0-9]{4})$","PIN");
    }
    public static String checkPasswordIsValid(String password){
        return validateAndRefetchInputIfInvalid(password,"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$","Password");
    }
    public static String checkTimeIsValid(String time){
        return  validateAndRefetchInputIfInvalid(time,"^([01]?[0-9]|2[0-3]).([0-5][0-9])$","Time");
    }
    public static String nameIsValid(String userName){
        return validateAndRefetchInputIfInvalid(userName, "([a-zA-Z',.-]+( [a-zA-Z',.-]+)*){2,30}","User Name");
    }
    public static String pointIsValid(String userName){
        return validateAndRefetchInputIfInvalid(userName, "([a-zA-Z',.-]+( [a-zA-Z',.-]+)*){2,30}","Point");
    }
    public static String checkIfYesOrNo(String choice){
        return validateAndRefetchInputIfInvalid(choice,"^[ynYN]$","Y or N");
    }
    public static int getNumberInRange(int max) {
        int input = 0;
        boolean isNotValid = true;
        while (isNotValid) {
            input = InputReader.scanInt();

            if (input > 0 && input <= max)
                isNotValid = false;
            else
                System.out.println("Enter a input between 1 and "+ max);
        }
        return input;
    }
    public static String checkHours(String time) {
        return  validateAndRefetchInputIfInvalid(time,"^(0?[1-9]|1[0-9]|2[0-4])$","Time");
    }
    public static double getRatingInRange( double max) {
        double input = 0;
        boolean isNotValid = true;
        while (isNotValid) {
            input = InputReader.scanDouble();

            if (input > 0 && input <= max)
                isNotValid = false;
            else
                System.out.println("Enter a input between 1 and "+ max);
        }
        return input;
    }
    public static boolean timeFormatIsValid(String time){
        return time.contains(".");
    }
    public static String licenseIsValid(String licenseNumber){
        return validateAndRefetchInputIfInvalid(licenseNumber,"^(([A-Z]{2}[0-9]{2})( )|([A-Z]{2}-[0-9]{2}))((19|20)[0-9][0-9])[0-9]{7}$","License Number ");
    }
    public static int getValidLocation( int max, int source) {
        int input = 0;
        boolean isNotValid = true;
        while (isNotValid) {
            input = InputReader.scanInt();

            if (input > 0 && input <= max && input!= source)
                isNotValid = false;
            else
                System.out.println("Enter a input between 1 and "+ max + " but not "+ source);
        }
        return input;
    }
}
