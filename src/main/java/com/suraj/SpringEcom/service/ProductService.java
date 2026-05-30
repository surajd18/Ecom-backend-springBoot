package com.suraj.SpringEcom.service;

import com.suraj.SpringEcom.exceptions.ImageNotFoundException;
import com.suraj.SpringEcom.exceptions.ProductNotFoundException;
import com.suraj.SpringEcom.model.Product;
import com.suraj.SpringEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return productRepo.findById(id).orElseThrow(()->new ProductNotFoundException(id));
    }

    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return productRepo.save(product);
    }

    public byte[] getProductByImage(UUID id) {
        Product product = getProductById(id);
        if(product.getImageData() == null){
            throw new ImageNotFoundException(id);
        }
        return product.getImageData();
    }
}
