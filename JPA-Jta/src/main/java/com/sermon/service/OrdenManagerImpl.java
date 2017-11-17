package com.sermon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sermon.dao.OrdenDao;
import com.sermon.model.Orden;

@Service
public class OrdenManagerImpl implements OrdenManager {

	@Autowired
	private OrdenDao dao;
	
	public List<Orden> getOrdens() {
		return dao.getOrden();
	}

}
