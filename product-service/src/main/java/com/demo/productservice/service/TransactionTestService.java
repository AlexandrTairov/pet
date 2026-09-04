package com.demo.productservice.service;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionTestService {

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void outerMethod() { innerMethod();}

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void innerMethod() {}

    @Transactional
    public void outerMethodFixed() {
        var proxy = (TransactionTestService) AopContext.currentProxy();
        proxy.innerMethod();
    }
}
