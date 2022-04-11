package app.javachat;

import app.javachat.Models.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleRoom implements Serializable, Room {
    private String username;
    private List<HashMap<String, Object>> messages;

    public SimpleRoom(String username) {
        this.username = username;
        this.messages = new ArrayList<>();

    }

    @Override
    public void addMessage(HashMap<String, Object> msg) {
        messages.add(msg);
    }

    @Override
    public String getId() {
        return username;
    }
    @Override
    public List<HashMap<String, Object>> getMessages() {
        return messages;
    }
}
