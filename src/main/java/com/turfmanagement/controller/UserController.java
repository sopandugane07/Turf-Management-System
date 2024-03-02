package com.turfmanagement.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turfmanagement.dto.AddWalletMoneyRequestDto;
import com.turfmanagement.dto.CommonApiResponse;
import com.turfmanagement.dto.UserLoginRequest;
import com.turfmanagement.dto.UserLoginResponse;
import com.turfmanagement.dto.UserLoginResponseDto;
import com.turfmanagement.dto.UserRoleResponse;
import com.turfmanagement.dto.UsersResponseDto;
import com.turfmanagement.entity.User;
import com.turfmanagement.service.CustomUserDetailsService;
import com.turfmanagement.service.UserService;
import com.turfmanagement.utility.Constants.Sex;
import com.turfmanagement.utility.Constants.UserRole;
import com.turfmanagement.utility.JwtUtil;

import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("api/user/")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("roles")
	@ApiOperation(value = "Api to get all user roles")
	public ResponseEntity<?> getAllUsers() {
		
		UserRoleResponse response = new UserRoleResponse();
		List<String> roles = new ArrayList<>();
		
		for(UserRole role : UserRole.values() ) {
			roles.add(role.value());
		}
		
		if(roles.isEmpty()) {
			response.setSuccess(true);
			response.setResponseMessage("Failed to Fetch User Roles");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		else {
		    response.setRoles(roles);
			response.setSuccess(true);
			response.setResponseMessage("User Roles Fetched success");
			return new ResponseEntity(response, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("gender")
	@ApiOperation(value = "Api to get all user gender")
	public ResponseEntity<?> getAllUserGender() {
		
		UserRoleResponse response = new UserRoleResponse();
		List<String> genders = new ArrayList<>();
		
		for(Sex gender : Sex.values() ) {
			genders.add(gender.value());
		}
		
		if(genders.isEmpty()) {
			
			response.setResponseMessage("Failed to Fetch User Genders");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		else {
			response.setGenders(genders);
			response.setResponseMessage("User Genders Fetched success");
			return new ResponseEntity(response, HttpStatus.OK);
		}
		
	}
	
	@PostMapping("register")
	@ApiOperation(value = "Api to register any User")
	public ResponseEntity<CommonApiResponse> register(@RequestBody User user) {
		LOG.info("Recieved request for User register");

		CommonApiResponse response = new CommonApiResponse();
		
		if(user == null) {
			response.setResponseMessage("Bad request, user data is missing");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);
		user.setWalletAmount(0);

		User registerUser = userService.registerUser(user);

		if (registerUser != null) {
			response.setSuccess(true);
			response.setResponseMessage(user.getRole() + " User Registered Successfully");
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}

		else {
			response.setSuccess(true);
			response.setResponseMessage("Failed to Register " + user.getRole() + " User");
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("login")
	@ApiOperation(value = "Api to login any User")
	public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequest userLoginRequest) {
		LOG.info("Recieved request for User Login");

		UserLoginResponseDto response = new UserLoginResponseDto();
		
		if(userLoginRequest == null) {
			response.setResponseMessage("Bad request, login data is missing");
			response.setSuccess(true);

			return new ResponseEntity<UserLoginResponseDto>(response, HttpStatus.BAD_REQUEST);
		}
		
		String jwtToken = null;
		UserLoginResponse useLoginResponse = new UserLoginResponse();
        User user = null;
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userLoginRequest.getEmailId(), userLoginRequest.getPassword()));
		} catch (Exception ex) {
			LOG.error("Autthentication Failed!!!");
			response.setSuccess(true);
			response.setResponseMessage("Failed to Login");
			return new ResponseEntity<UserLoginResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(userLoginRequest.getEmailId());

		for (GrantedAuthority grantedAuthory : userDetails.getAuthorities()) {
			if (grantedAuthory.getAuthority().equals(userLoginRequest.getRole())) {
				jwtToken = jwtUtil.generateToken(userDetails.getUsername());
			}
		}

		// user is authenticated
		if (jwtToken != null) {

			user = userService.getUserByEmailId(userLoginRequest.getEmailId());
			
			useLoginResponse = User.toUserLoginResponse(user);
			useLoginResponse.setJwtToken(jwtToken);
			
			response.setUser(useLoginResponse);

			response.setSuccess(true);
			response.setResponseMessage("Logged in Successful..!!!");
			return new ResponseEntity<UserLoginResponseDto>(response, HttpStatus.OK);
		
		}

		else {
			response.setSuccess(true);
			response.setResponseMessage("Failed to Log in..!!!");
			return new ResponseEntity<UserLoginResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
	}

	@GetMapping("id")
	@ApiOperation(value = "Api to fetch the User using user Id")
	public ResponseEntity<UsersResponseDto> fetchUser(@RequestParam("userId") int userId) {
		
		LOG.info("Recieved request for fetching user by user id");
		
		UsersResponseDto response = new UsersResponseDto();
		
		if(userId == 0) {
			response.setResponseMessage("Bad request, user id is missing");
			response.setSuccess(true);

			return new ResponseEntity<UsersResponseDto>(response, HttpStatus.BAD_REQUEST);
		}
		
		User user = userService.getUserById(userId);
		
		if(user == null) {
			response.setResponseMessage("Bad request, user not found");
			response.setSuccess(true);

			return new ResponseEntity<UsersResponseDto>(response, HttpStatus.BAD_REQUEST);
		}
		
		response.setUsers(Arrays.asList(user));
		response.setResponseMessage("User fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<UsersResponseDto>(response, HttpStatus.OK);
	}
	
	@GetMapping("customer/all")
	public ResponseEntity<UsersResponseDto> getAllCustomers() {
		
		LOG.info("Received the request for getting all the customer");
		
		UsersResponseDto response = new UsersResponseDto();
		
		List<User> customers = new ArrayList<>();
		customers = this.userService.getAllUserByRole(UserRole.CUSTOMER.value());
		
		response.setUsers(customers);
		response.setResponseMessage("Customers fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<UsersResponseDto>(response, HttpStatus.OK);
	}
	
	@PostMapping("add/wallet/money")
	@ApiOperation(value = "Api to add wallet money")
	public ResponseEntity<CommonApiResponse> register(@RequestBody AddWalletMoneyRequestDto request) {
		LOG.info("Recieved request for ");

		CommonApiResponse response = new CommonApiResponse();
		
		if (request == null) {
			response.setResponseMessage("Bad Request, improper request data");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(request.getUserId() == 0) {
			response.setResponseMessage("Bad Request, user id is missing");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(request.getWalletAmount() == 0 || request.getWalletAmount() < 0) {
			response.setResponseMessage("Bad Request, improper data");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

        User user = userService.getUserById(request.getUserId());
		
		if(user == null) {
			response.setResponseMessage("Bad Request, user not found!!!");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		double walletAmount = user.getWalletAmount();
		double walletToUpdate = walletAmount + request.getWalletAmount();

		user.setWalletAmount(walletToUpdate);
		
		User udpatedUser = userService.updateUser(user);
		
		if(udpatedUser != null) {
			response.setResponseMessage("Money added in wallet successfully!!!");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		} else {
			response.setResponseMessage("Failed to add the money in wallet!!!");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("customer/wallet/fetch")
	public ResponseEntity<?> getCustomerWallet(@RequestParam("userId") int userId) {
		
		LOG.info("Received the request for getting the customer wallet detail");
		
		User user = userService.getUserById(userId);
		
		double walletAmount = user.getWalletAmount();

		return new ResponseEntity<>(walletAmount, HttpStatus.OK);
		
	}

}
