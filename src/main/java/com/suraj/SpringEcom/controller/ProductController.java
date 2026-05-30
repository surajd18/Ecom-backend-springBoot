package com.suraj.SpringEcom.controller;

import com.suraj.SpringEcom.model.Product;
import com.suraj.SpringEcom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/product/${id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id){
        Product product = productService.getProductById(id);
        if(product != null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultiPartFile image){
        Product savedProduct  = productService.addProduct(product,image);

    }
}
