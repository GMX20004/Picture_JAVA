package com.example.demo.OCR;

import com.example.demo.common.ModelUrlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class OcrService {
    private final OcrProperties prop;
    private OcrCPP ocrCPP = null;

    public OcrService(OcrProperties prop) {
        this.prop = prop;
    }

    /**
     * 初始化加载ocr模型
     */
    @PostConstruct
    private void init() {
        System.out.println("加载Ocr执行文件...");
        loadOcrExe();
    }
    /**
     * 加载模型
     */
    public void loadOcrExe() {
        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("use_angle_cls", true);
        try {
            ocrCPP = new OcrCPP(new File(ModelUrlUtils.getRealUrl(prop.getOcrExe()).replaceFirst("file:/", "")), arguments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * ocr
     */
    public OcrEntry[] ocr(String imgFile) {
        try {
            OcrResponse resp = ocrCPP.runOcrOnPath(imgFile);
            if (resp.code == OcrCode.OK) {
                return resp.data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
