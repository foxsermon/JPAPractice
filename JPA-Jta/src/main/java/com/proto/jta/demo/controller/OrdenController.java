package com.proto.jta.demo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sermon.model.Orden;
import com.sermon.service.OrdenManager;

@RestController
public class OrdenController {

	private static final Logger logger = Logger.getLogger(OrdenController.class);
	
	@Autowired
	private OrdenManager service;
	
	@RequestMapping(method = RequestMethod.GET, value = "/ordens", headers = "Accept=application/json")
	public List<Orden> findAll() {
		List<Orden> ordens = service.getOrdens();
		logger.info("Size : " + ordens.size());
		return ordens;
	}
}
