package co.vinod.ops.controller;

import co.vinod.ops.dto.CustomerDto;
import co.vinod.ops.dto.CustomerResponseDto;
import co.vinod.ops.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management API", description = "APIs for managing customer information")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Creates a new customer with the provided information")
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieves a customer's information by their ID")
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieves all customers with pagination support")
    public ResponseEntity<Page<CustomerResponseDto>> getAllCustomers(Pageable pageable) {
        return ResponseEntity.ok(customerService.getAllCustomers(pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer", description = "Updates all fields of an existing customer")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch customer", description = "Partially updates an existing customer")
    public ResponseEntity<CustomerResponseDto> patchCustomer(
            @PathVariable UUID id,
            @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.patchCustomer(id, customerDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer", description = "Deletes an existing customer")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/batch")
    @Operation(summary = "Create multiple customers", description = "Creates multiple customers in a single request")
    public ResponseEntity<List<CustomerResponseDto>> createCustomers(
            @Valid @RequestBody List<CustomerDto> customers) {
        customerService.createCustomers(customers);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
