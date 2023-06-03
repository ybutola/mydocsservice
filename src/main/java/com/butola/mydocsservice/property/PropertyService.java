package com.butola.mydocsservice.property;

import com.butola.mydocsservice.azure.AzureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PropertyService {
    @Autowired
    AzureService azureService;

    public byte[] getPdfFile(String blobName) {
        return azureService.getPdfFile(blobName);
    }

    public void uploadFile(MultipartFile file) throws IOException {
        azureService.upload(file.getName(), file.getBytes());
    }
}
