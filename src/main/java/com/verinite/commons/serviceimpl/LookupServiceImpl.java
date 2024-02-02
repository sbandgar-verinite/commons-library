package com.verinite.commons.serviceimpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verinite.commons.controlleradvice.BadRequestException;
import com.verinite.commons.controlleradvice.InternalServerException;
import com.verinite.commons.dto.LookupResponse;
import com.verinite.commons.dto.LookupRowMapper;
import com.verinite.commons.dto.StatusResponse;
import com.verinite.commons.model.LookupColumns;
import com.verinite.commons.model.LookupTables;
import com.verinite.commons.repo.LookupColumnRepository;
import com.verinite.commons.repo.LookupTableRepository;
import com.verinite.commons.service.LookupService;
import com.verinite.commons.util.Constants;

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
	private LookupTableRepository lookUpTableRepo;

	@Autowired
	private LookupColumnRepository lookUpColumnRepo;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public StatusResponse createLookup(String name) throws BadRequestException {
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
		lookupTable.setLabel(name);
		lookUpTableRepo.save(lookupTable);
		logger.info("Lookup saved successfully");

		List<LookupColumns> lookupColumns = lookUpColumnRepo.findAll();
		if (CollectionUtils.isEmpty(lookupColumns)) {
			throw new BadRequestException("Lookup Columns Not Found");
		}
		JdbcTemplate jt = getConnection();
		StringBuilder createStmt = new StringBuilder("create table " + upperName + " (");
		for (LookupColumns column : lookupColumns) {
			if (column.getDatatype().equalsIgnoreCase(Constants.TEXT)) {
				createStmt.append("`" + column.getName() + "` varchar(255)");
			}
			if (column.getDatatype().equalsIgnoreCase(Constants.NUMERIC)) {
				createStmt.append("`" + column.getName() + "` bigint");
			}
			if (!column.getId().equals(lookupColumns.get(lookupColumns.size() - 1).getId())) {
				createStmt.append(",");
			}
		}
		createStmt.append(")");
		try {
			jt.execute(createStmt.toString());
		} catch (Exception ex) {
			logger.error("Unable to execute query : {}", ex.getMessage());
			throw new InternalServerException(ex.getMessage());
		}
		return new StatusResponse(Constants.SUCCESS, HttpStatus.CREATED.value(), "Lookups Created Successfully");
	}

	private JdbcTemplate getConnection() {
		SingleConnectionDataSource ds = new SingleConnectionDataSource();
		ds.setDriverClassName(driverClass);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);

		return new JdbcTemplate(ds);
	}

	@Override
	public StatusResponse addValues(String lookup, Object data) throws BadRequestException {
		if (data == null) {
			throw new BadRequestException("Invalid Data Object");
		}
		Optional<LookupTables> lookupData = lookUpTableRepo.findByName("L_" + lookup.toUpperCase());
		if (lookupData.isEmpty()) {
			throw new BadRequestException("Lookup Not Found");
		}
		JsonNode request = mapper.convertValue(data, JsonNode.class);
		List<LookupColumns> lookupColumns = lookUpColumnRepo.findAll();
		if (CollectionUtils.isEmpty(lookupColumns)) {
			throw new BadRequestException("Lookup Columns Not Found");
		}
		StringBuilder createStmt = new StringBuilder("insert into " + lookupData.get().getName() + " (");
		if (request.isArray()) {
			StringBuilder columnNames = new StringBuilder();
			StringBuilder values = new StringBuilder();
			lookupColumns.stream().filter(x -> !x.getName().equalsIgnoreCase("id"))
					.forEach(x -> columnNames.append("`" + x.getName() + "`,"));
			for (JsonNode value : request) {
				StringBuilder columnData = new StringBuilder();
				values.append("(");
				lookupColumns.stream().filter(x -> !x.getName().equalsIgnoreCase("id")).forEach(x -> {
					if (Objects.nonNull(value.get(x.getName()))) {
						columnData.append(value.get(x.getName()) + ",");
					}
				});
				values.append(StringUtils.chop(columnData.toString())).append("),");
			}
			createStmt.append(StringUtils.chop(columnNames.toString())).append(") values ")
					.append(StringUtils.chop(values.toString()));
		}
		try {
			JdbcTemplate jt = getConnection();
			jt.execute(createStmt.toString());
		} catch (Exception ex) {
			logger.error("Unable to execute query : {}", ex.getMessage());
			throw new InternalServerException(ex.getMessage());
		}
		return new StatusResponse(Constants.SUCCESS, HttpStatus.CREATED.value(), "Lookups Added Successfully");
	}

	@Override
	public List<Object> getLookups(String name) {
		Optional<LookupTables> lookupData = lookUpTableRepo.findByName("L_" + name.toUpperCase());
		if (lookupData.isEmpty()) {
			throw new BadRequestException("Lookup Not Found");
		}
		StringBuilder createStmt = new StringBuilder("select * from " + lookupData.get().getName());
		JdbcTemplate jt = getConnection();
		return jt.query(createStmt.toString(), new LookupRowMapper());
	}
}