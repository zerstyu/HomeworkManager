package com.manager.homework.vo;

import lombok.Data;

@Data
public class RecommendResponse {
    private String productName;
    private int productPrice;
    private String productImageUrl;
}