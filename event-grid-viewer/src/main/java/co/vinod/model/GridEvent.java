package co.vinod.model;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GridEvent {
    private String id;
    private String eventType;
    private String subject;
    private LocalDateTime eventTime;
    private Object data;
    private String dataVersion;
}