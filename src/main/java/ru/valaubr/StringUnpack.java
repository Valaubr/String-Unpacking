package ru.valaubr;

import java.util.Arrays;
import java.util.List;

public class StringUnpack {

    public String unpackString(String compressedString) {
        if (compressedString == null) {
            throw new NullPointerException("Trying unpack null object");
        } else if (compressedString.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        return recursiveRead(compressedString, stringBuilder, 1).toString();
    }

    private StringBuilder recursiveRead(String compressedString, StringBuilder stringBuilder, int cycle) {
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
                            subCycle += Character.digit(compressedString.charAt(i), 10);
                            leftPos = i + 2;
                        }
                    } else if (Character.isDigit(compressedString.charAt(i + 1))) {
                        if (lvl == 0) {
                            if (subCycle == 0) {
                                subCycle = Character.digit(compressedString.charAt(i), 10) * 10;
                            } else {
                                subCycle += subCycle * 10;
                            }
                        }
                    } else {
                        throw new IllegalArgumentException("Open digit not found");
                    }
                } else if (compressedString.charAt(i) == '[' && i == 0 || compressedString.charAt(i) == '[' && !Character.isDigit(compressedString.charAt(i - 1))) {
                    throw new IllegalArgumentException();
                } else if (compressedString.charAt(i) == ']') {
                    rightPos = i;
                    lvl -= 1;
                    if (lvl == 0) {
                        recursiveRead(compressedString.substring(leftPos, rightPos), stringBuilder, subCycle);
                        subCycle = 0;
                    }
                } else if (lvl == 0) {
                    stringBuilder.append(compressedString.charAt(i));
                }
            }
        }
        if (leftPos > rightPos || lvl != 0) {
            throw new IllegalArgumentException("']' not found");
        }
        return stringBuilder;
    }
}