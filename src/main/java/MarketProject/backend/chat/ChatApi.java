package MarketProject.backend.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatApi {

    private final ChatService chatService;

    @GetMapping("/messageTo")
    public void sendMessageTo(@RequestHeader("Authorization") String token, @RequestParam String senderUsername,
                              @RequestParam String receiverUsername, @RequestBody Message message) {
        chatService.sendMessageTo(token, senderUsername, receiverUsername, message);
    }

    @PostMapping("/enableChat")
    public void EnableChatService(@RequestHeader("Authorization") String token,@RequestParam String senderUsername) {

        chatService.startSocketServerManually(token,senderUsername);

    }

}
