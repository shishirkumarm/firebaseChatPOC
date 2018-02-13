package com.altimetrik.isha.profilemgmt.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.altimetrik.isha.profilemgmt.api.model.CountryDbDetails;
import com.altimetrik.isha.profilemgmt.api.model.repository.CountryDbDetailsRepository;
import com.altimetrik.isha.profilemgmt.api.service.ProfileService;
import com.altimetrik.isha.profilemgmt.api.tenant.TenantContext;
import com.altimetrik.isha.profilemgmt.api.tenant.hibernate.DataSourceBasedMultiTenantConnectionProviderImpl;

@Component
public class DatabaseConfigListener {

	public static final ArrayList<String> COUNTRY_LIST = new ArrayList<String>();
	
	@Autowired
	DataSourceBasedMultiTenantConnectionProviderImpl connectionProvider;
	
	@Autowired
	CountryDbDetailsRepository dbDetailsDao;
	
	@Autowired
    private DataSourceProperties properties;
	
	@Autowired
	private ProfileService service;

	
	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {
		TenantContext.setCurrentTenant("tenant_shared_schema");
		List<CountryDbDetails> dbDetailList = dbDetailsDao.findAll();
		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());
		dbDetailList.forEach(dbDetail -> {
			dataSourceBuilder
			.driverClassName(properties.getDriverClassName())
			.url(dbDetail.getDbUrl())
			.username(dbDetail.getDbUserName())
			.password(dbDetail.getDbPassword());
			if (properties.getType() != null) {
				dataSourceBuilder.type(properties.getType());
			}
			connectionProvider.getMap().put(dbDetail.getCountryName(), dataSourceBuilder.build());
			COUNTRY_LIST.add(dbDetail.getCountryName());
		});
		
		TenantContext.clear();
		
	}
}
