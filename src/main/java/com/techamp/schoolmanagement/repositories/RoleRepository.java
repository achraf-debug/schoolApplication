package com.techamp.schoolmanagement.repositories;

import com.techamp.schoolmanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role getRoleByRoleName(String roleName);
}
