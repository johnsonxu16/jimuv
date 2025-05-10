package com.jimuv.api.controller;

import com.jimuv.common.domain.video.VideoCreateResult;
import com.jimuv.api.service.VideoService;
import com.jimuv.common.domain.http.HttpResult;
import com.jimuv.common.domain.video.VideoCreate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/video")
@Api(tags = "视频")
@Slf4j
public class VideoController {

    @Resource
    private VideoService videoService;

    @PostMapping("/create")
    @ApiOperation("创建视频")
    public HttpResult<VideoCreateResult> create(@RequestBody VideoCreate videoCreate) {
        return HttpResult.success(videoService.create(videoCreate));
    }

}
