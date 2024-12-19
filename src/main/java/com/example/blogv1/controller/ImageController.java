package com.example.blogv1.controller;

import com.example.blogv1.dto.PostImagerOrderRequestDto;
import com.example.blogv1.service.ImageService;
import jakarta.servlet.ServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @PostMapping
    public ResponseEntity<List<String>> uploadImage(@RequestParam("images") MultipartFile[] files,@RequestParam("id") int id) {
        return new ResponseEntity<>(imageService.uploadImage(files,id), HttpStatus.CREATED);
    }
    @PostMapping("/cover")
    public ResponseEntity<String> uploadCover(@RequestParam("image") MultipartFile file,@RequestParam("id") int id) {
        return new ResponseEntity<>(imageService.uploadCoverImage(file,id), HttpStatus.CREATED);
    }

    @PutMapping("/order-image")
    public ResponseEntity<String> orderPostImage(@RequestParam int postId, List<PostImagerOrderRequestDto> postImagerOrderRequestDtos){
        return new ResponseEntity<>(imageService.orderPostImage(postId,postImagerOrderRequestDtos),HttpStatus.OK);
    }


    @GetMapping("/get-by-id")
    public ResponseEntity<Resource> getImageById(@RequestParam int imageId) {
        return imageService.getImageById(imageId);
    }

    @DeleteMapping("/cover-delete")
    public ResponseEntity<String> deleteCoverImage(@RequestParam int postId) {
        return new ResponseEntity<>(imageService.deleteCoverImage(postId), HttpStatus.OK);
    }

    @DeleteMapping("/delete-by-id")
    public ResponseEntity<List<String>> deleteImageById(@RequestParam Integer postId,@RequestParam List<Integer> imagesId) {
        return new ResponseEntity<>(imageService.deleteImageById(postId,imagesId),HttpStatus.OK);
    }

    @DeleteMapping("/post")
    public ResponseEntity<String> delete(@RequestParam int postId){
        return new ResponseEntity<>(imageService.delete(postId),HttpStatus.OK);
    }





}
