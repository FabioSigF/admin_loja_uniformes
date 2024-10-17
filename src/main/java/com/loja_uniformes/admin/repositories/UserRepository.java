package com.loja_uniformes.admin.repositories;

import com.loja_uniformes.admin.domain.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    public UserDetails findByLogin(String login);
}
