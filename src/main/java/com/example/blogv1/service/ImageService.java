package com.example.blogv1.service;

import com.example.blogv1.dto.PostImagerOrderRequestDto;
import com.example.blogv1.entity.post.Category;
import com.example.blogv1.entity.post.Image;
import com.example.blogv1.entity.post.ImageType;
import com.example.blogv1.entity.post.Post;
import com.example.blogv1.exception.BadRequestException;
import com.example.blogv1.exception.ConflictException;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.ImageRepository;
import jakarta.transaction.Transactional;
import net.coobird.thumbnailator.Thumbnails;
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
    private String uploadDir;
    @Value("${spring.url}")
    private String url;

    @Value("${spring.file.url}")
    private String urlFile;
    /*
    spring.web.resources.static-locations=file:/var/www/upload/bakebond
    spring.url=http://localhost:8081/
    spring.file.url=http://localhost:8081/api/v1/upload/
     */

    private final PostService postService;
    private final ImageRepository imageRepository;
    private final CategoryService categoryService;

    public ImageService(PostService postService, ImageRepository imageRepository, CategoryService categoryService) {
        this.postService = postService;
        this.imageRepository = imageRepository;

        this.categoryService = categoryService;
    }

    public List<String> uploadImage(MultipartFile[] files, int id) {
        System.out.println("uploadImage");
        Post post = postService.getById(id);

        List<String> uploadFilesName = new ArrayList<>();
        try {
            Path path = Paths.get(uploadDir+"images/"+id+"/");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            for (MultipartFile file : files) {

                String originalFileName = file.getOriginalFilename();
                if (originalFileName == null || originalFileName.isEmpty()) {
                    throw new RuntimeException("File name is invalid");
                }

                String fileExtension = "";
                int lastIndexOfDot = originalFileName.lastIndexOf(".");
                if (lastIndexOfDot != -1) {
                    fileExtension = originalFileName.substring(lastIndexOfDot); // Örneğin ".jpg"
                }

                String newFileName = UUID.randomUUID().toString() + fileExtension;

                Path filePath = path.resolve(newFileName);

                // Aynı isimde bir dosya var mı kontrol et
                if (Files.exists(filePath)) {
                    throw new RuntimeException("A file with the name '" + originalFileName + "' already exists.");
                }

                String urls = url+"api/v1/upload/neogearbox/images/"+id+"/"+newFileName;


                File outputFile = filePath.toFile();

                // İçerik türünü kontrol et
                String contentType = file.getContentType();
                if (contentType != null && contentType.startsWith("image/")) {
                    // Resimse: boyutlandır ve kaydet
                    Thumbnails.of(file.getInputStream())
                            .size(800, 800)
                            .outputQuality(0.9)
                            .outputFormat(fileExtension)
                            .keepAspectRatio(true)
                            .toFile(outputFile);
                } else {
                    // Resim değilse: direkt kaydet
                    file.transferTo(outputFile);
                }

                Image image = new Image(urls,post, ImageType.IMAGE);

                post.getImages().add(image);
                uploadFilesName.add(urls);
            }
            postService.savePost(post);

        } catch (IOException e) {
            throw new ConflictException("image not added");
        }
        return uploadFilesName;
    }

    public String uploadCoverImage(MultipartFile file, int id) {

        Post post = postService.getById(id);
        System.out.println("uploadCoverImage");

        if (post.getCoverImage() != null) {
            deleteCoverImage(id);
        }

        try {
            Path path = Paths.get(uploadDir+"cover/"+id+"/");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new RuntimeException("File name is invalid");
            }

            String fileExtension = "";
            int lastIndexOfDot = originalFileName.lastIndexOf(".");
            if (lastIndexOfDot != -1) {
                fileExtension = originalFileName.substring(lastIndexOfDot); // Örneğin ".jpg"
            }

            // Benzersiz dosya adı oluştur
            String newFileName = UUID.randomUUID().toString() + fileExtension;


            String urls = url+"api/v1/upload/neogearbox/cover/"+id+"/"+newFileName;

            Path filePath = path.resolve(newFileName);

            File outputFile = filePath.toFile();

            // İçerik türünü kontrol et
            String contentType = file.getContentType();
            if (contentType != null && contentType.startsWith("image/")) {
                // Resimse: boyutlandır ve kaydet
                Thumbnails.of(file.getInputStream())
                        .size(800, 800)
                        .outputQuality(0.9)
                        .outputFormat(fileExtension)
                        .keepAspectRatio(true)
                        .toFile(outputFile);
            } else {
                // Resim değilse: direkt kaydet
                file.transferTo(outputFile);
            }


            Image image = new Image(urls,ImageType.COVER);
            post.setCoverImage(image);
            postService.savePost(post);

            return urls;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed for post ID: " + id, e);
        }
    }

    public String uploadPdf(MultipartFile file, int id) {

        Post post = postService.getById(id);
        System.out.println("uploadPdf");

        if (post.getPdf() != null) {
            deletePdf(id);
        }

        try {
            Path path = Paths.get(uploadDir+"pdf/"+id+"/");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new RuntimeException("File name is invalid");
            }

            String fileExtension = "";
            int lastIndexOfDot = originalFileName.lastIndexOf(".");
            if (lastIndexOfDot != -1) {
                fileExtension = originalFileName.substring(lastIndexOfDot); // Örneğin ".jpg"
            }

            // Benzersiz dosya adı oluştur
            String newFileName = UUID.randomUUID().toString() + fileExtension;


            String urls = url+"api/v1/upload/neogearbox/pdf/"+id+"/"+newFileName;

            Path filePath = path.resolve(newFileName);

            file.transferTo(filePath.toFile());
            Image image = new Image(urls,ImageType.PDF);
            post.setPdf(image);
            postService.savePost(post);

            return urls;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed for post ID: " + id, e);
        }
    }

    public String uploadCategory(MultipartFile file, int id) {
        Category category = categoryService.findById(id);
        System.out.println("uploadCategory");

        if (category.getCoverImage() != null) {
            deleteCategory(id);
        }

        try {
            Path path = Paths.get(uploadDir+"category/"+id+"/");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new RuntimeException("File name is invalid");
            }

            String fileExtension = "";
            int lastIndexOfDot = originalFileName.lastIndexOf(".");
            if (lastIndexOfDot != -1) {
                fileExtension = originalFileName.substring(lastIndexOfDot); // Örneğin ".jpg"
            }


            // Benzersiz dosya adı oluştur
            String newFileName = UUID.randomUUID().toString() + fileExtension;


            String urls = url+"api/v1/upload/neogearbox/category/"+id+"/"+newFileName;

            Path filePath = path.resolve(newFileName);
            file.transferTo(filePath.toFile());
            Image image = new Image(urls,ImageType.CATEGORY);
            category.setCoverImage(image);
            categoryService.saveCategory(category);
            return urls;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public String deleteCategory(int id) {
        Category category = categoryService.findById(id);
        if (category.getCoverImage() != null) {
            String coverImage = category.getCoverImage().getFilename();
            String path = coverImage.replace(urlFile, uploadDir);

            System.out.println("path:          "+path);
            File file = new File(path);
            if (file.delete()) { // Dosya silinir.
                int ids = category.getCoverImage().getId();
                category.setCoverImage(null);
                categoryService.saveCategory(category);
                imageRepository.deleteById(ids);
                return "File deleted successfully";
            } else {
                throw new RuntimeException("Failed to delete file: " + path);
            }
        }else
            throw new ConflictException("Image not found");
    }


    @Transactional
    public String deleteCoverImage(int postId) {
        Post post = postService.getById(postId);
        if (post.getCoverImage() != null) {
            String coverImage = post.getCoverImage().getFilename();
            String path = coverImage.replace(urlFile,uploadDir);
            //url file :http://localhost:8081/api/v1/upload/  upload dir:/var/www/upload/bake/
            System.out.println("path:          "+path);
            //http://localhost:8081/api/v1/upload/bake/images/1/91eaf823-795a-4510-83e9-1954d022035d.jpeg
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

    @Transactional
    public String deletePdf(int postId) {
        Post post = postService.getById(postId);
        if (post.getPdf() != null) {
            String pdf = post.getPdf().getFilename();
            String path = pdf.replace(urlFile,uploadDir);
            //url file :http://localhost:8081/api/v1/upload/  upload dir:/var/www/upload/bake/
            System.out.println("path:          "+path);
            //http://localhost:8081/api/v1/upload/bake/images/1/91eaf823-795a-4510-83e9-1954d022035d.jpeg
            File file = new File(path);
            if (file.delete()) { // Dosya silinir.
                int id = post.getPdf().getId();
                post.setPdf(null);
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
            Path path = Paths.get(uploadDir+"images/"+image.getPost().getId()+"/"+image.getFilename());
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



    @Transactional
    public List<String> deleteImageById(int postId,List<Integer> imagesId) {
        try {
            List<String> notDeletedImages = new ArrayList<>();


            Post post = postService.getById(postId);
            List<Image> postImages = post.getImages();

            for (Integer imageId : imagesId) {
                Image image = imageRepository.findById(imageId).orElseThrow(()-> new NotFoundException("Image not found"));
                if (postImages.contains(image)) {
                    if (image.getType().equals(ImageType.IMAGE)) {
                        String urld = image.getFilename();
                        System.out.println("urld: "+urld);
                        String path = uploadDir + urld.replace(urlFile,"");
                        System.out.println("path: "+path);

                        String s ="";
                        File file = new File(path);
                        if (file.delete()) {
                            postImages.remove(image);
                            postService.savePost(post);
                            imageRepository.deleteById(imageId);

                            s= "File deleted successfully";
                        } else {
                            throw new RuntimeException("Failed to delete file: " + path);
                        }
                        notDeletedImages.add(s);

                    }else
                        throw new BadRequestException("Image type not supported");

                }

            }

            if (notDeletedImages.isEmpty()) {
                return List.of("Successfull");
            }else
                return notDeletedImages;

        }catch (NumberFormatException e) {
            throw new BadRequestException("Geçersiz data");
        }
    }

    public String orderPostImage(int postId, List<PostImagerOrderRequestDto> postImagerOrderRequestDtos) {
        return "";
    }

    @Transactional
    public String delete(int postId) {
        Post post = postService.getById(postId);

        if (post.getCoverImage() != null) {
            deleteCoverImage(postId);
        }
        if (post.getImages() != null) {
            deleteImageById(postId,post.getImages().stream().map(Image::getId).collect(Collectors.toList()));
        }
        postService.delete(post);


        if (postService.existsById(postId))
            throw new BadRequestException("Post Not Deleted");
        else
            return "Post Deleted Successfully";
    }


}

