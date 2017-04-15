package org.ninetripods.mq.study.designMode.Decorator;

/**
 * 定义组件，也是装饰者和被装饰者的超类
 * Created by MQ on 2017/4/16.
 */

public abstract class Sweet {
    String description = "Sweet";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
