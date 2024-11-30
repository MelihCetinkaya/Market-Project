package MarketProject.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
public class NotificationConfig {

    @Bean
    public ConcurrentHashMap<Long, CopyOnWriteArrayList<SseEmitter>> productEmitters() {
        return new ConcurrentHashMap<>();
    }
}
