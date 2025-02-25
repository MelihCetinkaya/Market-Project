package MarketProject.backend.chat;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {


    @Value("${socket-server.port}")
    private int port;

    @Value("${socket-server.host}") //192.168.222.162,10.39.194.161
    private String host;


    @Bean
    public SocketIOServer socketIOServer(){

        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();

        config.setHostname(host);
        config.setPort(port);

        return new SocketIOServer(config);
    }

}
