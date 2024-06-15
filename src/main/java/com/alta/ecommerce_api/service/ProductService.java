package com.alta.ecommerce_api.service;

import com.alta.ecommerce_api.entity.Product;
import com.alta.ecommerce_api.entity.User;
import com.alta.ecommerce_api.model.ProductRequest;
import com.alta.ecommerce_api.model.ProductResponse;
import com.alta.ecommerce_api.model.ProductSearchRequest;
import com.alta.ecommerce_api.model.ProductUpdateRequest;
import com.alta.ecommerce_api.repository.ProductRepository;
import com.alta.ecommerce_api.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

//    @PersistenceContext
//    private EntityManager entityManager;

    @Transactional
    public ProductResponse create(ProductRequest request){

        validationService.validate(request);

//        User user = entityManager.find(User.class, request.getUserId());
//
//        if (user == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found");
//        }

        // check user nya ada atau tidak
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found"));

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setUser(user);

        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .userId(request.getUserId())
                .build();
    }

    private ProductResponse toProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .userId(product.getUser().getId())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> search(ProductSearchRequest request){
        Specification<Product> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(request.getName())){
                predicates.add(
                        builder.like(root.get("name"), "%"+request.getName()+"%")
                );
            }

            if (Objects.nonNull(request.getDescription())){
                predicates.add(
                        builder.like(root.get("description"), "%"+request.getDescription()+"%")
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());
        Page<Product> products= productRepository.findAll(specification, pageable);
        List<ProductResponse> productResponses = products.getContent().stream()
                .map(this::toProductResponse)
                .toList();

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());

    }

    @Transactional(readOnly = true)
    public ProductResponse getById(String id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        return toProductResponse(product);
    }

    @Transactional
    public ProductResponse update(ProductUpdateRequest request){
        // pastikan product nya ada
        Product product = productRepository.findById(request.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        productRepository.save(product);

        return toProductResponse(product);
    }

    @Transactional
    public void delete(String productId){
        // pastikan product nya ada
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        productRepository.delete(product);
    }
}
