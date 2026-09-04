package com.demo.productservice.service;

import com.demo.productservice.entity.Product;
import com.demo.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAllWithNPlusOneProblem() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> findAllWithJoinFetch() {
        return productRepository.findAllWithJoinFetch();
    }
}
