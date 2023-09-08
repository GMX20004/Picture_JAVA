package com.example.demo.controller;

import com.example.demo.body.ToolBody;
import com.example.demo.mod.ToolMod;
import com.example.demo.service.OpenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Open")
public class OpenController {
    private final OpenService openService;

    public OpenController(OpenService openService) {
        this.openService = openService;
    }

    /**
     * 场景识别识别
     */
    @PostMapping("SceneComparison")
    public String sceneComparison(ToolBody toolBody){
        return openService.sceneComparison(toolBody.getFile());
    }
    /**
     * 图片比较
     */
    @PostMapping("PictureComparison")
    public Double pictureComparison(ToolBody toolBody){
        return openService.pictureComparison(toolBody);
    }
}
