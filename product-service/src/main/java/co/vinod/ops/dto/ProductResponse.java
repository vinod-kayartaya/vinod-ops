package co.vinod.ops.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private Integer id;
    private String name;
    private String category;
    private String brand;
    private String quantityPerUnit;
    private Double price;
    private Double discount;
    private String imageUrl;
}
