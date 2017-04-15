package org.ninetripods.mq.study.designMode.Decorator;

/**
 * 具体装饰者
 * Created by MQ on 2017/4/16.
 */

public class FruitDecorator extends Decorator {
    Sweet sweet;

    public FruitDecorator(Sweet sweet) {
        this.sweet = sweet;
    }

    @Override
    public String getDescription() {
        return sweet.getDescription() + "，水果";
    }

    @Override
    public double cost() {
        return sweet.cost() + 10;
    }
}
