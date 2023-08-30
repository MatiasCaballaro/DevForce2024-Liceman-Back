package com.liceman.application.user.application;

import com.liceman.application.shared.application.loggeduser.LoggedUser;
import com.liceman.application.shared.application.loggeduser.UserContext;
import com.liceman.application.shared.exceptions.NotValidImageFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.Base64;
import java.util.Objects;

@Service
public class AvatarServiceImpl implements AvatarService{
    @Value("${avatar.baseRoute}")
    private String BaseRoute;

    @LoggedUser
    @Override
    public void uploadAvatar(String image) throws IOException {
        try{
            if (!validateNotEmptyAndContentType(image) || !isImage(image)){
               throw new NotValidImageFormatException();
            }
            deleteOldAvatars();
            createNewAvatar(image);
        }
        catch (NotValidImageFormatException e){
            throw new NotValidImageFormatException();
        }
        catch (Exception e){
            throw new IOException();
        }
    }

    boolean validateNotEmptyAndContentType (String image) {
        return (!image.isEmpty()
                && Objects.requireNonNull(image).contains("image/"))
                && (image.contains("jpg")
                || image.contains("jpeg")
                || image.contains("png")
                || image.contains("webp"));
    }
    private String getContent(String image){
        String data;
        data = image.substring(23);
        return data;
    }

    public boolean isImage(String imageData){
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(
                    Base64.getDecoder().decode(getContent(imageData).getBytes()));
            ImageIO.read(inputStream);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    void deleteOldAvatars () {
        String basePath = "C:/Avatars/" + UserContext.getUser().getId();
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
                        System.out.println("File deleted: " + archivo.getName());
                    } else {
                        System.out.println("File couldn't be deleted: " + archivo.getName());
                    }
                }
            }
        }
    }

    void createNewAvatar(String image) throws IOException {
        OutputStream outputStream = null;
        try {
            new File(BaseRoute + UserContext.getUser().getId()).mkdirs();
            File file1 = new File(BaseRoute + UserContext.getUser().getId() + "/" +
                    "avatar-" + UserContext.getUser().getId() + ".jpeg");
            outputStream = new BufferedOutputStream(new FileOutputStream(file1));
            outputStream.write(Base64.getDecoder().decode(getContent(image)));
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
