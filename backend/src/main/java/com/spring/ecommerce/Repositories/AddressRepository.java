package com.spring.ecommerce.Repositories;

import com.spring.ecommerce.Models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // Additional query methods can be defined here if needed
}
