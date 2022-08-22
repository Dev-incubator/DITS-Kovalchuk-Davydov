package com.example.dits.controllers;

import com.example.dits.dto.UserInfoDTO;
import com.example.dits.entity.Role;
import com.example.dits.entity.User;
import com.example.dits.service.RoleService;
import com.example.dits.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping("/userEditor")
    public String getUsers(ModelMap model) {
        model.addAttribute("title", "User editor");
        return "admin/user-editor";
    }

    @ResponseBody
    @DeleteMapping("/deleteUser")
    public List<UserInfoDTO> removeUser(@RequestParam int userId) {
        userService.removeUser(userId);
        return getUsersList();
    }

    @ResponseBody
    @PostMapping("/addUser")
    public List<UserInfoDTO> addUser(@RequestBody UserInfoDTO userInfo, HttpSession httpSession) {
        User user = modelMapper.map(userInfo, User.class);
        Role role = roleService.getRoleByRoleName(userInfo.getRole());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userService.save(user);
        return getUsersList();
    }

    @PostMapping("/editUser")
    public String editUser(@RequestBody UserInfoDTO userInfo) {
        User user = modelMapper.map(userInfo, User.class);
        int userId = userInfo.getUserId();
        Role role = roleService.getRoleByRoleName(userInfo.getRole());
        user.setRole(role);
        try {
            userService.update(user, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/getUsers";
    }

    @ResponseBody
    @GetMapping("/getUsers")
    public List<UserInfoDTO> getUsersList() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(user -> modelMapper.map(user, UserInfoDTO.class)).collect(Collectors.toList());
    }
}
