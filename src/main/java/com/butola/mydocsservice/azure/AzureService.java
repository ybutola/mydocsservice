package com.butola.mydocsservice.azure;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface AzureService {
    byte[] getPdfFile(String blobName);

    void upload(String blobName, byte[] bytes);
}
