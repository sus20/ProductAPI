package com.example.productservice.adapter.out.persistence;

import com.example.productservice.adapter.out.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
}
