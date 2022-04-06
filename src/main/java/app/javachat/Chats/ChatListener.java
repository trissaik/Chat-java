//package app.javachat.Chats;
//
//import app.javachat.Controllers.ViewControllers.MainController;
//import app.javachat.Logger.Log;
//import app.javachat.Models.ChaDataContainer;
//import app.javachat.Models.User;
//import app.javachat.Utilities.Info;
//import javafx.scene.layout.VBox;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class ChatListener extends Thread {
//    private final MainController mainController;
//    private VBox lateralChatMenu;
//
//    private ServerSocket localServer;
//    private ChatRequest selfChatRequest;
//
//    public ChatListener(MainController mainController) {
//
//        this.mainController = mainController;
//        this.lateralChatMenu = mainController.getLateralMenu();
//
//        try {
//            localServer = new ServerSocket(Info.NEW_CHAT_LISTENER_PORT);
//        } catch (IOException e) {
//            Log.error(e.getMessage());
//        }
//    }
//
//
//    @Override
//    public void run() {
//        while (true) {
//            Log.show("Listening for new incoming chats at port "+ Info.NEW_CHAT_LISTENER_PORT,"Chat Listener");
//            listenChats();
//        }
//    }
//
//    private void listenChats() {
//
//        try {
//            Socket inputServer = localServer.accept();
//            Log.show("New chat incoming.","Chat Listener");
//            ObjectInputStream inputStream = new ObjectInputStream(inputServer.getInputStream());
//            Object objectRead = inputStream.readObject();
//
//            inputStream.close();
//
//            if (objectRead instanceof ChatRequest othersChatRequest) {
//                User otherUser = othersChatRequest.getOtherUser();
//                selfChatRequest = new ChatRequest(Info.localUser, true);
//                //Si no es una respuesta, simplemente mandales una respuesta
//                int indexOfChatPort, indexOfCallPort;
//                if (!othersChatRequest.isAccept()) {
//                    indexOfChatPort = othersChatRequest.getIndexOfChatPort();
//
//                    // When we receive a new chat request, we need to tell the other user which index is he using for his chats.
//                    selfChatRequest.setIndexOfChatPort(indexOfChatPort);
//
//                    enviarChatRequest(othersChatRequest.getOtherUser().getIP(), othersChatRequest.getChatListenerPort(), selfChatRequest);
//
//                    //creamos nuevo chat pasandole el request del otro, nuestro puerto de chat y nuestro puerto de llamada
//                    createNewChat(othersChatRequest, selfChatRequest.getChatPort(), otherUser);
//
//                } else {
//                    // Si es una respuesta de chat(isAccept es true) creamos el chat pasandole el chat request,
//                    indexOfChatPort = othersChatRequest.getIndexOfChatPort();
//                    createNewChat(othersChatRequest, Info.getPort(indexOfChatPort), otherUser);
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            Log.error(e.getMessage());
//        }
//    }
//
//    private void createNewChat(ChatRequest othersChatRequest, int selfPort, User otherUser) {
//        ChaDataContainer chaDataContainer = new ChaDataContainer();
//        chaDataContainer.setLocalChatPort(selfPort);
//        chaDataContainer.setOtherChatPort(othersChatRequest.getChatPort());
//        chaDataContainer.setCallListenerPort(othersChatRequest.getCallListenerPort());
//        chaDataContainer.setUser(otherUser);
//        if (!Info.checkIfChatExist(chaDataContainer)) {
////
////            Chat chat = new SimpleChat(othersChatRequest, selfPort);
////            ChatItem chatItem = new ChatItem(chat, chatInfo);
////
////            LeftChatItem leftChatItem = new LeftChatItem(chatItem);
////            leftChatItem.setMainController(mainController);
////
////            //Add chat to left side
////            Platform.runLater(() -> lateralChatMenu.getChildren().add(leftChatItem));
////            Info.addChat(chatInfo);
//
//        } else Log.error("El chat ya existe. Prueba uno distinto");
//
//    }
//
//    public static void enviarChatRequest(String ip, int chatListenerPort, ChatRequest chatRequest) {
//        Socket socketEnviador = null;
//        ObjectOutputStream outputStream = null;
//
//        try {
//            socketEnviador = new Socket(ip, chatListenerPort);
//            outputStream = new ObjectOutputStream(socketEnviador.getOutputStream());
//            outputStream.writeObject(chatRequest);
//
//        } catch (IOException e) {
//            Log.error(e.getMessage());
//        } finally {
//            try {
//                if (socketEnviador != null)
//                    socketEnviador.close();
//                if (outputStream != null)
//                    socketEnviador.close();
//            } catch (IOException e) {
//                Log.error(e.getMessage());
//            }
//        }
//    }
//}
