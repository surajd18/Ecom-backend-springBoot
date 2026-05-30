package com.suraj.SpringEcom.exceptions;

import java.util.UUID;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(UUID id){
        super("Image Not found For this Product");
    }
}
