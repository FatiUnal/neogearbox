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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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

    public Post create(PostRequestDto postRequestDto,String username) {
        Admin admin = adminService.getByUsername(username);
        PostDetails postDetails = null;
        if (postRequestDto instanceof LandRequestDto) {
            
        } else if (postRequestDto instanceof HouseRequestDto) {
            postDetails = createHouse((HouseRequestDto) postRequestDto);
        }else
            throw new BadRequestException("Geçersiz veri girişi");

        Post post = new Post(
                postRequestDto.getTitle(),
                postRequestDto.getContent(),
                admin,
                postDetails
        );
        return postRepository.save(post);
    }

    /**
    // LandDetails nesnesini oluşturuyoruz
    private LandListing createLand(LandRequestDto landRequestDto) {
        return new LandListing(
                landRequestDto.getBrutMetrekare(),
                landRequestDto.getNetMetrekare(),
                landRequestDto.getImarDurumu(),
                landRequestDto.getAdaParsel(),
                landRequestDto.getTapuDurumu()
        );
    }**/

    // HouseDetails nesnesini oluşturuyoruz
    private HouseListing createHouse(HouseRequestDto houseRequestDto) {
        return new HouseListing(
                houseRequestDto.getPrice(),
                houseRequestDto.getSehir(),
                houseRequestDto.getIlce(),
                houseRequestDto.getBrutMetrekare(),
                houseRequestDto.getIsitma(),
                houseRequestDto.getOdaSayisi(),
                houseRequestDto.isMutfakTipi(),
                houseRequestDto.isOtopark(),
                houseRequestDto.isHavuz(),
                houseRequestDto.isOyunPark(),
                houseRequestDto.isGüvenlik(),
                houseRequestDto.isSporSalon(),
                houseRequestDto.getContext1(),
                houseRequestDto.getContext2(),
                houseRequestDto.getContext3()
        );
    }

    public Post update(int id, PostRequestDto postRequestDto) {
        Post post = getById(id);
        PostDetails postDetails = post.getPostDetails();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
/**
        if (status.equals("ACTIVE")){
            post.setStatus(PostStatus.ACTIVE);
        } else if (status.equals("INACTIVE")) {
            post.setStatus(PostStatus.INACTIVE);
        } else if (status.equals("PAST")) {
            post.setStatus(PostStatus.PAST);
        }else
            throw new BadRequestException("Geçersiz data");
**/

        if (postRequestDto instanceof LandRequestDto) {
            LandListing landListing = (LandListing) postDetails;
            landListing.setAdaParsel(((LandRequestDto) postRequestDto).getAdaParsel());
            landListing.setBrutMetrekare(((LandRequestDto) postRequestDto).getBrutMetrekare());
            landListing.setImarDurumu(((LandRequestDto) postRequestDto).getImarDurumu());
            landListing.setNetMetrekare(((LandRequestDto) postRequestDto).getNetMetrekare());
            landListing.setTapuDurumu(((LandRequestDto) postRequestDto).getTapuDurumu());
        }else if (postRequestDto instanceof HouseRequestDto) {
            HouseListing houseListing = (HouseListing) postDetails;
            houseListing.setBrutMetrekare(((HouseRequestDto) postRequestDto).getBrutMetrekare());
            houseListing.setIsitma(((HouseRequestDto) postRequestDto).getIsitma());
            houseListing.setOdaSayisi(((HouseRequestDto) postRequestDto).getOdaSayisi());
            houseListing.setMutfakTipi(((HouseRequestDto) postRequestDto).isMutfakTipi());
            houseListing.setOtopark(((HouseRequestDto) postRequestDto).isOtopark());
            houseListing.setHavuz(((HouseRequestDto) postRequestDto).isHavuz());
            houseListing.setOyunPark(((HouseRequestDto) postRequestDto).isOyunPark());
            houseListing.setGüvenlik(((HouseRequestDto) postRequestDto).isGüvenlik());
            houseListing.setSporSalon(((HouseRequestDto) postRequestDto).isSporSalon());
            houseListing.setContext1(((HouseRequestDto) postRequestDto).getContext1());
            houseListing.setContext2(((HouseRequestDto) postRequestDto).getContext2());
            houseListing.setContext3(((HouseRequestDto) postRequestDto).getContext3());
        }else
            throw new BadRequestException("Geçersiz veri girişi");
        post.setPostDetails(postDetails);

        return postRepository.save(post);
    }

    /**
    public Post changeStatus(int id, String status) {
        Post post = getById(id);
        if (status.equals("active")){
            post.setStatus(PostStatus.ACTIVE);
        } else if (status.equals("inactive")) {
            post.setStatus(PostStatus.INACTIVE);
        } else if (status.equals("past")) {
            post.setStatus(PostStatus.PAST);
        }else
            throw new BadRequestException("Geçersiz data");
        System.out.println(post.getStatus());
        return postRepository.save(post);
    }
**/
    
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

    /**
    public List<Post> getActivePosts(String status) {
        if (status.equals("ACTIVE") || status.equals("INACTIVE") || status.equals("PAST")){
            return postRepository.findAllByStatus(status);
        }else
            throw new BadRequestException("Geçersiz istek");

    }
     **/

    public List<Post> getEstatePosts(String estate) {
        if (estate.equals("LAND")){
            return postRepository.findAllByPostDetails_Estate(EstateType.LAND);
        } else if (estate.equals("HOUSE")) {
            return postRepository.findAllByPostDetails_Estate(EstateType.HOUSE);
        } else
            throw new BadRequestException("Geçersiz istek");
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Post getByIlanNo(String ilan) {
        return postRepository.findByPostDetails_IlanNo(ilan).orElseThrow(()-> new NotFoundException("Not found"));
    }


    public void delete(Post post) {
        postRepository.delete(post);
    }

    public boolean existsById(int postId) {
        return postRepository.existsById(postId);
    }
}
