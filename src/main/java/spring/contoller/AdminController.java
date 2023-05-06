package spring.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.model.User;
import spring.service.UserDetailsServiceImpl;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/createUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("newUser", user);
        return "createUser";
    }


    @GetMapping(value = "/service")
    public String viewUsers(Model model) {
        model.addAttribute("usersAll", userDetailsService.allUsers());
        return "service";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("newUser") User user) {
        userDetailsService.registrationUser(user);
        return "redirect:/admin/service";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("newUser") User user) {
        userDetailsService.save(user);
        return "redirect:/admin/service";
    }


    @GetMapping("/showFormForUpdate/{id}")
    public String updateForm(@PathVariable(value = "id") long id, Model model) {
        User user = userDetailsService.findUserById(id);
        model.addAttribute("newUser", user);
        return "update";
    }


    @GetMapping("/deleteUser/{id}")
    public String deleteThroughId(@PathVariable(value = "id") long id) {
        userDetailsService.deleteUser(id);
        return "redirect:/admin/service";

    }
}
