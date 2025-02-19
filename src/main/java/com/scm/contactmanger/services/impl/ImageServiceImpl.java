package com.scm.contactmanger.services.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.contactmanger.helper.constants;
import com.scm.contactmanger.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    private Cloudinary cloudinary;

    //  Constructor injection
    // private Cloudinary cloudinary;

    // public ImageServiceImpl(Cloudinary cloudinary) {
    //     this.cloudinary = cloudinary;
    // }

    @Override
    public String uploadImage(MultipartFile contactimage,String filename) {
        // code which uploads image to cloud
        // returns url

        try {
            byte[]data=new byte[contactimage.getInputStream().available()];
            contactimage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id",filename
            ));
            return getUrlFromPublicId(filename);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
            .width(constants.Contact_image_width)
            .height(constants.Contact_image_height)
            .crop(constants.Contact_image_crop)
        )
        .generate(publicId);
    }

}
