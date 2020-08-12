package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.manager.homework.domain.Product;
import com.manager.homework.repository.ProductRepositorySupport;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.vo.RecommendResponse;
import com.manager.homework.vo.SubjectDto;
import com.manager.homework.vo.SubjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendService {
    private final SubjectRepository subjectRepository;
    private final ProductRepositorySupport productRepositorySupport;

    public List<RecommendResponse> getRecommendList(Long userId) {
        List<SubjectResponse> subjectResponseList = getSubjectList(userId);
        Map<String, Integer> nameMap = Maps.newHashMap();

        for (SubjectResponse subjectResponse : subjectResponseList) {
            List<SubjectDto> joinSubjectDtoList = subjectResponse.getJoinSubjectDtoList();
            for (SubjectDto subjectDto : joinSubjectDtoList) {
                String[] names = subjectDto.getSubjectName().split(" ");
                for (String name : names) {
                    if (nameMap.containsKey(name)) {
                        int count = nameMap.get(name);
                        nameMap.replace(name, count + 1);
                    } else {
                        nameMap.put(name, 1);
                    }
                }
            }
        }

        // value 내림차순으로 정렬하고, value가 같으면 key 오름차순으로 정렬
        List<Map.Entry<String, Integer>> list = new LinkedList<>(nameMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                int comparision = (o1.getValue() - o2.getValue()) * -1;
                return comparision == 0 ? o1.getKey().compareTo(o2.getKey()) : comparision;
            }
        });

        // 순서유지를 위해 LinkedHashMap을 사용
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Iterator<Map.Entry<String, Integer>> iter = list.iterator(); iter.hasNext(); ) {
            Map.Entry<String, Integer> entry = iter.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        List<RecommendResponse> recommendResponseList = Lists.newArrayList();
        if (sortedMap.size() == 0) {
            List<Product> allProductList = productRepositorySupport.findByCondition(null);
            for (Product product : allProductList) {
                RecommendResponse recommendResponse = new RecommendResponse();
                recommendResponse.setProductName(product.getName());
                recommendResponse.setProductPrice(product.getPrice());
                recommendResponse.setProductImageUrl(product.getImageUrl());
                recommendResponseList.add(recommendResponse);
            }
        } else {
            Map.Entry<String, Integer> entry = sortedMap.entrySet().iterator().next();
            String firstKey = entry.getKey();

            List<Product> productList = productRepositorySupport.findByCondition(firstKey);

            for (Product product : productList) {
                RecommendResponse recommendResponse = new RecommendResponse();
                recommendResponse.setProductName(product.getName());
                recommendResponse.setProductPrice(product.getPrice());
                recommendResponse.setProductImageUrl(product.getImageUrl());
                recommendResponseList.add(recommendResponse);
            }
        }

        Collections.shuffle(recommendResponseList);
        return recommendResponseList.stream().limit(3).collect(Collectors.toList());
    }

    private List<SubjectResponse> getSubjectList(Long userId) {
        return subjectRepository.selectAllSubjectList(userId);
    }
}
