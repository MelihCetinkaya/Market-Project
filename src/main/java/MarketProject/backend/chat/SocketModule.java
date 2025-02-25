package MarketProject.backend.chat;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SocketModule {

      private final SocketIOServer socketIOServer;
      private final ChatService chatService;

        public SocketModule(SocketIOServer socketIOServer, ChatService chatService) {
        this.socketIOServer = socketIOServer;
            this.chatService = chatService;
            socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("send_message", Message.class, onMessageReceived());
    }

        private DataListener<Message> onMessageReceived () {

        return (senderClient, data, ackSender) -> {
            log.info(String.format("%s -> %s", senderClient.getSessionId(), data.getContent()));
            // sender also receives their message
            // senderClient.getNamespace().getBroadcastOperations().sendEvent("get_message",data.getContent());

            String room = senderClient.getHandshakeData().getSingleUrlParam("room");
            senderClient.getNamespace().getRoomOperations(room).sendEvent("get_message", data);

        };
    }


        private ConnectListener onConnected () {

        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            if(room.contains(chatService.username) || room.substring(11, 20).equals(chatService.username)){

                client.joinRoom(room);
                client.getNamespace().getRoomOperations(room).sendEvent("get_message", String.format(
                        " %s connected to -> %s ", client.getSessionId(), room
                ));

                log.info(String.format("SocketId connected " + client.getSessionId().toString()));
            }

            else {
                System.out.println("boş şimdilik");
            }

        };
    }


        private DisconnectListener onDisconnected () {

        return client -> {
            log.info(String.format("SocketId disconnected " + client.getSessionId().toString()));
            //client.getNamespace().getRoomOperations(room).sendEvent("user_left", client.getSessionId());
        };
    }

}
