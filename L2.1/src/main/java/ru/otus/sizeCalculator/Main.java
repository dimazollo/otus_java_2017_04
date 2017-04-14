package ru.otus.sizeCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Supplier;

/**
 * @Author Dmitry Volovod
 * created on 13.04.2017
 */
// VM options -Xmx512m -Xms512m
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        ObjectSizeCalculator sizer = new ObjectSizeCalculator();
        int sampleSize;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Specify sample size: ");

        sampleSize = Integer.parseInt(bufferedReader.readLine());

        Supplier<String> stringSupplier = () -> new String().intern();
        System.out.println("interned String : " + sizer.estimateObjectSize(stringSupplier, 1) + " bytes");

        stringSupplier = () -> new String();
        System.out.println("empty String : " + sizer.estimateObjectSize(stringSupplier, sampleSize) + " bytes");

        Supplier<Object> objectSupplier = () -> new Object();
        System.out.println("empty Object : " + sizer.estimateObjectSize(objectSupplier, sampleSize) + " bytes");


        Supplier<Object> intArray = () -> new int[0];
        System.out.println("int[0] : " + sizer.estimateObjectSize(intArray, sampleSize) + " bytes");
        intArray = () -> new int[1];
        System.out.println("int[1] : " + sizer.estimateObjectSize(intArray, sampleSize) + " bytes");
        intArray = () -> new int[2];
        System.out.println("int[2] : " + sizer.estimateObjectSize(intArray, sampleSize) + " bytes");
        intArray = () -> new int[3];
        System.out.println("int[3] : " + sizer.estimateObjectSize(intArray, sampleSize) + " bytes");
    }
}
