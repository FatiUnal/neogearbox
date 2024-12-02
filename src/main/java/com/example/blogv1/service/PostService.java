package com.example.blogv1.service;

import com.example.blogv1.dto.PostRequestDto;
import com.example.blogv1.entity.Admin;
import com.example.blogv1.entity.Post;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AdminService adminService;

    public PostService(PostRepository postRepository, AdminService adminService) {
        this.postRepository = postRepository;
        this.adminService = adminService;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }
    public Post findById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("No Post found: "));
    }

    public Post save(PostRequestDto postRequestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            UserDetails principal = (UserDetails) auth.getPrincipal();
            Admin admin = adminService.getByUsername(principal.getUsername());
            Post post = new Post(postRequestDto.getTitle(),postRequestDto.getContent(),admin);
            return postRepository.save(post);
        }else
            throw new NotFoundException("No Admin found");
    }
}
