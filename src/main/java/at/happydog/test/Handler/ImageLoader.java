package at.happydog.test.Handler;

import at.happydog.test.enity.UserImages;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ImageLoader {

    public static UserImages createJPEGUserImage(String path) {

        MultipartFile imageFile = createMultiPartFromJpeg(path);
        ImageHandler imageHandler = new ImageHandler();
        UserImages userImages = null;
        try {
            userImages = imageHandler.getAppUserImageFromMultipartfile(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userImages;

    }

    public static MultipartFile createMultiPartFromJpeg(String path) {
        File photofile = new File(path);
        InputStream photoInputStream = null;
        MultipartFile imageFile = null;
        try {
            photoInputStream = new FileInputStream(photofile);
            imageFile = new MockMultipartFile(photofile.getName(), photofile.getName(), "image/jpeg", photoInputStream);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return imageFile;

    }
}
