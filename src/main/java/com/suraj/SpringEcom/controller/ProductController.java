package com.suraj.SpringEcom.controller;

import com.suraj.SpringEcom.model.Product;
import com.suraj.SpringEcom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable UUID id){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productService.getProductByImage(id));
    }
    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(product,imageFile));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id,@RequestPart Product product,@RequestPart MultipartFile imageFile){
        productService.getProductById(id);

        Product updateProduct = productService.updateProduct(id,product,imageFile);

        return ResponseEntity.ok(updateProduct);
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id){
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
 }
