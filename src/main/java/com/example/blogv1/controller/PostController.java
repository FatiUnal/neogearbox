package com.example.blogv1.controller;

import com.example.blogv1.dto.OrderPostRequestDto;
import com.example.blogv1.dto.PostRequestDto;
import com.example.blogv1.dto.PostSmallDto;
import com.example.blogv1.entity.post.Post;
import com.example.blogv1.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity<>(postService.create(postRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllList(){
        System.out.println("getAllList");
        return new ResponseEntity<>(postService.findAll(),HttpStatus.OK);
    }

    @Operation(summary = "Get greeting", description = "Returns a greeting message.")
    @GetMapping("/get-by-id")
    public ResponseEntity<Post> getById(@RequestParam int id){
        return new ResponseEntity<>(postService.getById(id),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Post> update(@RequestBody PostRequestDto postRequestDto,@RequestParam int id){
        return new ResponseEntity<>(postService.update(id,postRequestDto),HttpStatus.OK);
    }


    @PutMapping("/order")
    public ResponseEntity<String> orderPost(@RequestBody List<OrderPostRequestDto> orderPostRequestDto){
        return new ResponseEntity<>(postService.orderPost(orderPostRequestDto),HttpStatus.OK);
    }

    @GetMapping("/main")
    public ResponseEntity<List<Post>> getMainOrderList(){
        return new ResponseEntity<>(postService.getMainList(),HttpStatus.OK);
    }

    @GetMapping("/small")
    public ResponseEntity<List<PostSmallDto>> getSmallPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return new ResponseEntity<>(postService.getPaginatedPosts(page, size),HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<PostSmallDto>> getCategoryPosts(@RequestParam int category,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(postService.getCategoryPosts(category,page,size),HttpStatus.OK);
    }

    @GetMapping("/category/name")
    public ResponseEntity<List<PostSmallDto>> getCategoryByNamePosts(@RequestParam String linkName,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(postService.getCategoryByNamePosts(linkName,page,size),HttpStatus.OK);
    }

}
