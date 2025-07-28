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
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AdminService adminService;
    private final OrderPostRepository orderPostRepository;
    private final PostBuilder postBuilder;
    private final CategoryService categoryService;


    public PostService(PostRepository postRepository, AdminService adminService, OrderPostRepository orderPostRepository, PostBuilder postBuilder, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.adminService = adminService;
        this.orderPostRepository = orderPostRepository;
        this.postBuilder = postBuilder;
        this.categoryService = categoryService;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }
    public Post getById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("No Post found: "));
    }

    public Post create(PostRequestDto postRequestDto) {
        Category category = categoryService.findById(postRequestDto.getCategoryName());
        PostDetails postDetails;
        if (postRequestDto instanceof KwRequestDto) {
            postDetails = new KwListing(
                    category);
            
        }else
            throw new BadRequestException("Geçersiz veri girişi");

        Post post = new Post(
                postRequestDto.getTitle(),
                postRequestDto.getTitleEng(),
                postRequestDto.getTitleContent(),
                postRequestDto.getTitleContentEng(),
                postRequestDto.getContent(),
                postRequestDto.getContentEng(),
                postDetails
        );
        return postRepository.save(post);
    }

    public Post update(int id, PostRequestDto postRequestDto) {
        Post post = getById(id);
        PostDetails postDetails = post.getPostDetails();
        post.setTitle(postRequestDto.getTitle());
        post.setTitleEng(postRequestDto.getTitleEng());
        post.setContent(postRequestDto.getContent());
        post.setContentEng(postRequestDto.getContentEng());
        post.setTitleContent(postRequestDto.getTitleContent());
        post.setTitleContentEng(postRequestDto.getTitleContentEng());

        if (postRequestDto instanceof KwRequestDto kwRequestDto) {
            postDetails.setCategory(categoryService.findById(kwRequestDto.getCategoryName()));

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
        post.getPostDetails().setCategory(null);
        postRepository.save(post);
        postRepository.delete(post);
    }

    public boolean existsById(int postId) {
        return postRepository.existsById(postId);
    }

    public List<PostSmallDto> getCategoryPosts(int categoryId,int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "title"));
        if (!categoryService.existById(categoryId))
            throw new NotFoundException("Category Not Found");
        return postRepository.findByCategoryId(categoryId,pageable).stream().map(postBuilder::postToPostSmallDto).toList();
    }

    public List<PostSmallDto> getCategoryByNamePosts(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "title"));
        if (!categoryService.existByLinkName(name))
            throw new NotFoundException("Category Not Found");
        return postRepository.findByCategoryLinkName(name,pageable).stream().map(postBuilder::postToPostSmallDto).toList();
    }

    public  List<PostSmallDto> searchProductsByName(String search){
        if (search == null || search.isEmpty()){
            return new ArrayList<>();
        }
        return postRepository.findByTitleContainingIgnoreCase(search).stream().map(postBuilder::postToPostSmallDto).toList();
    }
}
