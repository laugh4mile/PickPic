package com.web.blog.util;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="file")
@Configuration
public class FileUploadsProperty {
//    private String uploadDir = "/home/ubuntu/images";
    private String uploadDir = "c:\\SSAFY\\uploaded";

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}