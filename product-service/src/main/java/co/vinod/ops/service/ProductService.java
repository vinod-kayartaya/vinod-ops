package co.vinod.ops.service;

import co.vinod.ops.dto.ProductRequest;
import co.vinod.ops.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProduct(Integer id);
    Page<ProductResponse> getAllProducts(Pageable pageable);
    ProductResponse updateProduct(Integer id, ProductRequest request);
    ProductResponse patchProduct(Integer id, ProductRequest request);
    void deleteProduct(Integer id);
    void createProducts(List<ProductRequest> requests);
}
