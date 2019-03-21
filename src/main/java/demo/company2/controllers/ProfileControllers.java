package demo.company2.controllers;


import demo.company2.entities.User;
import demo.company2.services.ImageService;
import demo.company2.services.TaskService;
import demo.company2.services.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

@Controller
public class ProfileControllers {

    private TaskService taskService;
    private UserService userService;
    private ImageService imageService;

    public ProfileControllers(TaskService taskService, UserService userService, ImageService imageService) {
        this.taskService = taskService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findOne(email);
        model.addAttribute("tasks", taskService.findUserTask(user));
        model.addAttribute("user", user);
        return "views/profile";
    }

    @GetMapping("profile/{id}/upload")
    public String showUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "views/imageUploadForm";
    }

    @PostMapping("profile/{id}/upload")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(id, file);
        return "redirect:/profile";
    }

    @GetMapping("profile/image")
    public void renderImageFromDB(Principal principal, HttpServletResponse response) throws IOException {
        String email = principal.getName();
        User user = userService.findOne(email);
        extractImage(response, user);
    }

    private void extractImage(HttpServletResponse response, User user) throws IOException {
        TaskController.renderImage(response, user);
    }
}
