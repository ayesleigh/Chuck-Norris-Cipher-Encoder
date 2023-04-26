package chucknorris;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final String INVALID_STRING = "Encoded string is not valid.";
    public static void main(String[] args) {
        String input;
        do {
            System.out.println();
            System.out.println("Please input operation (encode/decode/exit):");
            input = scanner.nextLine();
            if ("encode".equalsIgnoreCase(input)) {
                encode();
            } else if ("decode".equalsIgnoreCase(input)) {
                decode();
            } else if ("exit".equalsIgnoreCase(input)){
                System.out.println("Bye!");
                break;
            } else {
                System.out.printf("There is no '%s' operation", input);
            }
        } while (true);
    }

    static void encode() {
        System.out.println("Input string:");
        String inputString = scanner.nextLine();
        char[] characters = inputString.toCharArray();
        StringBuilder inputStringAsBinaryString = new StringBuilder();

        for (char ch : characters) {
            String characterAsBinaryString = Integer.toBinaryString(ch);
            String str = String.format("%7s", characterAsBinaryString);
            str = str.replace(' ','0');
            inputStringAsBinaryString.append(str);
        }

        String[] strings = inputStringAsBinaryString.toString().split("(?<=(.))" +
                "(?!\\1)");

        System.out.println("Encoded string:");

        for (String s : strings) {
            if (s.charAt(0) == '1') {
                System.out.print("0 ");
            } else {
                System.out.print("00 ");
            }
            System.out.print("0".repeat(s.length()) + " ");
        }
        System.out.println();
    }

    static void decode() {
        System.out.println("Input encoded string:");
        String inputString = scanner.nextLine();

        String inputStringWithoutSpaces = inputString.replaceAll(" ", "");
        for (int i = 0; i < inputStringWithoutSpaces.length(); i++) {
            if (inputStringWithoutSpaces.charAt(i) != '0') {
                System.out.println(INVALID_STRING);
                return;
            }
        }

        String[] inputStringAsArray = inputString.split(" ");
        if (inputStringAsArray.length % 2 == 1) {
            System.out.println(INVALID_STRING);
            return;
        }

        StringBuilder binary = new StringBuilder();

        for (int i = 1; i < inputStringAsArray.length; i += 2) {
            String symbol;
            if (inputStringAsArray[i - 1].equalsIgnoreCase("0")) {
                symbol = "1";
            } else if (inputStringAsArray[i - 1].equalsIgnoreCase("00")) {
                symbol = "0";
            } else {
                System.out.println(INVALID_STRING);
                return;
            }
            binary.append(symbol.repeat(inputStringAsArray[i].length()));
        }

        if (binary.length() % 7 != 0) {
            System.out.println(INVALID_STRING);
            return;
        }

        String[] binaryAsArray = new String[binary.length() / 7];

        for (int i = 0, j = 0; i < binary.length(); i += 7, j++) {
            binaryAsArray[j] = binary.substring(i, i + 7);
        }

        StringBuilder result = new StringBuilder();

        for (String s : binaryAsArray) {
            char ch = (char) Integer.parseInt(s, 2);
            result.append(ch);
        }

        System.out.println("Decoded string:");
        System.out.println(result);
    }
}