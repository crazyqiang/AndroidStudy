package org.ninetripods.mq.study.designMode.Decorator;

/**
 * 具体装饰者
 * Created by MQ on 2017/4/16.
 */

public class CandleDecorator extends Decorator {
    Sweet sweet;

    public CandleDecorator(Sweet sweet) {
        this.sweet = sweet;
    }

    @Override
    public String getDescription() {
        return sweet.getDescription() + "，蜡烛";
    }

    @Override
    public double cost() {
        return sweet.cost() + 10;
    }
}
