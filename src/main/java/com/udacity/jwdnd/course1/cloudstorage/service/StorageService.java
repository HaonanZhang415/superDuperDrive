package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileSaved;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service
public class StorageService {

    private FileMapper fileMapper;

    private final Path root = Paths.get("uploadDirectory");

    public StorageService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
        try {
            if (Files.exists(root)) {
                return;
            }
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(MultipartFile file, Integer userId) {
        try {
            if (Files.exists(this.root.resolve(file.getOriginalFilename()))) {
                return;
            }
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            FileSaved fileSaved = new FileSaved();
            fileSaved.setFileName(file.getOriginalFilename());
            fileSaved.setFileSize(Files.size(this.root.resolve(file.getOriginalFilename())));
            fileSaved.setFileType(Files.probeContentType(this.root.resolve(file.getOriginalFilename())));
            //Byte sqlLobValue = new SqlLobValue(file.getInputStream(), (int)file.getResource().contentLength());
            fileSaved.setTheFile(file.getBytes());
            fileSaved.setUserId(userId);
            fileSaved.setFilePath(this.root.resolve(file.getOriginalFilename()).toString());
            this.fileMapper.addFile(fileSaved);
        } catch (Exception e) {
            throw new RuntimeException("Could not store this file. Error: " + e.getMessage());
        }
    }

    public void delete(Integer fileId) {

        Path filePath = Paths.get(this.fileMapper.selectFile(fileId).getFilePath());
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileMapper.deleteFile(fileId);

    }

    public Resource download(Integer fileId) {
        Path filePath = Paths.get(this.fileMapper.selectFile(fileId).getFilePath());
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<FileSaved> getFileName() {
        List<FileSaved> allFiles = this.fileMapper.getAllFiles();
        Collections.reverse(allFiles);
        return allFiles;
    }
}
