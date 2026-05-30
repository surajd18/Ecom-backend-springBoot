package com.suraj.SpringEcom.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(UUID id){
        super("Product Not Found with ID "+ id);
    }
}
