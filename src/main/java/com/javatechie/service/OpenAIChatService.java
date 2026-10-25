//package com.javatechie.service;
//
//import com.javatechie.tools.ITSupoortTicketTool;
//import com.javatechie.tools.RealTimeDataTool;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OpenAIChatService {
//
//    private final ChatClient chatClient;
//
//    private final RealTimeDataTool realTimeDataTool;
//
//    private final ITSupoortTicketTool itSupoortTicketTool;
//
//    public OpenAIChatService(ChatClient chatClient, RealTimeDataTool realTimeDataTool, ITSupoortTicketTool itSupoortTicketTool) {
//        this.chatClient = chatClient;
//        this.realTimeDataTool = realTimeDataTool;
//        this.itSupoortTicketTool = itSupoortTicketTool;
//    }
//
//    public String chatWithOpenAILLM(String message){
//        return chatClient.prompt(message)
//                .call()
//                .content();
//    }
//
//    public String askToAI(String message){
//        return chatClient
//                .prompt(message)
//                .system("You are an IT Support assistant.Help users create,update and manage support tickets.Extract details like email,issue and ticket id from user input")
//                .tools(realTimeDataTool,itSupoortTicketTool)
//                .call()
//                .content();
//    }
//}
