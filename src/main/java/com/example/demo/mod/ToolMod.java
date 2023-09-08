package com.example.demo.mod;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class ToolMod {
    /**
     * MultipartFile转File
     * @param file 文件
     */
    public File multipartFiletoFile(MultipartFile file){
        try {
            File toFile = null;
            if((!file.equals("")) && (file.getSize() > 0)) {
                String filePath = "/tmp/img";
                if(!new File(filePath).exists()) {
                    new File(filePath).mkdirs();
                }
                InputStream inputStream = file.getInputStream();
                String fileFullName = file.getOriginalFilename();
                String fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
                String prefix = fileFullName.substring(fileFullName.lastIndexOf("."));
                toFile = new File(filePath + fileName + "_" + System.currentTimeMillis() + prefix);
                System.out.println(toFile.getPath());
                intputStreamToFile(inputStream, toFile);
                inputStream.close();
            }
            return toFile;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取文件流
     * @param inputStream
     * @param file
     */
    private void intputStreamToFile(InputStream inputStream, File file) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除临时文件
     * @param file
     */
    @SuppressWarnings("NewApi")
    public void deleteTempFile(File file) {
        if(Optional.ofNullable(file).isPresent()) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
    public Double ImageComparison(String a,String b){
        Mat img1 = Imgcodecs.imread(a);
        Mat img2 = Imgcodecs.imread(b);
        // 将图片转换为灰度图像
        Imgproc.cvtColor(img1, img1, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(img2, img2, Imgproc.COLOR_BGR2GRAY);
        // 调整图像大小以便于比较
        Imgproc.resize(img1, img1, new Size(512, 512));
        Imgproc.resize(img2, img2, new Size(512, 512));
        // 计算直方图
        Mat hist1 = new Mat();
        Mat hist2 = new Mat();
        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        MatOfInt histSize = new MatOfInt(50);
        Imgproc.calcHist(List.of(img1), new MatOfInt(0), new Mat(), hist1, histSize, ranges);
        Imgproc.calcHist(List.of(img2), new MatOfInt(0), new Mat(), hist2, histSize, ranges);
        // 比较直方图，计算相似度
        return Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL);
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ToolMod toolMod = new ToolMod();
        String imagePath1 = "D:\\cs\\cs\\1.jpg";
        String imagePath2 = "D:\\cs\\cs\\3.jpg";
        System.out.println(toolMod.ImageComparison(imagePath1,imagePath2));
    }

}
