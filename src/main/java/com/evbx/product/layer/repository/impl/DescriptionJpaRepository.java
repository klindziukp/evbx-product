package com.evbx.product.layer.repository.impl;

import com.evbx.product.layer.repository.DescriptionRepository;
import com.evbx.product.model.domain.Description;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionJpaRepository extends JpaRepository<Description, Long>, DescriptionRepository {

}
