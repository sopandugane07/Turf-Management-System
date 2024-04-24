package com.turfmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turfmanagement.entity.Booking;

@Repository
public interface BookingDao extends JpaRepository<Booking, Integer> {
	
	List<Booking> findByUserId(int userId);

	List<Booking> findByDateAndGroundIdAndTimeSlotAndStatus(String date,int groundId, String timeSlot, String status);
	
	List<Booking> findByGroundId(int groundId);
	
}
