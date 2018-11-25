package com.example.controller;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")//for all method check permit
public class UserController {
    @Autowired
    private  UserRepository userRepository;

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "userList";
    }
    //mapping for edit user + ident
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
    //Save Edit user role
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ){
        user.setUsername(username);
        //Get list roles
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();//clear all roles

        for(String key : form.keySet()){
          if(roles.contains(key)) {
              user.getRoles().add(Role.valueOf(key));//work if Role add
          }
        }

        userRepository.save(user);
        return "redirect:/user";
    }
}
