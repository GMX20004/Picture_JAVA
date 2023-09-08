package com.example.demo.body;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ToolBody {
    private MultipartFile file;
    private MultipartFile file2;
}
