package com.javatechie.configuration;

import com.javatechie.tools.ITSupoortTicketTool;
import com.javatechie.tools.RealTimeDataTool;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public List<ToolCallback> toolCallbacks(ITSupoortTicketTool itSupoortTicketTool, RealTimeDataTool realTimeDataTool){
            return List.of(ToolCallbacks.from(itSupoortTicketTool,realTimeDataTool));
    }
}
