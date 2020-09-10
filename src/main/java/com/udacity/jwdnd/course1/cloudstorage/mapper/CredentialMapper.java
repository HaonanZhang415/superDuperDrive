package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getAllCredentials();

    @Insert("INSERT INTO CREDENTIALS (url, username, password, userid) VALUES(#{url}, #{username}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username =#{username}, password=#{password} WHERE credentialid =#{credentialId}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid =#{credentialId}")
    void deleteCredential(int credentialId);

}
