package com.javatechie.tools;

import com.javatechie.entity.SupportTicket;
import com.javatechie.service.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ITSupoortTicketTool {
    private final SupportTicketService service;

    @Tool(description = "Create a support ticket with email and description")
    public SupportTicket createTicket(@ToolParam (description = "Issue description")String description,@ToolParam (description = "user email ") String email){
        return service.createSupportTicket(description,email);
    }

    @Tool(description = "Add comment to support ticket")
    public String addComment(@ToolParam (description = "Ticket ID") Long id,@ToolParam (description = "Comment Text")String comment){
        return service.addComment(id,comment);
    }

    @Tool(description = "Escalate a support ticket to high priority")
    public String esclateTicket(@ToolParam (description = "Ticket ID") Long id){
        return service.escalateTicket(id);
    }

    @Tool(description = "Get complete support ticket details")
    public SupportTicket getTicketDetails(@ToolParam (description = "Ticket ID") Long id){
        return service.getTicketDetails(id);
    }

    @Tool(description = "List all support ticket")
    public List< SupportTicket> listAllTickets(){
        return service.getAllTickets();
    }


}
