package com.manager.homework.service;

import com.manager.homework.domain.Block;
import com.manager.homework.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;

    public static List<Block> blockchain = new ArrayList<Block>();
    public static int prefix = 4;

    public List<Block> getBlockList() {
        return blockRepository.findAll();
    }

    public void addBlock(String data){
        Block block = blockRepository.findFirstByOrderByIdDesc();

        String previousHash = "0";
        if (block != null) {
            previousHash = block.getHash();
        }
        block = new Block(data, previousHash, new Date().getTime());
        block.mineBlock(prefix);
        blockchain.add(block);
        blockRepository.saveAll(blockchain);
    }
}
