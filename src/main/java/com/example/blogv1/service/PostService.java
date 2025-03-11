package com.example.blogv1.service;

import com.example.blogv1.builder.PostBuilder;
import com.example.blogv1.dto.*;
import com.example.blogv1.entity.*;
import com.example.blogv1.entity.post.*;
import com.example.blogv1.exception.BadRequestException;
import com.example.blogv1.exception.ConflictException;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.OrderPostRepository;
import com.example.blogv1.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AdminService adminService;
    private final OrderPostRepository orderPostRepository;
    private final PostBuilder postBuilder;


    public PostService(PostRepository postRepository, AdminService adminService, OrderPostRepository orderPostRepository, PostBuilder postBuilder) {
        this.postRepository = postRepository;
        this.adminService = adminService;
        this.orderPostRepository = orderPostRepository;
        this.postBuilder = postBuilder;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }
    public Post getById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("No Post found: "));
    }

    public Post create(PostRequestDto postRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminService.getByUsername(authentication.getPrincipal().toString());
        PostDetails postDetails;
        if (postRequestDto instanceof BakeRequestDto bakeRequestDto) {
            postDetails = new BakeListing(
                    bakeRequestDto.getCategoryName(),
                    bakeRequestDto.getPortion(),
                    bakeRequestDto.isAnimalProduct(),
                    bakeRequestDto.getShelfLife(),
                    bakeRequestDto.getNetQuantity());
            
        }else
            throw new BadRequestException("Geçersiz veri girişi");

        Post post = new Post(
                postRequestDto.getTitle(),
                postRequestDto.getTitleContent(),
                postRequestDto.getContent(),
                admin,
                postDetails
        );
        return postRepository.save(post);
    }

    public Post update(int id, PostRequestDto postRequestDto) {
        Post post = getById(id);
        PostDetails postDetails = post.getPostDetails();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        if (postRequestDto instanceof BakeRequestDto bakeRequestDto) {
            postDetails = new BakeListing(
                    bakeRequestDto.getCategoryName(),
                    bakeRequestDto.getPortion(),
                    bakeRequestDto.isAnimalProduct(),
                    bakeRequestDto.getShelfLife(),
                    bakeRequestDto.getNetQuantity());

        }else
            throw new BadRequestException("Geçersiz veri girişi");
        post.setPostDetails(postDetails);

        return postRepository.save(post);
    }
    
    public String orderPost(List<OrderPostRequestDto> orderPostRequestDto) {

        for (int i = 0; i < orderPostRequestDto.size(); i++) {
            for (int j = i+1; j < orderPostRequestDto.size(); j++) {
                if (orderPostRequestDto.get(i).getOrder() == orderPostRequestDto.get(j).getOrder() ||
                        orderPostRequestDto.get(i).getPostId() == orderPostRequestDto.get(j).getPostId())
                    throw new ConflictException("Geçersiz sıralama yeniden deneyiniz");
            }
        }
        if (!orderPostRepository.findAll().isEmpty()){
            orderPostRepository.deleteAll();
        }
        for (OrderPostRequestDto postRequestDto : orderPostRequestDto) {
            Post post = getById(postRequestDto.getPostId());
            OrderPost orderPost = new OrderPost(postRequestDto.getOrder(),post);
            orderPostRepository.save(orderPost);
        }
        return "Success";
    }

    public List<Post> getMainList() {
        List<OrderPost> allByOrderByOrderIdAsc = orderPostRepository.findAllByOrderByOrderIdAsc();
        return allByOrderByOrderIdAsc.stream().map(OrderPost::getPost).collect(Collectors.toList());
    }

    public List<PostSmallDto> getPaginatedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable).stream().map(postBuilder::postToPostSmallDto).toList();
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }


    public void delete(Post post) {
        postRepository.delete(post);
    }

    public boolean existsById(int postId) {
        return postRepository.existsById(postId);
    }
}
