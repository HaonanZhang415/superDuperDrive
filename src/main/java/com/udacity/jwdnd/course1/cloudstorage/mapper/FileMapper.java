package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileSaved;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES")
    List<FileSaved> getAllFiles();

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata, filepath) VALUES(#{fileName}, #{fileType}, #{fileSize}, #{userId}, #{theFile}, #{filePath})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(FileSaved fileSaved);

    @Select("SELECT * FROM FILES WHERE fileid =#{fileId}")
    FileSaved selectFile(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileid =#{fileId}")
    void deleteFile(Integer fileId);

}
