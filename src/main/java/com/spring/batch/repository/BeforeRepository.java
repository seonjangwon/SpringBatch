package com.spring.batch.repository;

import com.spring.batch.entity.BeforeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeforeRepository extends JpaRepository<BeforeEntity,Long> {
}
