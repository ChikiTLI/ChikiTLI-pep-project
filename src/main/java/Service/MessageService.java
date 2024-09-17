package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        if(message.getMessage_text().length() < 255 && (message.getMessage_text() != "" && message.getMessage_text() != null)) {
            if(messageDAO.getAccountByID(message.getPosted_by())) {
                messageDAO.addMessage(message);
                return message;
            }
        }
        return null;
    }

    public Message updateMessage(int id, Message message) {
        if(message.getMessage_text().length() < 255 && (message.getMessage_text() != "" && message.getMessage_text() != null)) {
            if(messageDAO.updateMessageById(id, message) != null) {
                System.out.println(message.getMessage_id());
                System.out.println(message.getMessage_text());
                System.out.println(message.getPosted_by());
                System.out.println(message.getTime_posted_epoch());
                // messageDAO.updateMessageById(id, message);
                return message;
            }
        }
        return null;
    }


    public Message deleteMessage(int id) {
        Message message = messageDAO.deleteMessageById(id);
        if(message != null) {
            return message;
        }
        return null;
    }

    public List<Message> allMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        Message message = messageDAO.getMessageById(id);
        if(message != null) {
            return message;
        }
        return null;
    }

    public List<Message> allMessages(int id) {
        return messageDAO.getAllMessages(id);
    }
}
