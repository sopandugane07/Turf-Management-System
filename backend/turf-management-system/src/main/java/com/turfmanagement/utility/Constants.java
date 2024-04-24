package com.turfmanagement.utility;

public class Constants {
	
	public enum UserRole {
		ADMIN("admin"),
		CUSTOMER("customer");
		
		private String role;

	    private UserRole(String role) {
	      this.role = role;
	    }

	    public String value() {
	      return this.role;
	    }    
	}
	
	public enum Sex {
		MALE("Male"),
		FEMALE("Female");
		
		private String sex;

	    private Sex(String sex) {
	      this.sex = sex;
	    }

	    public String value() {
	      return this.sex;
	    }    
	}
	
	public enum BookingStatus {
		APPROVED("Approved"),
		PENDING("Pending"),
		CANCEL("Cancel");
		
		
		private String status;

	    private BookingStatus(String status) {
	      this.status = status;
	    }

	    public String value() {
	      return this.status;
	    }    
	}
	
	public enum ResponseCode {
		SUCCESS(0),
		FAILED(1);
		
		
		private int code;

	    private ResponseCode(int code) {
	      this.code = code;
	    }

	    public int value() {
	      return this.code;
	    }    
	}
	
	public enum TimeSlot {
		NINE_TO_TEN_AM("09:00 - 10:00 am"),
		TEN_TO_ELEVEN_AM("10:00 - 11:00 am"),
		ELEVEN_TO_TWELLVE_AM("11:00 - 12:00 am"),
		TWELVE_TO_ONE_PM("12:00 - 01:00 pm"),
		ONE_TO_TWO_PM("01:00 - 02:00 pm"),
		TWO_TO_THREE_PM("02:00 - 03:00 pm"),
		THREE_TO_FOUR_PM("03:00 - 04:00 pm"),
		FOUR_TO_FIVE_PM("04:00 - 05:00 pm"),
		FIVE_TO_SIX_PM("05:00 - 06:00 pm"),
		SIX_TO_SEVEN_PM("06:00 - 07:00 pm"),
		SEVEN_TO_EIGHT_PM("07:00 - 08:00 pm"),
		EIGHT_TO_NINE_PM("08:00 - 09:00 pm"),
		NINE_TO_TEN_PM("09:00 - 10:00 pm");
		
		private String time;

	    private TimeSlot(String time) {
	      this.time = time;
	    }

	    public String value() {
	      return this.time;
	    }
	     
	}
	
	public enum TurfStatus {
		ACTIVE(1),
		DELETED(0);
		
		
		private int status;

	    private TurfStatus(int status) {
	      this.status = status;
	    }

	    public int value() {
	      return this.status;
	    }    
	}
	
}
