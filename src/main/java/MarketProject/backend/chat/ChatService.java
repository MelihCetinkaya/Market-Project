package MarketProject.backend.chat;

import MarketProject.backend.service.impl.JwtService;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class ChatService {

   // protected String jwtToken;
    protected String username;
    protected Boolean checkToken = false;
    private final JwtService jwtService;
    private final SocketIOServer socketIOServer;

    public void startSocketServerManually(String token,String senderUsername) {

        if (checkToken) {
            System.out.println("Socket.io server has already started");

        } else if (!jwtService.checkTokenUsername(token,senderUsername)) {
            System.out.println("you entered wrong information");

        } else {
            try {
                checkToken = true;
                //jwtToken=token;
                username=senderUsername;
                socketIOServer.start();
                System.out.println("Socket.IO server started.");

            } catch (Exception e) {
                System.out.println("there is an exception while starting socket.io server");
            }
        }
    }

    public void checkRoom(){


    }

    public void sendMessageTo(String token, String senderUsername, String receiverUsername, Message message) {

        checkToken = jwtService.checkTokenUsername(token, senderUsername);


    }
}
