package co.vinod.ops.service.impl;

import co.vinod.ops.dto.ProductRequest;
import co.vinod.ops.dto.ProductResponse;
import co.vinod.ops.entity.Product;
import co.vinod.ops.mapper.ProductMapper;
import co.vinod.ops.repository.ProductRepository;
import co.vinod.ops.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating new product with name: {}", request.getName());
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully with ID: {}", savedProduct.getId());
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse getProduct(Integer id) {
        log.info("Fetching product with ID: {}", id);
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.info("Fetching products page: {}", pageable.getPageNumber());
        return productRepository.findAll(pageable)
            .map(productMapper::toResponse);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Integer id, ProductRequest request) {
        log.info("Updating product with ID: {}", id);
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        
        Product updatedProduct = productMapper.toEntity(request);
        updatedProduct.setId(id);
        Product savedProduct = productRepository.save(updatedProduct);
        log.info("Product updated successfully");
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse patchProduct(Integer id, ProductRequest request) {
        log.info("Patching product with ID: {}", id);
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        
        if (request.getName() != null) product.setName(request.getName());
        if (request.getCategory() != null) product.setCategory(request.getCategory());
        if (request.getBrand() != null) product.setBrand(request.getBrand());
        if (request.getQuantityPerUnit() != null) product.setQuantityPerUnit(request.getQuantityPerUnit());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getDiscount() != null) product.setDiscount(request.getDiscount());
        if (request.getImageUrl() != null) product.setImageUrl(request.getImageUrl());
        
        Product savedProduct = productRepository.save(product);
        log.info("Product patched successfully");
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        log.info("Deleting product with ID: {}", id);
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
        log.info("Product deleted successfully");
    }

    @Override
    @Transactional
    public void createProducts(List<ProductRequest> requests) {
        log.info("Creating {} products", requests.size());
        List<Product> products = requests.stream()
            .map(productMapper::toEntity)
            .toList();
        productRepository.saveAll(products);
        log.info("Bulk product creation completed successfully");
    }
}
