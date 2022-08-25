package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int LIMIT_DIGITS = 36;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the length of the secret code:");
        int length = readInt();
        if (length > LIMIT_DIGITS || length <= 0) {
            System.out.println("Error: can't generate a secret number with a length of " + length + " because there aren't enough unique digits.");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int range = readInt();
        if (range > LIMIT_DIGITS) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }
        if (range < length) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + range + " unique symbols.");
            return;
        }

        Code code = new Code(length, range);
        System.out.println("The secret is prepared: " + code.getCode() + " " + getRangeText(range) + ".");
        System.out.println("Okay, let's start a game!");


        int turn = 0;
        Digits guessingCode;
        while (!code.guessed) {
            System.out.println("Turn " + ++turn + ":");
            boolean success;
            do {
                success = true;
                String tempCode = "";
                try {
                    tempCode = scanner.nextLine();
                    guessingCode = new Digits(length, range, tempCode);
                    printTurn(code, guessingCode);
                } catch (RuntimeException e) {
                    System.out.println("Error: \"" + tempCode + "\" isn't a valid number.");
                    success = false;
                }
            } while (!success);
        }

        System.out.println("Congratulations! You guessed the secret code.");

    }

    private static String getRangeText(int range) {
        int index = range - 1;
        StringBuilder rangeText = new StringBuilder();

        char start = '0';
        char end = index >= 10 ? (char) ('0' + 9) : (char) ('0' + index);
        rangeText.append("(").append(start).append("-").append(end);

        if (index >= 10) {
            start = 'a';
            end = (char) ('a' + index - 10);
            rangeText.append(" ,").append(start).append("-").append(end);
        }
        rangeText.append(")");
        return rangeText.toString();
    }

    private static int readInt() {
        int num = 0;
        Scanner scanner = new Scanner(System.in);
        String tempText = "";
        try {
            tempText = scanner.nextLine();
            num = Integer.parseInt(tempText);
        } catch (Exception e) {
            System.out.println("Error: \"" + tempText + "\" isn't a valid number.");
            System.exit(1);
        }

        return num;
    }


    private static void printTurn(Code code, Digits g) {
        System.out.println("Grade: " + printGrade(code, g) + ".");
    }


    private static String printGrade(Code code, Digits guessingCode) {
        String msg = "";
        int countBulls = code.countBulls(guessingCode);
        int countCows = code.countCows(guessingCode);
        if (countCows == 0 && countBulls == 0) {
            msg = "None";
        } else {
            if (code.isAllEqual(guessingCode)) {
                msg = code.size + " bulls";
            } else {
                if (countBulls > 0) {
                    msg = msg + countBulls + " bull" + getPlural(countBulls);
                }
                if (countCows > 0) {
                    String separator = countBulls == 0 ? "" : " and ";
                    msg = msg + separator + countCows + " cow" + getPlural(countCows);
                }
            }
        }
        return msg;
    }

    private static String getPlural(int count) {
        return count > 1 ? "s" : "";
    }
}
