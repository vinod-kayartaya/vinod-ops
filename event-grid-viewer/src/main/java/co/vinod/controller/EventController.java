package co.vinod.controller;


import co.vinod.model.GridEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class EventController {
    private final SimpMessagingTemplate messagingTemplate;
    private final List<GridEvent> events = Collections.synchronizedList(new ArrayList<>());

    public EventController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/events")
    public void receiveEvent(@RequestBody List<GridEvent> gridEvents) {
        for (GridEvent event : gridEvents) {
            events.add(event);
            messagingTemplate.convertAndSend("/topic/events", event);
        }
    }

    @GetMapping("/events")
    public List<GridEvent> getEvents() {
        return new ArrayList<>(events);
    }

    @DeleteMapping("/events")
    public void clearEvents() {
        events.clear();
        messagingTemplate.convertAndSend("/topic/clear", "clear");
    }
}

