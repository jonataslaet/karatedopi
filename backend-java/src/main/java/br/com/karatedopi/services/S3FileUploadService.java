package br.com.karatedopi.services;

import br.com.karatedopi.services.exceptions.FileException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class S3FileUploadService {

    private Logger LOG = LoggerFactory.getLogger(S3FileUploadService.class);

    private final AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(inputStream, fileName, contentType);
        } catch (IOException e) {
            LOG.error("IOException Error: "+ e.getMessage());
            throw new FileException("Erro de entrada ou saída de dados");
        }
    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            LOG.info("Iniciando upload");
            amazonS3.putObject(bucketName, fileName, inputStream, objectMetadata);
            LOG.info("Upload finalizado");
            return amazonS3.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            LOG.error("URISyntaxException Error: "+ e.getMessage());
            throw new FileException("Erro ao converter URL para URI");
        } catch (AmazonS3Exception e) {
            LOG.error("AmazonS3Exception Error: "+ e.getMessage());
            throw new AmazonS3Exception("Erro ao tentar se conectar ao bucket S3");
        } catch (AmazonServiceException e) {
            LOG.error("AmazonServiceException Error: "+ e.getMessage() + " | Status Code: " + e.getErrorCode());
            throw new AmazonServiceException("Erro de conexão ao serviço da amazon");
        } catch (AmazonClientException e) {
            LOG.error("AmazonClientException Error: "+ e.getMessage());
            throw new AmazonClientException("Erro ao tentar se conectar ao bucket S3");
        }
    }
}

