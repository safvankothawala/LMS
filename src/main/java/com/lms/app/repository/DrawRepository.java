package com.lms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.app.entity.Draw;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {

	Draw findByDrawNumber(String drawNumber);
}
