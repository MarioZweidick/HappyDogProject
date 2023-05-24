package at.happydog.test.Handler;

import at.happydog.test.enity.AppUser;
import at.happydog.test.enity.Training;
import at.happydog.test.enity.UserImages;
import at.happydog.test.imageUtil.ImageUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageHandler {

    //returns AppUserImage from multipartfile input
    public UserImages getAppUserImageFromMultipartfile(MultipartFile file) throws IOException {
        UserImages appUserImages = new UserImages();
        appUserImages.setName(file.getOriginalFilename());
        appUserImages.setType(file.getContentType());
        appUserImages.setImageData(ImageUtil.compressImage(file.getBytes()));
        return appUserImages;
    }

    //return decompressed image from AppUser
    public byte[] downloadImageFromAppUser(AppUser appUser){
        byte[] bytes = new byte[0];
        //if(appUser.getUserImages()!=null){
            UserImages imageData = appUser.getUserImages();
            return ImageUtil.decompressImage(imageData.getImageData());
        //}
        //return bytes;
    }
    public byte[] downloadImageFromTraining(Training training){
        byte[] bytes = new byte[0];
        //if(appUser.getUserImages()!=null){
        UserImages imageData = training.getTrainingsImage();
        return ImageUtil.decompressImage(imageData.getImageData());
        //}
        //return bytes;
    }
}
