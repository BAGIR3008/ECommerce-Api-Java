package com.alta.ecommerce_api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 *      "message":"success/failed",
 *      "data": ...
 * }
 * 
 * {
 *          "meta":{
 *              "status":"ok"
 *              "message":""
 *          },
 *          "data":{
 *              ....
 *          }
 *      }
 */

 @Data
 @AllArgsConstructor
 @NoArgsConstructor
 @Builder
public class WebResponse<T> {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaginationResponse pagination;
}
