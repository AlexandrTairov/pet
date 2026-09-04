package com.demo.productservice;

import com.demo.productservice.service.TransactionTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TransactionTestServiceTest {

    @Autowired
    private TransactionTestService service;

    @Test
    void testSelfInvocationDoesNotCreateNewTransaction() {
        service.outerMethod();
    }
}
