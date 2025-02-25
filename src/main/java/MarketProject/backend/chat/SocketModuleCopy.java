package MarketProject.backend.chat;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SocketModuleCopy {

       /* private final SocketIOServer socketIOServer;

        public SocketModuleCopy(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
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
            client.joinRoom(room);
            client.getNamespace().getRoomOperations(room).sendEvent("get_message", String.format(
                    " %s connected to -> %s ", client.getSessionId(), room
            ));
            //client.getNamespace().getRoomOperations(room).sendEvent("user_joined", client.getSessionId());

            log.info(String.format("SocketId connected " + client.getSessionId().toString()));
        };
    }


        private DisconnectListener onDisconnected () {

        return client -> {
            log.info(String.format("SocketId disconnected " + client.getSessionId().toString()));
            //client.getNamespace().getRoomOperations(room).sendEvent("user_left", client.getSessionId());
        };
    }*/

    }

