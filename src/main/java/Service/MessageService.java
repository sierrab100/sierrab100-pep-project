package Service;
import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;


    public MessageService(){
        messageDAO = new MessageDAO();
    }
    
    public Message addMessage(Message message){
        List<Message> test = messageDAO.getAllMessagesByUser(message.getTime_posted_epoch());
        if (test == null || message.getMessage_text() == "" || message.getMessage_text().length() >= 225 ){
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessagesWithID (int message_id){
        return messageDAO.getMessagesWithID(message_id);
    }

    public Message deleteMessagesWithID(int message_id){
        Message test = messageDAO.getMessagesWithID(message_id);
        if (test == null){
            return null;
        }
        messageDAO.deleteMessagesWithID(test);
        return test;
    }

    public Message updateMessage(int message_id, String message_text){
        Message test = messageDAO.getMessagesWithID(message_id);
        if (test == null || message_text == "" || message_text.length() > 225){
            return null;
        }
        messageDAO.updateMessage(message_id, message_text);
        return messageDAO.getMessagesWithID(message_id);
    }

    public List<Message> getAllMessagesByUser(Long posted_by){
        return messageDAO.getAllMessagesByUser(posted_by);

    }
}
