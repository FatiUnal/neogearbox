package com.example.blogv1.controller;

import com.example.blogv1.dto.OrderPostRequestDto;
import com.example.blogv1.dto.PostRequestDto;
import com.example.blogv1.dto.PostSmallDto;
import com.example.blogv1.entity.Post;
import com.example.blogv1.service.PostService;
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
        return new ResponseEntity<>(postService.create(postRequestDto,"fatihgs133@gmail.com"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllList(){
        return new ResponseEntity<>(postService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<Post> getById(@RequestParam int id){
        return new ResponseEntity<>(postService.getById(id),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Post> update(@RequestBody PostRequestDto postRequestDto,@RequestParam int id,@RequestParam String status){
        return new ResponseEntity<>(postService.update(id,postRequestDto,status),HttpStatus.OK);
    }

    @PutMapping("/status")
    public ResponseEntity<Post> changeStatus(@RequestParam int id, @RequestParam String status){
        return new ResponseEntity<>(postService.changeStatus(id,status),HttpStatus.OK);
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
    public ResponseEntity<List<PostSmallDto>> getOrderPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(postService.getPaginatedPosts(page, size),HttpStatus.OK);
    }

}
