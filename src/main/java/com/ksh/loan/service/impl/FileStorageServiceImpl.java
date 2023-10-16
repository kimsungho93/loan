package com.ksh.loan.service.impl;

import com.ksh.loan.exception.BaseException;
import com.ksh.loan.exception.ResultType;
import com.ksh.loan.repository.ApplicationRepository;
import com.ksh.loan.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    private final ApplicationRepository applicationRepository;

    @Override
    public void save(Long applicationId, MultipartFile file) {
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        try {
            String applicationPath = uploadPath.concat("/" + applicationId);
            Path directoryPath = Path.of(applicationPath);
            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }

            Files.copy(file.getInputStream(), Paths.get(applicationPath).
                    resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public Resource load(Long applicationId, String fileName) {
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Resource resource = null;

        try {
            String applicationPath = uploadPath.concat("/" + applicationId);
            Path file = Paths.get(applicationPath).resolve(fileName);
            resource = new UrlResource(file.toUri());

            if (resource.isReadable() || resource.exists()) {
                return resource;
            } else {
                throw new BaseException(ResultType.SYSTEM_ERROR);
            }
        } catch (Exception e) {
            throw new BaseException(ResultType.NOT_EXIST);
        }

    }

    @Override
    public Stream<Path> loadAll(Long applicationId) {
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        try {
            String applicationPath = uploadPath.concat("/" + applicationId);
            return Files.walk(Paths.get(applicationPath), 1).filter(path -> !path.equals(Paths.get(applicationPath)));
        } catch (IOException e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public void deleteAll(Long applicationId) {
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        String applicationPath = uploadPath.concat("/" + applicationId);
        FileSystemUtils.deleteRecursively(Paths.get(applicationPath).toFile());
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }
}
