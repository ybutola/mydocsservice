package com.butola.mydocsservice.azure.service.impl;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.butola.mydocsservice.azure.AzureService;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayOutputStream;

public class AzureServiceImpl implements AzureService {

    @Value("${azure.blobstorage.connectionstring}")
    private String CONNECTION_STRING;

    @Value("${azure.blobstorage.containername}")
    private String CONTAINER_NAME;

    public byte[] getPdfFile(String blobName) {

        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString(CONNECTION_STRING)
                .containerName(CONTAINER_NAME)
                .buildClient();

        if (containerClient.getBlobClient(blobName).getBlockBlobClient().exists()) {
            BlobClientBuilder blobClientBuilder = new BlobClientBuilder()
                    .connectionString(CONNECTION_STRING)
                    .containerName(CONTAINER_NAME)
                    .blobName(blobName);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                blobClientBuilder.buildClient().download(outputStream);
                byte[] pdfBytes = outputStream.toByteArray();
                return pdfBytes;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
