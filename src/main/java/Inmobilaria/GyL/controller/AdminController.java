package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.service.IImageService;
import Inmobilaria.GyL.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final IImageService iImageService;

    public AdminController(UserService userService, IImageService iImageService) {
        this.userService = userService;
        this.iImageService = iImageService;
    }

    @GetMapping("/listaUsuarios")
    public String listUsers(ModelMap model) {

        List<User> users = userService.listUsers();

        model.put("users", users);

        return "into.html";
    }

    @GetMapping("/busquedaUsuarios")
    public String searchUsers(@RequestParam String word, ModelMap model) {

        List<User> users = userService.findByName(word);

        model.put("users", users);

        return "into.html";
    }

    @PostMapping("/modificarUsuario")
    public String modifyUser(@RequestParam Long id,@RequestParam String role ,@RequestParam String name,@RequestParam Long dni,@RequestParam String email,@RequestParam boolean status) {
        System.out.println(status + " VALGO ESTO!!");
        userService.adminModifyUser(id,name,dni,role,email,status);
        return "redirect:/admin/listaUsuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.adminDeleteUser(id);
        return "redirect:/admin/listaUsuarios";
    }


    @GetMapping("/crearImg")
    public String adminViewImg(){
        return "adminCreateImg";
    }

    @PostMapping("/creaImg")
    public String adminCreateIcon(MultipartFile icon) throws Exception {
        iImageService.submitImg(icon);

        return "redirect:/admin/crearImg";
    }

}
