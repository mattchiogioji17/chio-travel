package com.chio.webportal;

import com.chio.webportal.dao.RoleDao;
import com.chio.webportal.domain.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebPortalApplication implements CommandLineRunner{

	@Autowired
	private RoleDao roleDao;

	public static void main(String[] args) {
		SpringApplication.run(WebPortalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role role = new Role();
		role.setName("ROLE_USER");
		roleDao.save(role);
	}
}
