package ru.valaubr;

public class StringUnpack {
    StringBuilder stringBuilder;

    public String unpackString(String compressedString) {
        if (compressedString == null) {
            throw new NullPointerException("Trying unpack null object");
        } else if (compressedString.isEmpty()) {
            return "";
        }
        stringBuilder = new StringBuilder();
        recursiveRead(compressedString, stringBuilder, 1);
        return stringBuilder.toString();
    }

    // Memory:
    // Worst case scenario: O(n * n/2 + n/2)
    // Normal case scenario: O(n)
    // Complexity:
    // Worst case scenario: O(n)
    // Normal case scenario: O(n)
    private int recursiveRead(String compressedString, StringBuilder stringBuilder, int cycle) {
        int lvl = 0;
        int returnValue = 0;
        int subCycle = 0;
        for (int i = 0; i < cycle; i++) {
            for (int j = 0; j < compressedString.length(); j++) {
                if (!Character.isDigit(compressedString.charAt(j))
                        && compressedString.charAt(j) != '['
                        && compressedString.charAt(j) != ']'
                        && lvl == 0) {
                    stringBuilder.append(compressedString.charAt(j));
                } else if (Character.isDigit(compressedString.charAt(j)) && lvl == 0) {
                    if (compressedString.charAt(j + 1) == '[') {
                        lvl += 1;
                        subCycle += Character.digit(compressedString.charAt(j), 10);
                        j += recursiveRead(compressedString.substring(j + 2), stringBuilder, subCycle);
                        subCycle = 0;
                    } else if (Character.isDigit(compressedString.charAt(j + 1))) {
                        if (subCycle == 0) {
                            subCycle = Character.digit(compressedString.charAt(j), 10) * 10;
                        } else {
                            subCycle += subCycle * 10;
                        }
                    } else {
                        throw new IllegalArgumentException("Open digit not found");
                    }
                } else if (compressedString.charAt(j) == ']') {
                    if (lvl == 0) {
                        returnValue = j;
                        break;
                    }
                    lvl -= 1;
                } else if (compressedString.charAt(i) == '[' && i == 0 || compressedString.charAt(i) == '[' && !Character.isDigit(compressedString.charAt(i - 1))) {
                    throw new IllegalArgumentException();
                }
            }
        }
        if (lvl != 0) {
            throw new IllegalArgumentException("']' not found");
        }
        return returnValue + 1;
    }
}
