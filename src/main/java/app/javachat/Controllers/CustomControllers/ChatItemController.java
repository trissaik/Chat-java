package app.javachat.Controllers.CustomControllers;

import app.javachat.Logger.Log;
import app.javachat.Models.Message;
import app.javachat.SimpleRoom;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.MessageSenderService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.HashMap;

import static app.javachat.Utilities.MessageJSONBuilder.parseMessageToJSON;


public class ChatItemController {
    private int callListenerPort;
    @FXML
    private TextField chatInput;
    @FXML
    private VBox chatBox;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Label headerUsername;
    @FXML
    private Button btnLlamar, btnSendMessage;
    private SimpleRoom room;
    private Stage callWindow;
    private boolean notCalled;

    public ChatItemController() {
    }

    public ChatItemController(SimpleRoom room) {
        this.room = room;

    }

    @FXML
    void initialize() {
        headerUsername.setText(room.getUsername());
//        btnLlamar.setOnMouseClicked(mouseEvent -> {
//            if (!Info.Call.isInCall())
//                new Thread(this::sendCallRequest).start();
//        });
        btnSendMessage.setOnMouseClicked(mouseEvent -> {
            onSendMessageButton();
        });
        chatInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                onSendMessageButton();
        });
        chatBox.heightProperty().addListener((observableValue, number, t1) -> {
            scroll.setVvalue((Double) t1);
        });
    }
//
//    private void sendCallRequest() {
//        try {
//
//            Socket socket = new Socket(otherUser.getIP(), callListenerPort);
//            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//            outputStream.writeObject(new CallRequest(true, false, false));
//            outputStream.close();
//        } catch (IOException e) {
//            Log.error(e.getMessage());
//        }
//
//    }

    private void onSendMessageButton() {
        String message = chatInput.getText();
        if(message.equals("")) return;
        new Thread(() -> {
            sendMessageToServer(message,room.getUsername());
        }).start();
        chatInput.setText("");
    }

    private void sendMessageToServer(String message, String username) {
        Message msg = new Message(message, username, LocalDateTime.now().toString());
        Platform.runLater(() -> chatBox.getChildren().add(new MessageItem(msg, true)));
        String jsonMessage=parseMessageToJSON(message,username,Info.username.getValue());
        MessageSenderService.sendMessage(jsonMessage);

    }




    public void addMessage(HashMap<String, Object> msg) {
        room.addMessage(msg);
        Log.show("Message received. "+msg,"ChatItemController");
        Message message= new Message((String) msg.get("content"), (String) msg.get("sender"),"ahora");
        Platform.runLater(() -> chatBox.getChildren().add(new MessageItem(message, false)));
    }

    public String getUsername() {
        return room.getUsername();
    }
}
