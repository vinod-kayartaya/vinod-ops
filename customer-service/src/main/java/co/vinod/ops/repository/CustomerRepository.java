package co.vinod.ops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.vinod.ops.entity.Customer;
import java.util.UUID;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
}
