package co.vinod.ops.dto;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
}
