package com.example.demo.OCR;

import java.util.Arrays;

public class OcrEntry {
    public String text;
    public int[][] box;
    public double score;

    @Override
    public String toString() {
        return "RecognizedText{" +
                "text='" + text + '\'' +
                ", box=" + Arrays.toString(box) +
                ", score=" + score +
                '}';
    }
}
