package com.sermon.main;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sermon.model.Orden;
import com.sermon.service.OrdenManager;

public class JpaSpringPracticeMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		ctx.registerShutdownHook();

		OrdenManager ordenManager = (OrdenManager) ctx.getBean(OrdenManager.class);

		List<Orden> list = ordenManager.getOrdens();
		System.out.println("Orden count: " + list.size());

	}

}
