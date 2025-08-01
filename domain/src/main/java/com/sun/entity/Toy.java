package com.sun.entity;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName= ConfigurableBeanFactory.SCOPE_PROTOTYPE) //原型 也就是非单例
public class Toy {

    public Toy() {
        System.out.println("Buy a new toy...");
    }
}

