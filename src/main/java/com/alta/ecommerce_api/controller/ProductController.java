package com.alta.ecommerce_api.controller;

import com.alta.ecommerce_api.model.*;
import com.alta.ecommerce_api.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;



    @PostMapping(
            path = "/products",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> create(@RequestBody ProductRequest request){
        ProductResponse productResponse = productService.create(request);
        return WebResponse.<ProductResponse>builder()
                .message("success input data")
                .data(productResponse)
                .build();
    }

    /**
     * Get all products
     * @param name
     * @param description
     * @param page : start from 0
     * @param limit
     * @return
     */
    @GetMapping(
            path = "/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ProductResponse>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ){
        ProductSearchRequest request = ProductSearchRequest.builder()
                .page(page)
                .limit(limit)
                .name(name)
                .description(description)
                .build();

        log.info("page: "+page.toString());
        log.info("name: "+name);
        Page<ProductResponse> productResponses = productService.search(request);
        return WebResponse.<List<ProductResponse>>builder()
                .data(productResponses.getContent())
                .message("success")
                .pagination(PaginationResponse.builder()
                        .currentPage(productResponses.getNumber())
                        .totalPage(productResponses.getTotalPages())
                        .limit(productResponses.getSize())
                        .build())
                .build();
    }

    @GetMapping(
            path = "/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> getById(@PathVariable("productId") String productId){
        ProductResponse productResponse = productService.getById(productId);
        return WebResponse.<ProductResponse>builder()
                .message("success get product")
                .data(productResponse)
                .build();
    }

    @PutMapping(
            path = "/products/{productId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> update(
            @RequestBody ProductUpdateRequest request,
            @PathVariable("productId") String productId
    ){
        request.setId(productId);
        ProductResponse productResponse = productService.update(request);
        return WebResponse.<ProductResponse>builder()
                .message("success update data")
                .data(productResponse)
                .build();
    }

    @DeleteMapping(
            path = "/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("productId") String productId){
        productService.delete(productId);
        return WebResponse.<String>builder()
                .message("success delete product")
                .build();
    }

}