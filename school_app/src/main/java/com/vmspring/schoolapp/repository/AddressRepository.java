package com.vmspring.schoolapp.repository;


import com.vmspring.schoolapp.model.Address;
import com.vmspring.schoolapp.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
