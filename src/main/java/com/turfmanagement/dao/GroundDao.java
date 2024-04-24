package com.turfmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turfmanagement.entity.Ground;

@Repository
public interface GroundDao extends JpaRepository<Ground, Integer> {
	
	List<Ground> findByStatus(int status);

}
