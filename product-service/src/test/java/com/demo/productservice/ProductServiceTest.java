package com.demo.productservice;

import com.demo.productservice.entity.Category;
import com.demo.productservice.entity.Product;
import com.demo.productservice.repository.ProductRepository;
import com.demo.productservice.service.ProductService;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest(classes = {ProductServiceApplication.class, DatasourceProxyBeanPostProcessor.class})
@Transactional
@ActiveProfiles("test")
@TestPropertySource(properties = "debug=true")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void initData() {
        productRepository.deleteAllInBatch();
        entityManager.createQuery("DELETE FROM Category").executeUpdate(); // очищаем категории

        for (int i = 1; i <= 3; i++) {
            Category category = new Category();
            category.setName("Category " + i); // каждая своя категория
            entityManager.persist(category);

            Product product = new Product();
            product.setName("Product " + i);
            product.setPrice(BigDecimal.valueOf(100 + i * 10));
            product.setCategory(category);
            entityManager.persist(product);
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testNPlusOneProblem() {
        SQLStatementCountValidator.reset();
        var products = productService.findAllWithNPlusOneProblem();
        products.forEach(p -> p.getCategory().getName());
        SQLStatementCountValidator.assertSelectCount(1 + products.size());
    }

    @Test
    void testJoinFetchSolution() {
        SQLStatementCountValidator.reset();
        var products = productService.findAllWithJoinFetch();
        products.forEach(p -> p.getCategory().getName());
        SQLStatementCountValidator.assertSelectCount(1);
    }
}
