package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountsService;
import Service.MessageService;
import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountsService accountsService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountsService = new AccountsService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagesHandler);
        app.delete("/messages/{message_id}", this::deleteMessagesHandler);
        app.patch("/messages/{message_id}", this::patchMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    

    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountsService.addAccount(account);
        if(addedAccount == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
 
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account login = accountsService.login(account);
        if(login==null){
            ctx.status(401);
        }else{
            ctx.json(mapper.writeValueAsString(login));
        }
 
    }

    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessages = messageService.addMessage(message);
        if(addedMessages==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedMessages));
        }
 
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessagesHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessagesWithID(message_id);
        if (message == null){
            ctx.status(200);
        }else{
            ctx.json(message);
        }
 
    }

    private void deleteMessagesHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessagesWithID(message_id);
        if (message == null){
            ctx.status(200);
        }else{
            ctx.json(message);
        }
    }


    public void patchMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message1 = mapper.readValue(ctx.body(), Message.class);
        String message_text = message1.getMessage_text();
        Message message = messageService.updateMessage(message_id, message_text);
        if (message == null){
            ctx.status(400);
        }else{
            ctx.json(message);
        }
        
        }
    

    private void getAllMessagesByUserHandler(Context ctx) {
        ctx.json(messageService.getAllMessagesByUser(Long.parseLong(ctx.pathParam("account_id"))));
    }


}