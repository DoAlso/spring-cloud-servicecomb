package com.sample.servicecomb.common.enums;

/**
 * @ClassName BasicOperation
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/11 11:29
 */
public enum BasicOperation implements Operation {
   PLUS("+"){
        @Override
        public double apply(double x, double y) {
            return x+y;
        }
    }, MINUS("-"){
        @Override
        public double apply(double x, double y) {
            return x-y;
        }
    }, TIMES("*"){
        @Override
        public double apply(double x, double y) {
            return x*y;
        }
    }, DIVIDE("/"){
        @Override
        public double apply(double x, double y) {
            return x/y;
        }
    };

    // 属性
    private String symbol;
    BasicOperation(String symbol){
        this.symbol = symbol;
    }
}
