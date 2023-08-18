package com.liceman.application.user.application;

import com.liceman.application.shared.application.loggeduser.LoggedUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface AvatarService {

    @LoggedUser
    void uploadAvatar (String file) throws IOException;

    @LoggedUser
    String getAvatar(Long id) throws FileNotFoundException;
}
