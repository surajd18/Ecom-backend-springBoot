package com.suraj.SpringEcom.service;

import com.suraj.SpringEcom.exceptions.ImageNotFoundException;
import com.suraj.SpringEcom.exceptions.ProductNotFoundException;
import com.suraj.SpringEcom.model.Product;
import com.suraj.SpringEcom.repo.ProductRepo;
import org.jspecify.annotations.Nullable;
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

    public Product addProduct(Product product, MultipartFile image) {
        try {
            product.setImageName(image.getOriginalFilename());
            product.setImageType(image.getContentType());
            product.setImageData(image.getBytes());
            return productRepo.save(product);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process image: " + e.getMessage()); // ← wrap it
        }
    }


    public byte[] getProductByImage(UUID id) {
        Product product = getProductById(id);
        if(product.getImageData() == null){
            throw new ImageNotFoundException(id);
        }
        return product.getImageData();
    }

    public Product updateProduct(UUID id, Product product, MultipartFile imageFile) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(product.getName());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setIsProductAvailable(product.getIsProductAvailable());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setReleaseDate(product.getReleaseDate());

        try{
            if(imageFile !=null && !imageFile.isEmpty()){
                existingProduct.setImageName(imageFile.getOriginalFilename());
                existingProduct.setImageType(imageFile.getContentType());
                existingProduct.setImageData(imageFile.getBytes());
            }
        }catch (IOException e){
            throw new RuntimeException("Failed to process image: "+e.getMessage());
        }
        return productRepo.save(existingProduct);
    }


    public void deleteProduct(UUID id) {
        getProductById(id);
        productRepo.deleteById(id);
    }
}
