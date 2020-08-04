package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Order;
import com.manager.homework.domain.User;
import com.manager.homework.repository.OrderRepositorySupport;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.vo.RecommendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendService {
    private final UserRepository userRepository;
    private final OrderRepositorySupport orderRepositorySupport;

    public List<RecommendResponse> getRecommendList(Long userId) {
        List<RecommendResponse> recommendResponseList = Lists.newArrayList();
        User user = userRepository.findById(userId).get();
        List<Order> orderList = orderRepositorySupport.findByCondition(user.getGender());
        for (Order order : orderList) {
            RecommendResponse recommendResponse = new RecommendResponse();
            recommendResponse.setProductName(order.getProduct().getName());
            recommendResponse.setProductPrice(order.getProduct().getPrice());
            recommendResponse.setProductImageUrl(order.getProduct().getImageUrl());
            recommendResponseList.add(recommendResponse);
        }

        Collections.shuffle(recommendResponseList);
        return recommendResponseList.stream().limit(3).collect(Collectors.toList());
    }
}
