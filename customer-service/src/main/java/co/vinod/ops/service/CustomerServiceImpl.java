package co.vinod.ops.service;

import co.vinod.ops.dto.CustomerDto;
import co.vinod.ops.dto.CustomerResponseDto;
import co.vinod.ops.entity.Customer;
import co.vinod.ops.exception.CustomerNotFoundException;
import co.vinod.ops.exception.DuplicateEmailException;
import co.vinod.ops.mapper.CustomerMapper;
import co.vinod.ops.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponseDto createCustomer(CustomerDto customerDto) {
        log.info("Creating new customer with email: {}", customerDto.getEmail());
        
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            log.error("Customer with email {} already exists", customerDto.getEmail());
            throw new DuplicateEmailException("Email already exists");
        }
        
        Customer customer = customerMapper.toEntity(customerDto);
        customer = customerRepository.save(customer);
        log.info("Customer created successfully with ID: {}", customer.getId());
        
        return customerMapper.toResponseDto(customer);
    }

    @Override
    public CustomerResponseDto getCustomer(UUID id) {
        log.info("Fetching customer with ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Customer not found with ID: {}", id);
                return new CustomerNotFoundException(id);
            });
            
        return customerMapper.toResponseDto(customer);
    }

    @Override
    public Page<CustomerResponseDto> getAllCustomers(Pageable pageable) {
        log.info("Fetching customers page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        
        return customerRepository.findAll(pageable)
            .map(customerMapper::toResponseDto);
    }

    @Override
    @Transactional
    public CustomerResponseDto updateCustomer(UUID id, CustomerDto customerDto) {
        log.info("Updating customer with ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Customer not found with ID: {}", id);
                return new CustomerNotFoundException(id);
            });
            
        if (!customer.getEmail().equals(customerDto.getEmail()) && 
            customerRepository.existsByEmail(customerDto.getEmail())) {
            log.error("Cannot update customer. Email {} already exists", customerDto.getEmail());
            throw new DuplicateEmailException("Email already exists");
        }
        
        customerMapper.updateCustomerFromDto(customerDto, customer);
        customer = customerRepository.save(customer);
        log.info("Customer updated successfully with ID: {}", id);
        
        return customerMapper.toResponseDto(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDto patchCustomer(UUID id, CustomerDto customerDto) {
        log.info("Patching customer with ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Customer not found with ID: {}", id);
                return new CustomerNotFoundException(id);
            });
            
        if (customerDto.getEmail() != null && 
            !customer.getEmail().equals(customerDto.getEmail()) && 
            customerRepository.existsByEmail(customerDto.getEmail())) {
            log.error("Cannot patch customer. Email {} already exists", customerDto.getEmail());
            throw new DuplicateEmailException("Email already exists");
        }
        
        if (customerDto.getName() != null) customer.setName(customerDto.getName());
        if (customerDto.getEmail() != null) customer.setEmail(customerDto.getEmail());
        if (customerDto.getPhone() != null) customer.setPhone(customerDto.getPhone());
        if (customerDto.getAddress() != null) customer.setAddress(customerDto.getAddress());
        if (customerDto.getCity() != null) customer.setCity(customerDto.getCity());
        if (customerDto.getCountry() != null) customer.setCountry(customerDto.getCountry());
        
        customer = customerRepository.save(customer);
        log.info("Customer patched successfully with ID: {}", id);
        
        return customerMapper.toResponseDto(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(UUID id) {
        log.info("Deleting customer with ID: {}", id);
        
        if (!customerRepository.existsById(id)) {
            log.error("Customer not found with ID: {}", id);
            throw new CustomerNotFoundException(id);
        }
        
        customerRepository.deleteById(id);
        log.info("Customer deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional
    public List<CustomerResponseDto> createCustomers(List<CustomerDto> customers) {
        log.info("Creating {} customers", customers.size());
        
        return customers.stream()
            .map(this::createCustomer)
            .collect(Collectors.toList());
    }
}
