package com.walab.happymanback.user.repository;

import com.walab.happymanback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {}
