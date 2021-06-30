package com.xyz.jdbnk.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

@Service
public class AmazonClient
{
    private AmazonS3 s3client;
    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        final AWSCredentials credentials = (AWSCredentials)new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = (AmazonS3)new AmazonS3Client(credentials);
    }

    public String uploadFile(final MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            final File file = this.convertMultiPartToFile(multipartFile);
            final String fileName = this.generateFileName(multipartFile);
            fileUrl = this.endpointUrl + "/" + this.bucketName + "/" + fileName;
            this.uploadFileTos3bucket(fileName, file);
            file.delete();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private File convertMultiPartToFile(final MultipartFile file) throws IOException {
        final File convFile = new File(file.getOriginalFilename());
        final FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(final MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(final String fileName, final File file) {
        this.s3client.putObject(new PutObjectRequest(this.bucketName, fileName, file));
    }

    public String deleteFileFromS3Bucket(final String fileUrl) {
        final String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        this.s3client.deleteObject(new DeleteObjectRequest(this.bucketName, fileName));
        return "Successfully deleted";
    }

    public String getSigned(final String id) {
        final Calendar cal = Calendar.getInstance();
        cal.add(10, 12);
        final URL s = this.s3client.generatePresignedUrl(this.bucketName, id, cal.getTime());
        return s.toString();
    }
}