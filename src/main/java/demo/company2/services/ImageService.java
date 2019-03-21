package demo.company2.services;

import demo.company2.entities.User;
import demo.company2.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Service
@Transactional
public class ImageService {

    private UserRepository userRepository;

    public ImageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveImageFile(String email, MultipartFile file) {

        try {
            User user = userRepository.findByEmail(email);
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            user.setImage(byteObjects);
            userRepository.save(user);

        } catch (IOException e) {
            //todo handle better
            e.printStackTrace();
        }
    }
}
