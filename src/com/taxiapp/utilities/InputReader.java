package com.taxiapp.utilities;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputReader {
    public static String scanStringWithMsg(String str){
        System.out.println(str);
        return scanString();
    }

    public static String scanString(){
        while (true){
            try{
                return new Scanner(System.in).next();
            }catch (InputMismatchException e) {
                System.out.println("Enter Valid Input");
            }
        }
    }
    public static int scanIntWithMsg(String str){
        System.out.println(str);
        return scanInt();
    }
    public static int scanInt() {
        int userInput = -1;
        while (true){
            try{
                userInput = new Scanner(System.in).nextInt();
                return userInput;
            }catch (InputMismatchException e) {
                System.out.println("Enter Valid Input");
            }
        }
    }

    public static double scanDouble() {
        double userInput = -1;
        while (true){
            try{
                userInput = new Scanner(System.in).nextDouble();
                return userInput;
            }catch (InputMismatchException e) {
                System.out.println("Enter Valid Input");
            }
        }
    }
    public static String scanNextLine(String str){
        System.out.println(str);
        while (true){
            try {
                return new Scanner(System.in).nextLine();
            }
            catch (InputMismatchException e){
                System.out.println("Enter valid Input ");
            }
        }
    }
}
