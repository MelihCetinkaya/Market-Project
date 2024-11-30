package MarketProject.backend.api.DevelopingApi;

import MarketProject.backend.entity.Product;
import MarketProject.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ModelService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final Repo modelRepository;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ModelService.class);
   public Mono<Model1> createModel(Model1 model){
       return modelRepository.save(model);
   }


    public Flux<Model1> getAllModels(){
        return  modelRepository.findAll();
    }

    public Mono<Model1> findById(Long notificationId){
        return modelRepository.findById(notificationId);
    }


    public void consumeServerSentEvent() {
        WebClient client = WebClient.create("http://localhost:8085/sse-server");
        ParameterizedTypeReference<ServerSentEvent<String>> type
                = new ParameterizedTypeReference<ServerSentEvent<String>>() {};

        Flux<ServerSentEvent<String>> eventStream = client.get()
                .uri("/stream-sse")
                .retrieve()
                .bodyToFlux(type);

        eventStream.subscribe(
                content -> logger.info("Time: {} - event: name[{}], id [{}], content[{}] ",
                        LocalTime.now(), content.event(), content.id(), content.data()),
                error -> logger.error("Error receiving SSE: {}", error),
                () -> logger.info("Completed!!!"));
    }

    public boolean consumeServerSentEvent2(Long id) {

      Optional <Product> product=productRepository.findById(id);
        return product.isPresent();
    }
}
