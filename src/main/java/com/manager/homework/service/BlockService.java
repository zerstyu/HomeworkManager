package com.manager.homework.service;

import com.manager.homework.domain.Block;
import com.manager.homework.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;

    public List<Block> getBlockList() {
        return blockRepository.findAll();
    }
}
