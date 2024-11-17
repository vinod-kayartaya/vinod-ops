package co.vinod.ops.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private String brand;
    
    @Column(name = "quantity_per_unit")
    private String quantityPerUnit;
    
    @Column(nullable = false)
    private Double price;
    
    private Double discount;
    
    @Column(name = "image_url")
    private String imageUrl;
}
