package com.sun.entity;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON) //单例
public class Boy {
    public void playToy() {
        Toy toy = getToy();
        System.out.println("toy="+toy);
    }

    @Lookup
    public Toy getToy() {
        return null;
    }
}

