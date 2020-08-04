package com.manager.homework.service;

import com.manager.homework.repository.OrderRepository;
import com.manager.homework.vo.RecommendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendService {
    private final OrderRepository orderRepository;

    public List<RecommendResponse> getRecommendList() {
        orderRepository.findAll();
        return null;
    }
}
