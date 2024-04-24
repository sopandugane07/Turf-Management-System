package com.turfmanagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.turfmanagement.dao.BookingDao;
import com.turfmanagement.entity.Booking;

@Service
public class BookingService {

	@Autowired
	private BookingDao bookingDao;
	
	public Booking addBooking(Booking booking) {
		return bookingDao.save(booking);
	}
	
	public Booking getBookById(int id) {
		return bookingDao.findById(id).get();
	}
	
	public List<Booking> getAllBooking() {
		return bookingDao.findAll();
	}
	
	public List<Booking> getBookingByDateAndGroundIdAndTimeSlotAndStatus(String date, int groundId, String timeSlot, String status) {
		return bookingDao.findByDateAndGroundIdAndTimeSlotAndStatus(date, groundId, timeSlot, status);
	}
	 
	public List<Booking> getBookingsByUserId(int userId) {
		return bookingDao.findByUserId(userId);
	}
	
	public List<Booking> getBookingsByGroundId(int groundId) {
		return bookingDao.findByUserId(groundId);
	}
	
}
