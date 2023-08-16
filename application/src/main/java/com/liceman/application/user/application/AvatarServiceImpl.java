package com.liceman.application.user.application;

import com.liceman.application.shared.application.loggeduser.LoggedUser;
import com.liceman.application.shared.application.loggeduser.UserContext;
import com.liceman.application.shared.exceptions.NotValidImageFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.Objects;

@Service
public class AvatarServiceImpl implements AvatarService{
    @Value("${avatar.baseRoute}")
    private String BaseRoute;

    @LoggedUser
    @Override
    public void uploadAvatar(MultipartFile file) throws IOException {
        try{
            if (!validateNotEmptyAndContentType(file)){
               throw new NotValidImageFormatException();
            }
            deleteOldAvatars();
            createNewAvatar(file);
        }
        catch (NotValidImageFormatException e){
            throw new NotValidImageFormatException();
        }
        catch (Exception e){
            throw new IOException();
        }
    }

    boolean validateNotEmptyAndContentType (MultipartFile file) {
        return (!file.isEmpty()
                && Objects.requireNonNull(file.getContentType()).contains("image/"))
                && (file.getContentType().contains("jpg")
                || file.getContentType().contains("jpeg")
                || file.getContentType().contains("png")
                || file.getContentType().contains("webp"));
    }
    void deleteOldAvatars () {
        String basePath = "C:/Avatares/" + UserContext.getUser().getId();
        String avatarPrefix = "avatar-";

        File directory = new File(basePath);

        // Verify if the directory exists
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            // Search for files with the prefix given in the directory
            for (File archivo : files) {
                if (archivo.isFile() && archivo.getName().startsWith(avatarPrefix)) {
                    // Delete the files if the files matches the prefix
                    if (archivo.delete()) {
                        System.out.println("Archivo borrado: " + archivo.getName());
                    } else {
                        System.out.println("No se pudo borrar el archivo: " + archivo.getName());
                    }
                }
            }
        }
    }

    void createNewAvatar(MultipartFile file) throws IOException {
        OutputStream outputStream = null;
        try {
            new File(BaseRoute + UserContext.getUser().getId()).mkdirs();
            String extension = Objects.requireNonNull(file.getContentType()).substring(file.getContentType().indexOf("/") + 1);
            File file1 = new File(BaseRoute + UserContext.getUser().getId() + "/" +
                    "avatar-" + UserContext.getUser().getId() + "." + extension);
            outputStream = new BufferedOutputStream(new FileOutputStream(file1));
            outputStream.write(file.getBytes());
        } catch (Exception e){
            throw new IOException();
        }finally {
            outputStream.close();
        }
    }

    @Override
    public String getAvatar(Long id) throws FileNotFoundException {
        InputStream inputStream = null;
        try{
            File directory = new File(BaseRoute + "/" + id);
            File[] files = directory.listFiles();
            for (File archivo : files) {
                String filename= archivo.getName();
            }
            if(files.length > 0){
                File file = files[0];
                inputStream = new BufferedInputStream(new FileInputStream(file));
                byte[] dataByte = inputStream.readAllBytes();
                inputStream.close();
                return Base64.getEncoder().encodeToString(dataByte);
            }
            throw new FileNotFoundException();
        }
        catch (Exception e) {
            throw new FileNotFoundException();
        }finally {
            try {
                inputStream.close();
            }catch (Exception ignored){}
        }
    }
}
