package com.lms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.app.entity.License;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {

}
