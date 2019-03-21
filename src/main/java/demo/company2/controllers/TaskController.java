package demo.company2.controllers;

import demo.company2.entities.Task;
import demo.company2.entities.User;
import demo.company2.services.TaskService;
import demo.company2.services.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

@Controller
public class TaskController {

    private TaskService taskService;
    private UserService userService;
    private String id;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @GetMapping("/addTask/{id}")
    public String taskForm(Model model, @PathVariable String id) {
        setId(id);
        User user =userService.findOne(getId());
        System.out.println(user.getEmail());
        model.addAttribute("task", new Task());
        return "views/taskForm";
    }

    @PostMapping("/addTask")
    public String addTask(@Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "views/taskForm";
        }
        System.out.println(getId());
        User user =userService.findOne(getId());
        System.out.println(user);
        taskService.addTask(task, userService.findOne(getId()));
        return "redirect:/users";
    }

    @GetMapping("viewTasks/{id}")
    public String viewTasks(Model model, @PathVariable String id) {
        User user = userService.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.findUserTask(user));
        return "views/viewTasks";
    }


    @GetMapping("profile/userImage/{id}")
    public void showUserImage( HttpServletResponse response ,@PathVariable String id) throws IOException {
        User user = userService.findOne(id);
        extractImage(response, user);
    }

    private void extractImage(HttpServletResponse response, User user) throws IOException {
        renderImage(response, user);
    }

    static void renderImage(HttpServletResponse response, User user) throws IOException {
        if (user.getImage() != null) {
            byte[] byteArray = new byte[user.getImage().length];
            int i = 0;
            for (Byte wrappedByte : user.getImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
