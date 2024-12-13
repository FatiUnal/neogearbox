package com.example.blogv1.service;

import com.example.blogv1.entity.post.Image;
import com.example.blogv1.entity.post.ImageType;
import com.example.blogv1.entity.post.Post;
import com.example.blogv1.exception.BadRequestException;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ImageService {
    @Value("${upload.dir}")
    private String UPLOAD_DIR;
    @Value("${spring.url}")
    private String url;
    private final PostService postService;
    private final ImageRepository imageRepository;

    public ImageService(PostService postService, ImageRepository imageRepository) {
        this.postService = postService;
        this.imageRepository = imageRepository;
    }

    public List<String> uploadImage(MultipartFile[] files, int id) {
        Post post = postService.getById(id);
        System.out.println("1");
        List<String> uploadFilesName = new ArrayList<>();
        try {
            Path path = Paths.get(UPLOAD_DIR+"images/"+id+"/");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            for (MultipartFile file : files) {

                String originalFileName = file.getOriginalFilename();
                if (originalFileName == null || originalFileName.isEmpty()) {
                    throw new RuntimeException("File name is invalid");
                }

                String fileExtension = "";
                int lastDotIndex = originalFileName.lastIndexOf(".");
                if (lastDotIndex > 0) {
                    fileExtension = originalFileName.substring(lastDotIndex); // Örneğin: .png
                } else {
                    throw new RuntimeException("File extension is missing");
                }
                String fileName = UUID.randomUUID().toString() + fileExtension;
                String urls = url+"images/"+id+"/"+fileName;

                Image image = new Image(urls,post, ImageType.IMAGE);
                Path filePath = path.resolve(fileName);
                file.transferTo(filePath.toFile());

                post.getImages().add(image);
                uploadFilesName.add(urls);
            }
            postService.savePost(post);

        } catch (IOException e) {
            throw new RuntimeException("File upload failed for post ID: " + id, e);
        }
        return uploadFilesName;
    }

    public String uploadCoverImage(MultipartFile file, int id) {

        Post post = postService.getById(id);

        if (post.getCoverImage() != null) {
            deleteCoverImage(id);
        }

        try {
            Path path = Paths.get(UPLOAD_DIR+"cover/"+id+"/");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new RuntimeException("File name is invalid");
            }

            String fileExtension = "";
            int lastDotIndex = originalFileName.lastIndexOf(".");
            if (lastDotIndex > 0) {
                fileExtension = originalFileName.substring(lastDotIndex); // Örneğin: .png
            } else {
                throw new RuntimeException("File extension is missing");
            }

            String fileName = UUID.randomUUID().toString() + fileExtension;

            String urls = url+"cover/"+id+"/"+fileName;

            Path filePath = path.resolve(fileName);

            file.transferTo(filePath.toFile());
            Image image = new Image(urls,ImageType.COVER);
            post.setCoverImage(image);
            postService.savePost(post);

            return urls;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed for post ID: " + id, e);
        }

    }

    public ResponseEntity<Resource> getCoverImage(int postId) {
        Post post = postService.getById(postId);
        try {
            Path path = Paths.get(UPLOAD_DIR+"cover/"+postId+"/"+post.getCoverImage().getFilename());
            Resource resource = new UrlResource(path.toUri());
            String contentType = Files.probeContentType(path);
            if (!resource.exists()) {
                throw new NotFoundException("Image not found");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Resource> getImages(int postId,String fileName) {
        try {
            Path path = Paths.get(UPLOAD_DIR+"images/"+postId+"/"+fileName);
            Resource resource = new UrlResource(path.toUri());
            String contentType = Files.probeContentType(path);
            if (!resource.exists()) {
                throw new NotFoundException("Image not found");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public String deleteCoverImage(int postId) {
        Post post = postService.getById(postId);
        String coverImage = post.getCoverImage().getFilename();
        String path = UPLOAD_DIR + "cover/" + post.getId() + "/"+coverImage;
        return deleteImage(path,postId);
    }

    public ResponseEntity<Resource> getImageById(int imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(()-> new NotFoundException("Image not found"));

        try {
            Path path = Paths.get(UPLOAD_DIR+"images/"+image.getPost().getId()+"/"+image.getFilename());
            Resource resource = new UrlResource(path.toUri());
            String contentType = Files.probeContentType(path);
            if (!resource.exists()) {
                throw new NotFoundException("Image not found");
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public List<String> deleteImageById(List<String> imagesId) {
        try {
            List<String> notDeletedImages = new ArrayList<>();
            List<Integer> ids = imagesId.stream()
                    .map(Integer::parseInt)
                    .toList();

            List<Image> images = new ArrayList<>();
            for (Integer id : ids) {
                Image imageNotFound = imageRepository.findById(id).orElseThrow(() -> new NotFoundException("Image not found"));
                images.add(imageNotFound);
            }
            for (Image image : images) {
                if (image.getType().equals(ImageType.IMAGE)) {
                    String s = deleteImage(UPLOAD_DIR + "images/" + image.getPost().getId() + "/" + image.getFilename(), image.getId());
                    if (!s.equals("File deleted successfully"))
                        notDeletedImages.add(s);
                }else
                    throw new BadRequestException("Image type not supported");

            }
            if (notDeletedImages.isEmpty()) {
                return List.of("Successfull");
            }else
                return notDeletedImages;

        }catch (NumberFormatException e) {
            throw new BadRequestException("Geçersiz data");
        }
    }

    public String deleteImage(String filePath,int id) {
        File file = new File(filePath);
        if (file.delete()) { // Dosya silinir.
            imageRepository.deleteById(id);
            return "File deleted successfully";
        } else {
            throw new RuntimeException("Failed to delete file: " + filePath);
        }
    }
}

