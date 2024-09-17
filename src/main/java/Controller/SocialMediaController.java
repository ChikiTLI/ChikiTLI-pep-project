package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.*;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
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
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessageHandler);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessageByUserId);
        return app;
    }

    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else {
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);
        if(loginAccount != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(loginAccount));
        }else {
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createMessage = messageService.addMessage(message);
        if(createMessage != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(createMessage));
        }else {
            ctx.status(400);
        }
    }

    private void getAllMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> allMessages = messageService.allMessages();
        if(allMessages != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(allMessages));
        }else {
            ctx.status(400);
        }
    }

    private void getMessageById (Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message getMessage = messageService.getMessageById(id);
        if(getMessage != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(getMessage));
        }else {
            ctx.status(200);
        }
    }

    private void getAllMessageByUserId (Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("account_id")));
        List<Message> allMessages = messageService.allMessages(id);
        if(allMessages != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(allMessages));
        }else {
            ctx.status(200);
        }
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message deleteMessage = messageService.deleteMessage(id);
        if(deleteMessage != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(deleteMessage));
        }else {
            ctx.status(200);
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createMessage = messageService.updateMessage(id, message);
        if(createMessage != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(createMessage));
        }else {
            ctx.status(400);
        }
    }
}