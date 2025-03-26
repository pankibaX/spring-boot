package com.pankiba.restfulwebservices;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pankiba.restfulwebservices.domain.BusinessUnit;
import com.pankiba.restfulwebservices.service.BusinessUnitService;
import com.pankiba.restfulwebservices.util.DisplayTableUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestDataLoader implements CommandLineRunner, Ordered {

	@Autowired
	private BusinessUnitService businessUnitService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {

		log.info(" Running Data Loader ");

		ObjectMapper objectMapper = new ObjectMapper();
		
		// support Java 8 date time apis
		objectMapper.registerModule(new JavaTimeModule());
		
		BusinessUnit[] businessUnits =   objectMapper.readValue(new File("src/main/resources/test-data.json"), BusinessUnit[].class);
		
		for (BusinessUnit businessUnit : businessUnits) {
			businessUnit.getEmployees().forEach( emp -> {
				System.out.println(emp.getEmployeeProfile().getPhoneNumber());
			});
			businessUnitService.createBusinessUnit(businessUnit);
		}
		
		DisplayTableUtil.printTable(jdbcTemplate, "BUSINESS_UNIT","EMPLOYEE");

	}

	@Override
	public int getOrder() {
		return 1;
	}

}
