package co.vinod.ops.service;

import co.vinod.ops.dto.CustomerDto;
import co.vinod.ops.dto.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;
import java.util.List;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerDto customerDto);
    CustomerResponseDto getCustomer(UUID id);
    Page<CustomerResponseDto> getAllCustomers(Pageable pageable);
    CustomerResponseDto updateCustomer(UUID id, CustomerDto customerDto);
    CustomerResponseDto patchCustomer(UUID id, CustomerDto customerDto);
    void deleteCustomer(UUID id);
    List<CustomerResponseDto> createCustomers(List<CustomerDto> customers);
}
