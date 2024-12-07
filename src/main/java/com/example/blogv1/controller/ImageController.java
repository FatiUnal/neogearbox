package com.example.blogv1.controller;

import com.example.blogv1.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<List<String>> uploadImage(@RequestParam("images") MultipartFile[] files,@RequestParam int id) {
        return new ResponseEntity<>(imageService.uploadImage(files,id), HttpStatus.CREATED);
    }
    @PostMapping("/cover")
    public ResponseEntity<String> uploadCover(@RequestParam("image") MultipartFile file,@RequestParam int id) {
        return new ResponseEntity<>(imageService.uploadCoverImage(file,id), HttpStatus.CREATED);
    }

    @GetMapping("/cover/{postId}")
    public ResponseEntity<Resource> getCoverImage(@PathVariable int postId) {
        return imageService.getCoverImage(postId);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Resource> getImage(@PathVariable int postId,@RequestParam String filename ) {
        return imageService.getImages(postId,filename);
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
    public ResponseEntity<List<String>> deleteImageById(@RequestParam List<String> imagesId) {
        return new ResponseEntity<>(imageService.deleteImageById(imagesId),HttpStatus.OK);
    }





}
