package com.batch.service;

import org.springframework.stereotype.Component;

import com.batch.process.CommonsProcessor;

@Component
public class GlnServices extends CommonsProcessor {
	/*
	@Autowired
	CatalogService catalogService;

	public Long getIdGln(Long idCountry, String code){
		return catalogService.getGlnByCode(idCountry,code);	
	}
	*/
}
