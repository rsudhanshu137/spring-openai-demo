//package com.javatechie.service;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MultiModelChatService {
//
//    private final ChatClient openAIChatClient;
//    private final ChatClient ollamaChatClient;
//
//    public MultiModelChatService( ChatClient openAIChatClient,ChatClient ollamaChatClient) {
//        this.ollamaChatClient = ollamaChatClient;
//        this.openAIChatClient = openAIChatClient;
//    }
//
//    public String chatWithOpenAI(String message){
//        return openAIChatClient
//                .prompt(message)
//                .call()
//                .content();
//    }
//
//    public String chatWithOllama(String message){
//        return openAIChatClient
//                .prompt(message)
//                .call()
//                .content();
//    }
//}
