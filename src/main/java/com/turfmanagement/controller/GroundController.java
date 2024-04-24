package com.turfmanagement.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turfmanagement.dto.CommonApiResponse;
import com.turfmanagement.dto.GroundAddRequest;
import com.turfmanagement.dto.GroundResponseDto;
import com.turfmanagement.entity.Ground;
import com.turfmanagement.service.GroundService;
import com.turfmanagement.utility.Constants.TurfStatus;
import com.turfmanagement.utility.StorageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/ground/")
@CrossOrigin(origins = "http://localhost:3000")
public class GroundController {
	
	Logger LOG = LoggerFactory.getLogger(GroundController.class);
	
	@Autowired
	private GroundService groundService;
	
	@Autowired
	private StorageService storageService;
	
	
	@PostMapping("add")
	@ApiOperation(value = "Api to add ground")
	public ResponseEntity<CommonApiResponse> addGround(GroundAddRequest groundAddRequest) {
		LOG.info("Recieved request for Add Ground");

		CommonApiResponse response = new CommonApiResponse();

		if (groundAddRequest == null) {
			response.setSuccess(true);
			response.setResponseMessage("bad request, request data is missing");
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Ground ground = new Ground();
		ground.setDescription(groundAddRequest.getDescription());
		ground.setHeight(groundAddRequest.getHeight());
		ground.setWidth(groundAddRequest.getWidth());
		ground.setName(groundAddRequest.getName());
        ground.setPrice(groundAddRequest.getPrice());
        ground.setLength(groundAddRequest.getLength());
		ground.setStatus(TurfStatus.ACTIVE.value());
        
        String image = storageService.store(groundAddRequest.getImage());
        
        ground.setImage(image);
        
        Ground addedGround = this.groundService.addGround(ground);
        
		if (addedGround != null) {
			response.setSuccess(true);
			response.setResponseMessage("Ground Added Successfully");
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}

		else {
			response.setSuccess(true);
			response.setResponseMessage("Failed to add Ground");
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("id")
	@ApiOperation(value = "Api to fetch grounds by using Ground Id")
	public ResponseEntity<GroundResponseDto> fetchHotel(@RequestParam("groundId") int groundId) {
		LOG.info("Recieved request for Fetch Ground using Ground Id");

		GroundResponseDto response = new GroundResponseDto();
		
		if(groundId == 0) {
			response.setSuccess(true);
			response.setResponseMessage("bad request, ground id not found");
			return new ResponseEntity<GroundResponseDto>(response, HttpStatus.BAD_REQUEST);
		}
		
		Ground ground = null;
		
		ground = this.groundService.getGroundById(groundId);
		
		if(ground == null) {
			response.setSuccess(true);
			response.setResponseMessage("ground not found");
			return new ResponseEntity<GroundResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setGrounds(Arrays.asList(ground));
		response.setSuccess(true);
		response.setResponseMessage("ground fetched successfully");
		return new ResponseEntity<GroundResponseDto>(response, HttpStatus.OK);

	}

	@GetMapping("fetch")
	@ApiOperation(value = "Api to fetch all grounds")
	public ResponseEntity<GroundResponseDto> fetchAllGrounds() {
		LOG.info("Recieved request for Fetch Grounds");
		
		GroundResponseDto response = new GroundResponseDto();

		List<Ground> grounds = new ArrayList<>();
		
        grounds = this.groundService.getAllGroundByStatus(TurfStatus.ACTIVE.value());
		
        response.setGrounds(grounds);
        response.setSuccess(true);
		response.setResponseMessage("ground fetched successfully");
		return new ResponseEntity<GroundResponseDto>(response, HttpStatus.OK);

	}
	
	@DeleteMapping("delete")
	@ApiOperation(value = "Api to delete the turf")
	public ResponseEntity<CommonApiResponse> deleteTurf(@RequestParam("groundId") int groundId) {
		LOG.info("Recieved request for deleting the ground");
		
		CommonApiResponse response = new CommonApiResponse();

		if(groundId == 0) {
			response.setSuccess(true);
			response.setResponseMessage("missing ground id");
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Ground ground = this.groundService.getGroundById(groundId);
		
		if(ground == null) {
			response.setSuccess(true);
			response.setResponseMessage("ground not found");
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		ground.setStatus(TurfStatus.DELETED.value());
		
		Ground updatedGround = this.groundService.addGround(ground); // it will set ground as deleted
		
		if(updatedGround != null) {
			response.setSuccess(true);
			response.setResponseMessage("ground deleted successfully");
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		} else {
			response.setSuccess(true);
			response.setResponseMessage("failed to delete the ground");
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}
		
		
        

	}
	
	@GetMapping(value="/{groundImageName}", produces = "image/*")
	@ApiOperation(value = "Api to fetch ground image by using image name")
	public void fetchProductImage(@PathVariable("groundImageName") String groundImageName, HttpServletResponse resp) {
		System.out.println("request came for fetching ground pic");
		System.out.println("Loading file: " + groundImageName);
		Resource resource = storageService.load(groundImageName);
		if(resource != null) {
			try(InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("response sent!");
	}
	
}
