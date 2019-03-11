package com.sample.servicecomb.common.util;

import com.sample.servicecomb.common.enums.BasicOperation;
import com.sample.servicecomb.common.enums.Operation;

import java.util.stream.IntStream;

/**
 * @ClassName TestMain
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/11 11:32
 */
public class TestMain {
    private static final String PREFIX = "ALEX-";
    public static void main(String[] args) {
        Operation operation = BasicOperation.MINUS;
        double result = operation.apply(1,2);
        System.out.println(result);
        IntStream.range(0,5).mapToObj(TestMain::createThread).forEach(Thread::start);
    }

    public static Thread createThread(final int intName){
        return new Thread(() -> System.out.println(Thread.currentThread().getName()),PREFIX + intName);
    }
}
