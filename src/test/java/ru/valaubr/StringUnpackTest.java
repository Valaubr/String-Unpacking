package ru.valaubr;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringUnpackTest {
    private StringUnpack stringUnpack = new StringUnpack();

    @Test
    public void testStringUnpackNormalData() {
        assertEquals(stringUnpack.unpackString("3[xyz]4[xy]z"), "xyzxyzxyzxyxyxyxyz");
        assertEquals(stringUnpack.unpackString("asd2[3[x]y]"), "asdxxxyxxxy");
        assertEquals(stringUnpack.unpackString("2[3[x]y]"), "xxxyxxxy");
        assertEquals(stringUnpack.unpackString("2[7[xyz]5[git]k]e"), "xyzxyzxyzxyzxyzxyzxyzgitgitgitgitgitkxyzxyzxyzxyzxyzxyzxyzgitgitgitgitgitke");
        assertEquals(stringUnpack.unpackString("2[2[we will ]rock you!\n]"), "we will we will rock you!\nwe will we will rock you!\n");
        assertEquals(stringUnpack.unpackString("4[2[HIDE! ]AND 2[S2[E]K! ]\n]LONG NIGHT IS COME!"), "HIDE! HIDE! AND SEEK! SEEK! \n" +
                "HIDE! HIDE! AND SEEK! SEEK! \n" +
                "HIDE! HIDE! AND SEEK! SEEK! \n" +
                "HIDE! HIDE! AND SEEK! SEEK! \n" +
                "LONG NIGHT IS COME!");
    }

    @Test
    public void testStringUnpackBigData() throws IOException {
        //Т.к. в задаче не указано что требуется писать строку в файл => храним ее в памяти
        //=> размер строки ограничен HEAP_SPACE
        BufferedReader fileReader = new BufferedReader(new FileReader("D:\\GitDirs\\String-Unpacking\\src\\main\\resources\\testFile.txt"));
        assertEquals(stringUnpack.unpackString("2[4[8[64[qa]]observer8[64[ta]]]4[8[64[ja]]observer8[64[tu]]]4[8[64[ou]]observer8[64[op]]]4[8[64[kr]]observer8[64[la]]observer]]"), fileReader.readLine());

    }

    @Test
    public void testStringUnpackIllegalData() {
        assertThrows(IllegalArgumentException.class, () -> {
            stringUnpack.unpackString("2[xxx");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            stringUnpack.unpackString("2xxx]");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            stringUnpack.unpackString("2xxx");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            stringUnpack.unpackString("2[[xxx]");
        });
        assertThrows(NullPointerException.class, () -> {
            stringUnpack.unpackString(null);
        });
    }
}