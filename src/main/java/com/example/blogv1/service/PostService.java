package com.example.blogv1.service;

import com.example.blogv1.builder.PostBuilder;
import com.example.blogv1.dto.*;
import com.example.blogv1.entity.*;
import com.example.blogv1.exception.BadRequestException;
import com.example.blogv1.exception.ConflictException;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.OrderPostRepository;
import com.example.blogv1.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Post create(PostRequestDto postRequestDto,String username) {
        Admin admin = adminService.getByUsername(username);
        PostDetails postDetails;
        if (postRequestDto instanceof LandRequestDto) {
            postDetails = createLand((LandRequestDto) postRequestDto);
        } else if (postRequestDto instanceof HouseRequestDto) {
            postDetails = createHouse((HouseRequestDto) postRequestDto);
        }else
            throw new BadRequestException("Geçersiz veri girişi");

        Post post = new Post(
                postRequestDto.getTitle(), postRequestDto.getContent(), admin, postDetails
        );
        return postRepository.save(post);
    }

    // LandDetails nesnesini oluşturuyoruz
    private LandListing createLand(LandRequestDto landRequestDto) {
        System.out.println("getAdreess: "+landRequestDto.getAddress());
        return new LandListing(
                landRequestDto.getBrutMetrekare(),
                landRequestDto.getNetMetrekare(),
                landRequestDto.getImarDurumu(),
                landRequestDto.getAdaParsel(),
                landRequestDto.getTapuDurumu(),
                landRequestDto.getAddress()
        );
    }

    // HouseDetails nesnesini oluşturuyoruz
    private HouseListing createHouse(HouseRequestDto houseRequestDto) {
        System.out.println("getAdreess: "+houseRequestDto.getAddress());
        return new HouseListing(
                houseRequestDto.getAddress(),
                houseRequestDto.getBrutMetrekare(),
                houseRequestDto.getNetMetrekare(),
                houseRequestDto.getOdaSayisi(),
                houseRequestDto.getSiteAdi(),
                houseRequestDto.getBulunduguKat(),
                houseRequestDto.getKatSayisi(),
                houseRequestDto.getIsitma(),
                houseRequestDto.getBanyoSayisi(),
                houseRequestDto.getMutfakTipi(),
                houseRequestDto.isBalkon(),
                houseRequestDto.isAsansor(),
                houseRequestDto.isOtopark(),
                houseRequestDto.isEsyali(),
                houseRequestDto.getKullanimDurumu(),
                houseRequestDto.isSiteIcerisinde(),
                houseRequestDto.getSiteAdi(),
                houseRequestDto.getAidat(),
                houseRequestDto.isKrediyeUygun(),
                houseRequestDto.getTapuDurumu(),
                houseRequestDto.isTakas()
        );
    }

    public Post update(int id, PostRequestDto postRequestDto,String status) {
        Post post = getById(id);
        PostDetails postDetails = post.getPostDetails();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        if (status.equals("ACTIVE")){
            post.setStatus(PostStatus.ACTIVE);
        } else if (status.equals("INACTIVE")) {
            post.setStatus(PostStatus.INACTIVE);
        } else if (status.equals("PAST")) {
            post.setStatus(PostStatus.PAST);
        }else
            throw new BadRequestException("Geçersiz data");

        if (postRequestDto instanceof LandRequestDto) {
            LandListing landListing = (LandListing) postDetails;
            landListing.setAdaParsel(((LandRequestDto) postRequestDto).getAdaParsel());
            landListing.setBrutMetrekare(((LandRequestDto) postRequestDto).getBrutMetrekare());
            landListing.setImarDurumu(((LandRequestDto) postRequestDto).getImarDurumu());
            landListing.setNetMetrekare(((LandRequestDto) postRequestDto).getNetMetrekare());
            landListing.setTapuDurumu(((LandRequestDto) postRequestDto).getTapuDurumu());
            landListing.setAddress(post.getPostDetails().getAddress());
        }else if (postRequestDto instanceof HouseRequestDto) {
            HouseListing houseListing = (HouseListing) postDetails;
            houseListing.setBrutMetrekare(((HouseRequestDto) postRequestDto).getBrutMetrekare());
            houseListing.setNetMetrekare(((HouseRequestDto) postRequestDto).getNetMetrekare());
            houseListing.setAddress( postRequestDto.getAddress());
            houseListing.setOdaSayisi(((HouseRequestDto) postRequestDto).getOdaSayisi());
            houseListing.setBinaYasi(((HouseRequestDto) postRequestDto).getBinaYasi());
            houseListing.setBulunduguKat(((HouseRequestDto) postRequestDto).getBulunduguKat());
            houseListing.setKatSayisi(((HouseRequestDto) postRequestDto).getKatSayisi());
            houseListing.setIsitma(((HouseRequestDto) postRequestDto).getIsitma());
            houseListing.setBanyoSayisi(((HouseRequestDto) postRequestDto).getBanyoSayisi());
            houseListing.setMutfakTipi(((HouseRequestDto) postRequestDto).getMutfakTipi());
            houseListing.setBalkon(((HouseRequestDto) postRequestDto).isBalkon());
            houseListing.setAsansor(((HouseRequestDto) postRequestDto).isAsansor());
            houseListing.setOtopark(((HouseRequestDto) postRequestDto).isOtopark());
            houseListing.setEsyali(((HouseRequestDto) postRequestDto).isEsyali());
            houseListing.setKullanimDurumu(((HouseRequestDto) postRequestDto).getKullanimDurumu());
            houseListing.setSiteIcerisinde(((HouseRequestDto) postRequestDto).isSiteIcerisinde());
            houseListing.setSiteAdi(((HouseRequestDto) postRequestDto).getSiteAdi());
            houseListing.setAidat(((HouseRequestDto) postRequestDto).getAidat());
            houseListing.setKrediyeUygun(((HouseRequestDto) postRequestDto).isKrediyeUygun());
            houseListing.setTapuDurumu(((HouseRequestDto) postRequestDto).getTapuDurumu());
            houseListing.setTakas(((HouseRequestDto) postRequestDto).isTakas());


        }else
            throw new BadRequestException("Geçersiz veri girişi");
        post.setPostDetails(postDetails);

        return postRepository.save(post);
    }

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

    public List<Post> getActivePosts(String status) {
        if (status.equals("ACTIVE") || status.equals("INACTIVE") || status.equals("PAST")){
            return postRepository.findAllByStatus(status);
        }else
            throw new BadRequestException("Geçersiz istek");

    }

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
}
