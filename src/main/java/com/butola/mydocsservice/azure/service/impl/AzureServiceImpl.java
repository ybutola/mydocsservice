package com.butola.mydocsservice.azure.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.butola.mydocsservice.azure.AzureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AzureServiceImpl implements AzureService {

    @Value("${azure.blobstorage.connectionstring}")
    private String CONNECTION_STRING;

    @Value("${azure.blobstorage.containername}")
    private String CONTAINER_NAME;

    private BlobContainerClient getBlobContainerClient() {
        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString(CONNECTION_STRING)
                .containerName(CONTAINER_NAME)
                .buildClient();
        return containerClient;
    }

    public byte[] getPdfFile(String blobName) {
        try {
            BlobContainerClient containerClient = getBlobContainerClient();

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void upload(String blobName, byte[] fileBytes) {
        BlobContainerClient containerClient = getBlobContainerClient();
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        try {
            blobClient.upload(new ByteArrayInputStream(fileBytes), fileBytes.length, true);
            System.out.println("PDF file uploaded successfully.");
        } catch (Exception ex) {
            System.out.println("Error uploading PDF file: " + ex.getMessage());
        }
    }
}
