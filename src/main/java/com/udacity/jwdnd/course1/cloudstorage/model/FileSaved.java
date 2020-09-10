package com.udacity.jwdnd.course1.cloudstorage.model;

public class FileSaved {
    private Integer fileId;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private byte[] theFile;
    private String filePath;
    private Integer userId;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getTheFile() {
        return theFile;
    }

    public void setTheFile(byte[] theFile) {
        this.theFile = theFile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
