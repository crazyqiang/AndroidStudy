package org.ninetripods.mq.study.designMode.Decorator;

/**
 * 定义被装饰者
 * Created by MQ on 2017/4/16.
 */

public class Cake extends Sweet {
    @Override
    public String getDescription() {
        return "一个蛋糕";
    }

    @Override
    public double cost() {
        return 66;
    }
}
