package co.vinod.ops.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import co.vinod.ops.entity.Customer;
import co.vinod.ops.dto.CustomerDto;
import co.vinod.ops.dto.CustomerResponseDto;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerDto dto);
    CustomerResponseDto toResponseDto(Customer entity);
    void updateCustomerFromDto(CustomerDto dto, @MappingTarget Customer customer);
}
