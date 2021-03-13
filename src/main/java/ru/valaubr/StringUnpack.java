package ru.valaubr;

public class Main {

    private static String EXCEPTION_MESSAGE = "Open substring digit not found";

    public static void main(String[] args) throws IllegalStringFormatException {
        unpackString("2[3[x]y]");
        System.out.println(unpackString("3[xyz]4[xy]z"));
        System.out.println(unpackString("2[3[xyz]4[xy]z]"));
    }

    public static String unpackString(String compressedString) throws IllegalStringFormatException {
        if (compressedString == null) {
            throw new NullPointerException("Trying unpack null object");
        } else if (compressedString.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        return recursiveRead(compressedString, stringBuilder, 1).toString();
    }

    private static StringBuilder recursiveRead(String compressedString, StringBuilder stringBuilder, int cycle) throws IllegalStringFormatException {
        int leftPos = 0;
        int rightPos = 0;
        int subCycle = 0;
        int lvl = 0;
        for (int j = 0; j < cycle; j++) {
            for (int i = 0; i < compressedString.length(); i++) {
                if (Character.isDigit(compressedString.charAt(i))) {
                    if (compressedString.charAt(i + 1) == '[') {
                        lvl += 1;
                        if (lvl == 1) {
                            subCycle = Character.digit(compressedString.charAt(i), 10);
                            leftPos = i + 2;
                        }
                    } else {
                        throw new IllegalStringFormatException(EXCEPTION_MESSAGE);
                    }
                } else if (compressedString.charAt(i) == ']') {
                    rightPos = i;
                    lvl -= 1;
                    if (lvl == 0) {
                        recursiveRead(compressedString.substring(leftPos, rightPos), stringBuilder, subCycle);
                    }
                } else if (lvl == 0) {
                    stringBuilder.append(compressedString.charAt(i));
                }
            }
        }
        if (leftPos > rightPos) {
            throw new IllegalStringFormatException(EXCEPTION_MESSAGE);
        }
        return stringBuilder;
    }
}