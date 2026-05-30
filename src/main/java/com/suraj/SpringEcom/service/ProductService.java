package com.suraj.SpringEcom.service;

import com.suraj.SpringEcom.model.Product;
import com.suraj.SpringEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(UUID id) {
        return productRepo.findById(id).orElse(null);
    }
}
