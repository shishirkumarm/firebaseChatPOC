package com.altimetrik.isha.profilemgmt.api.tenant.hibernate;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 8168907057647334460L;

	@Autowired
	private DataSource dataSource;

	
	
	@Autowired
    private DataSourceProperties properties;
	
	
	private Map<String, DataSource> map;

	@PostConstruct
	public void load() {
		map = new HashMap<>();
		map.put("tenant_shared_schema",dataSource);
	}

	@Override
	protected DataSource selectAnyDataSource() {
		return dataSource;
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return map.get(tenantIdentifier);
	}

	public Map<String, DataSource> getMap() {
		return map;
	}

	public void setMap(Map<String, DataSource> map) {
		this.map = map;
	}
}
