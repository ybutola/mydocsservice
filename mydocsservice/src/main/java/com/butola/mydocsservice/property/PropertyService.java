package com.butola.mydocsservice.property;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.butola.mydocsservice.azure.AzureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PropertyService {

    String BLOB_NAME = "us.minnesota.edenprairie.yogi.tax";

    @Value("${azure.blobstorage.connectionstring}")
    private String CONNECTION_STRING;

    @Value("${azure.blobstorage.containername}")
    private String CONTAINER_NAME;

    @Autowired
    AzureService azureService;

    public byte[] getPdfFile(String blobName) {
        return azureService.getPdfFile(blobName);
    }
}
