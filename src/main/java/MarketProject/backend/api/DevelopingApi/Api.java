package MarketProject.backend.api.DevelopingApi;


import MarketProject.backend.service.impl.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class Api {

    private final ModelService modelService;
    private final JwtService jwtService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Model1> create(@RequestBody Model1 model1){
        return modelService.createModel(model1);
    }

    @GetMapping
    public Flux<Model1> getAllModels(){
        return modelService.getAllModels();
    }

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(sequence -> ServerSentEvent.<String> builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now().toString())
                        .build());
    }

    List<SseEmitter> emitters =new CopyOnWriteArrayList<>();
    //alternative private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    @GetMapping(value="/subscribe",consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@RequestHeader("Authorization") String token,
                                @PathVariable String username,
                                @RequestParam Long productId){

        if (jwtService.checkTokenUsername(token,username)) {


            SseEmitter sseEmitter=new SseEmitter(30_000L);
            try {
                sseEmitter.send(SseEmitter.event().name("INIT"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sseEmitter.onCompletion(()-> emitters.remove(sseEmitter));

            emitters.add(sseEmitter);
            return sseEmitter;
        }
       return null; // throw new exception

    }

    @PostMapping("/dispatchEvents")
    public void dispatchEventsToClients(){

   for(SseEmitter emitter : emitters){

    try {
        emitter.send(SseEmitter.event().name("latest name"));
    } catch (IOException e) {
        emitters.remove(emitter);
    }

}

    }
}
