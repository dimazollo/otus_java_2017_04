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

        Supplier<Object> objectSupplier = () -> new Object();
        System.out.println("empty Object : " + sizer.estimateObjectSize(objectSupplier, sampleSize) + " bytes");

        Supplier<Byte> byteSupplier = () -> new Byte("0");
        System.out.println("Byte : " + sizer.estimateObjectSize(byteSupplier, sampleSize) + " bytes");

        Supplier<Boolean> booleanSupplier = () -> new Boolean(false);
        System.out.println("Boolean : " + sizer.estimateObjectSize(booleanSupplier, sampleSize) + " bytes");

        Supplier<Short> shortSupplier = () -> new Short("0");
        System.out.println("Short : " + sizer.estimateObjectSize(shortSupplier, sampleSize) + " bytes");

        Supplier<Character> charSupplier = () -> new Character('a');
        System.out.println("Character : " + sizer.estimateObjectSize(charSupplier, sampleSize) + " bytes");

        Supplier<Integer> intSupplier = () -> new Integer(0);
        System.out.println("Integer : " + sizer.estimateObjectSize(intSupplier, sampleSize) + " bytes");

        Supplier<Float> floatSupplier = () -> new Float(0f);
        System.out.println("Float : " + sizer.estimateObjectSize(floatSupplier, sampleSize) + " bytes");

        Supplier<Long> longSupplier = () -> new Long(0L);
        System.out.println("Long : " + sizer.estimateObjectSize(longSupplier, sampleSize) + " bytes");

        Supplier<Double> doubleSupplier = () -> new Double(.0d);
        System.out.println("Double : " + sizer.estimateObjectSize(doubleSupplier, sampleSize) + " bytes");
        System.out.println();

        Supplier<String> stringSupplier = () -> new String("A").intern();
        System.out.println("interned String : " + sizer.estimateObjectSize(stringSupplier, 1) + " bytes");

        stringSupplier = () -> new String();
        System.out.println("empty String : " + sizer.estimateObjectSize(stringSupplier, sampleSize) + " bytes");
        System.out.println();

        Supplier<Object> intArraySupplier = () -> new int[0];
        System.out.println("int[0] : " + sizer.estimateObjectSize(intArraySupplier, sampleSize) + " bytes");
        intArraySupplier = () -> new int[1];
        System.out.println("int[1] : " + sizer.estimateObjectSize(intArraySupplier, sampleSize) + " bytes");
        intArraySupplier = () -> new int[2];
        System.out.println("int[2] : " + sizer.estimateObjectSize(intArraySupplier, sampleSize) + " bytes");
        intArraySupplier = () -> new int[3];
        System.out.println("int[3] : " + sizer.estimateObjectSize(intArraySupplier, sampleSize) + " bytes");
    }
}
