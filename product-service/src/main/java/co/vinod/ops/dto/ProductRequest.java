package co.vinod.ops.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotBlank(message = "Brand is required")
    private String brand;
    
    private String quantityPerUnit;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
    
    @PositiveOrZero(message = "Discount cannot be negative")
    private Double discount;
    
    @Pattern(regexp = "^(http|https)://.*", message = "Invalid URL format")
    private String imageUrl;
}
