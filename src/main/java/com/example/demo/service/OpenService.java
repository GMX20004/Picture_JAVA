package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.OCR.OcrEntry;
import com.example.demo.OCR.OcrService;
import com.example.demo.body.ToolBody;
import com.example.demo.mod.ToolMod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;

@Service
public class OpenService {
    private final ToolMod toolMod;
    private final OcrService ocrService;

    public OpenService(ToolMod toolMod, OcrService ocrService) {
        this.toolMod = toolMod;
        this.ocrService = ocrService;
    }

    public String sceneComparison(MultipartFile multipartFile){
        File tFile = null;
        OcrEntry[] ocrEntries = null;
        try {
            tFile = toolMod.multipartFiletoFile(multipartFile);
            ocrEntries = ocrService.ocr(tFile.toURI().getPath().replaceFirst("/", ""));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            toolMod.deleteTempFile(tFile);
        }
        System.out.println(JSONObject.toJSONString(ocrEntries));
        return Arrays.toString(ocrEntries);
    }
    public Double pictureComparison(ToolBody toolBody){
        toolMod.multipartFiletoFile(toolBody.getFile());
        return 0D;
    }
}
