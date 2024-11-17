package co.vinod.ops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.vinod.ops.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
