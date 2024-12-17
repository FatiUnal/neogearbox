package com.example.blogv1.service;

import com.example.blogv1.dto.PostImagerOrderRequestDto;
import com.example.blogv1.entity.post.Image;
import com.example.blogv1.entity.post.ImageType;
import com.example.blogv1.entity.post.Post;
import com.example.blogv1.exception.BadRequestException;
import com.example.blogv1.exception.ConflictException;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.ImageRepository;
import com.example.blogv1.repository.PostRepository;
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
import java.util.stream.Collectors;

@Service
public class ImageService {
    @Value("${upload.dir}")
    private String UPLOAD_DIR;
    @Value("${spring.url}")
    private String url;

    @Value("${spring.file.url}")
    private String urlFile;
    private final PostService postService;
    private final ImageRepository imageRepository;

    public ImageService(PostService postService, ImageRepository imageRepository) {
        this.postService = postService;
        this.imageRepository = imageRepository;

    }

    public List<String> uploadImage(MultipartFile[] files, int id) {
        Post post = postService.getById(id);

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

                String urls = url+"upload/images/"+id+"/"+originalFileName;

                Image image = new Image(urls,post, ImageType.IMAGE);
                Path filePath = path.resolve(originalFileName);
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

            String urls = url+"upload/cover/"+id+"/"+originalFileName;

            Path filePath = path.resolve(originalFileName);

            // Aynı isimde bir dosya olup olmadığını kontrol et
            if (Files.exists(filePath)) {
                throw new RuntimeException("A file with the same name already exists: " + originalFileName);
            }

            file.transferTo(filePath.toFile());
            Image image = new Image(urls,ImageType.COVER);
            post.setCoverImage(image);
            postService.savePost(post);

            return urls;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed for post ID: " + id, e);
        }

    }

    public String deleteCoverImage(int postId) {
        Post post = postService.getById(postId);
        if (post.getCoverImage() != null) {
            String coverImage = post.getCoverImage().getFilename();
            String path = coverImage.replace(urlFile,UPLOAD_DIR);
            File file = new File(path);
            if (file.delete()) { // Dosya silinir.
                int id = post.getCoverImage().getId();
                post.setCoverImage(null);
                postService.savePost(post);
                imageRepository.deleteById(id);
                return "File deleted successfully";
            } else {
                throw new RuntimeException("Failed to delete file: " + path);
            }
        }else
            throw new ConflictException("Image not found");
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



    public List<String> deleteImageById(List<Integer> imagesId) {
        try {
            List<String> notDeletedImages = new ArrayList<>();

            List<Image> images = new ArrayList<>();
            for (Integer id : imagesId) {
                Image imageNotFound = imageRepository.findById(id).orElseThrow(() -> new NotFoundException("Image not found"));
                images.add(imageNotFound);
            }
            for (Image image : images) {
                if (image.getType().equals(ImageType.IMAGE)) {
                    String url = image.getFilename();
                    String path = UPLOAD_DIR+url.replace("litysofttest.site/upload/","");

                    String s ="";

                    File file = new File(path);
                    if (file.delete()) {
                        imageRepository.deleteById(image.getId());
                        /**
                        List<Image> getImages = image.getPost().getImages();
                        getImages.remove(image);
                        image.getPost().setImages(getImages);
                        postRepository.save(image.getPost());
**/
                        s= "File deleted successfully";
                    } else {
                        throw new RuntimeException("Failed to delete file: " + path);
                    }


                    image.getPost().getImages().remove(image);
                    postService.savePost(image.getPost());
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
        }catch (RuntimeException e) {
            throw new ConflictException("Failed to delete images");
        }
    }

    public String orderPostImage(int postId, List<PostImagerOrderRequestDto> postImagerOrderRequestDtos) {
        return "";
    }

    public String delete(int postId) {
        Post post = postService.getById(postId);

        if (post.getCoverImage() != null) {
            deleteCoverImage(postId);
        }
        if (post.getImages() != null) {
            deleteImageById(post.getImages().stream().map(Image::getId).collect(Collectors.toList()));
        }
        postService.delete(post);


        if (postService.existsById(postId))
            throw new BadRequestException("Post Not Deleted");
        else
            return "Post Deleted Successfully";
    }
}

