package co.vinod.ops.controller;

import co.vinod.ops.dto.ProductRequest;
import co.vinod.ops.dto.ProductResponse;
import co.vinod.ops.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Product API", description = "API endpoints for product management")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        log.info("Received request to create product");
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Integer id) {
        log.info("Received request to get product with ID: {}", id);
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves all products with pagination")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable) {
        log.info("Received request to get all products, page: {}", pageable.getPageNumber());
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates all fields of an existing product")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductRequest request) {
        log.info("Received request to update product with ID: {}", id);
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch product", description = "Partially updates an existing product")
    public ResponseEntity<ProductResponse> patchProduct(
            @PathVariable Integer id,
            @RequestBody ProductRequest request) {
        log.info("Received request to patch product with ID: {}", id);
        return ResponseEntity.ok(productService.patchProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes a product by its ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        log.info("Received request to delete product with ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/batch")
    @Operation(summary = "Create multiple products", description = "Creates multiple products in a single request")
    public ResponseEntity<Void> createProducts(@Valid @RequestBody List<ProductRequest> requests) {
        log.info("Received request to create {} products", requests.size());
        productService.createProducts(requests);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
