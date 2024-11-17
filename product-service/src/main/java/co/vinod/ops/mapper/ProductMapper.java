package co.vinod.ops.mapper;

import org.mapstruct.Mapper;
import co.vinod.ops.entity.Product;
import co.vinod.ops.dto.ProductRequest;
import co.vinod.ops.dto.ProductResponse;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequest request);
    ProductResponse toResponse(Product product);
}
