package com.verinite.commons.serviceimpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.verinite.commons.model.LookupColumns;
import com.verinite.commons.model.LookupTables;
import com.verinite.commons.repo.LookupColumnRepository;
import com.verinite.commons.repo.LookupTableRepository;
import com.verinite.commons.service.LookupService;

@Service
public class LookupServiceImpl implements LookupService {

	private final Logger logger = LoggerFactory.getLogger(LookupServiceImpl.class);

	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.driverClassName}")
	private String driverClass;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private LookupTableRepository lookUpTableRepo;

	@Autowired
	private LookupColumnRepository lookUpColumnRepo;

	@Override
	public void createLookup(String name) throws BadRequestException {
		if (StringUtils.isBlank(name)) {
			throw new BadRequestException("Invalid Lookup Name");
		}

		List<LookupTables> lookupsList = lookUpTableRepo.findAll();
		String upperName = "L_" + name.toUpperCase();
		Boolean found = lookupsList.stream().anyMatch(x -> x.getName().equalsIgnoreCase(upperName));
		if (Boolean.TRUE.equals(found)) {
			throw new BadRequestException("Lookup Already Exists");
		}

		LookupTables lookupTable = new LookupTables();
		lookupTable.setName(upperName);
		//lookUpTableRepo.save(lookupTable);
		logger.info("Lookup saved successfully");
		
		List<LookupColumns> lookupColumns = lookUpColumnRepo.findAll();
		if (CollectionUtils.isEmpty(lookupColumns)) {
			throw new BadRequestException("Lookup Columns Not Found");
		}
		SingleConnectionDataSource ds = new SingleConnectionDataSource();
	    ds.setDriverClassName(driverClass);
	    ds.setUrl(url);
	    ds.setUsername(username);
	    ds.setPassword(password);

	    JdbcTemplate jt = new JdbcTemplate(ds);
	    StringBuilder createStmt = new StringBuilder("create table " + upperName + " (");
	    for (LookupColumns column : lookupColumns) {
	    	createStmt.append(column.getName() + " varchar(255)");
	    	if (!column.getId().equals(lookupColumns.get(lookupColumns.size() - 1).getId())) {
	    		createStmt.append(",");
	    	}
	    }
	    createStmt.append(")");
	    jt.execute(createStmt.toString());
	}

}