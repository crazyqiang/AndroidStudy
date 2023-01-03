package org.ninetripods.mq.study.generics;


import java.util.ArrayList;
import java.util.List;

/**
 * Java泛型示例
 */
public class GenericsByJava {

    /**
     * <? extends E>来定义上界，可以保证协变性
     */
    public void GExtends() {
        //1、Parent是Parent的子类
        Parent parent = new Child();

        //2、协变，泛型参数是Parent
        CList<Parent> objs = new CList<>();
        List<Child> strs = new ArrayList<>(); //声明字符串List
        strs.add(new Child());
        objs.addAll(strs); //addAll()方法中的入参必须为List<? extends E>，从而保证了List<Child>是List<Parent>的子类。
    }

    /**
     * 逆变性
     */
    public void GSuper(){
        CList<Child> objs = new CList<>();
        List<Parent> parents = new ArrayList<>(); //声明字符串List
        parents.add(new Parent());
        objs.popAll(parents); //逆变
    }
}

class CList<E> {

    //通过<? extends E>来定义上界
    public void addAll(List<? extends E> list) {
        //...
    }

    //通过<? super E>来定义下界
    public void popAll(List<? super E> dest) {
        //...
    }
}

//继承关系Child -> Parent
class Parent{
    protected String name = "Parent";
}

class Child extends Parent {
    protected String name = "Child";
}
