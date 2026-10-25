//package com.javatechie.controller;
//
//import com.javatechie.service.MultiModelChatService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/multi-model/api")
//public class MultiModelChatController {
//
//    @Autowired
//    private  MultiModelChatService multiModelChatService;
//
//    @GetMapping("/chat/openai")
//    public String chatWithOpenAI(@RequestParam("message") String message){
//        return multiModelChatService.chatWithOpenAI(message);
//    }
//
//    @GetMapping("/chat/ollama")
//    public String chatWithOllama(@RequestParam("message") String message){
//        return multiModelChatService.chatWithOllama(message);
//    }
//
//
//
//}
