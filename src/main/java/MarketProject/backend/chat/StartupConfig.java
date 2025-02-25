package MarketProject.backend.chat;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupConfig  { //CommandLineRunner

    private final SocketIOServer socketIOServer;

 /*   @Override
    public void run(String... args) throws Exception {

        if (chatService.checkToken){

            socketIOServer.start();
        }
        else
            System.out.println("Socket.io server is not active");

    }*/

     @PreDestroy
     public void stopSocketServer() {
        this.socketIOServer.stop();
    }
}
