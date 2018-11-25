package com.example.controller;



import com.example.domain.Message;
import com.example.domain.User;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")//get variable, find property and add
    private String uploadPath;

    @GetMapping("/")
    public String greeting( String name, Map<String, Object> model) {

        return "greeting";
    }
    @GetMapping("/main")
    public String main(Map<String, Object> model){
        Iterable<Message> messages=messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Message message = new Message(text,tag, user);

        //add file if they only have name
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            //if exist -> create
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            //unique name file
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }

        messageRepository.save(message);
        Iterable<Message> messages=messageRepository.findAll();
        model.put("messages", messages);

    return "main";
    }

    @ PostMapping("filter")// Find method
    public String filter(@RequestParam String filter,
                         Map<String, Object> model){
        Iterable<Message> messages;
        if(filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        }else{
            messages = messageRepository.findAll();
        }
        model.put("messages", messages);
        return "main";
    }
}