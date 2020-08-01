package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.domain.Block;
import com.manager.homework.service.BlockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"8. Block"})
@AllArgsConstructor
public class BlockApiController {
    private BlockService blockService;

    @ApiOperation(value = "블록 리스트 조회", notes = "블록 리스트 조회")
    @GetMapping("/blocks")
    public Response<List<Block>> getBlockList() {
        return Response.ok(blockService.getBlockList());
    }
}
