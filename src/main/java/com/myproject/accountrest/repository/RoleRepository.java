package com.myproject.accountrest.repository;

import com.myproject.accountrest.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
Optional<RoleEntity> findByName(String name);
Boolean existsByName(String name);
}
