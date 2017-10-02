package com.batch.service;

import org.springframework.stereotype.Component;

import com.batch.process.CommonsProcessor;

@Component
public class CustomerServices extends CommonsProcessor{
	/*
	@Autowired
	private TownDao townDao;
	@Autowired
	private NeighborhoodDao neighborhoodDao;
	@Autowired
	private PostalCodeDao postalCodeDao;
	@Autowired
	private CatalogService catalogService; 
	@Autowired
	private CustomerService customerService;
	@Autowired
	private HierarchyCustomerService hierarchyCustomerService;
	
	public static final String PAYMENT_METHOD_CREDIT = "CREDIT";
	private static List<Town> listTown;
	private LoggerUtil logger = LoggerUtil.getInstance();
	
	public CustomerTemp initializeCustomerTemporalObject() {
		CustomerTemp customerTemp = new CustomerTemp();
		customerTemp.setParent(new ParentLight());
		customerTemp.setCountry(new Country());
		customerTemp.setAddressTemp(new AddressTemp());
		customerTemp.setFiscalImformationTemp(new FiscalImformationTemp());
		customerTemp.getFiscalImformationTemp().setAddressTemp(
				new AddressTemp());
		customerTemp.getFiscalImformationTemp().getAddressTemp()
				.setState(new State());
		customerTemp.getFiscalImformationTemp().getAddressTemp()
				.setTown(new Town());
		customerTemp.getFiscalImformationTemp().getAddressTemp()
				.setNeighborhood(new Neighborhood());
		customerTemp.getFiscalImformationTemp().getAddressTemp()
				.setPostalCode(new PostalCode());
		customerTemp.setHierarchyCustomerTemp(new HierarchyCustomerTemp());
		// customerTemp.getAddressTemp().setCountry(new Country());
		// customerTemp.getAddressTemp().getCountry().setIdCountry(1000L);
		customerTemp.getFiscalImformationTemp().getAddressTemp()
				.setCountry(customerTemp.getAddressTemp().getCountry());
		customerTemp.getAddressTemp().setState(new State());
		customerTemp.getAddressTemp().setTown(new Town());
		customerTemp.getAddressTemp().setNeighborhood(new Neighborhood());
		customerTemp.getAddressTemp().setPostalCode(new PostalCode());
		customerTemp.setOrgDataTemp(new CustomerOrgDataTemp());
		customerTemp.getOrgDataTemp().setOrganization(new Organization());
		customerTemp.getOrgDataTemp().setPaymentMethod(new PaymentMethod());
		customerTemp.getOrgDataTemp().setChannelType(new ChannelType());
		customerTemp.getOrgDataTemp().setPaymentTerms(new PaymentTerms());
		customerTemp.getOrgDataTemp().setFiscalType(new FiscalType());
		customerTemp.getOrgDataTemp().setInvoiceType(new InvoiceType());
		customerTemp.getOrgDataTemp().setPaymentForm(new PaymentForm());
		customerTemp.getOrgDataTemp().setPaymentPlace(new PaymentPlace());
		customerTemp.getOrgDataTemp().setPriceList(new PriceList());
		customerTemp.getOrgDataTemp().setCommercialConditionsTemps(new HashSet<CommercialConditionsTemp>());
		customerTemp.getOrgDataTemp().setConsolidationPlace(
				new ConsolidationPlace());
		customerTemp.getOrgDataTemp().setConsolidationLevel(
				new ConsolidationLevel());
		customerTemp.getOrgDataTemp().setConsolidationDay(
				new ConsolidationDay());
		customerTemp.getOrgDataTemp().setEnabled(true);
		customerTemp.setCustomerType(new CustomerType());
		customerTemp.setClassification(new Classification());
		customerTemp.setCategory(new Category());
		//customerTemp.setPriceList(new PriceList());
		customerTemp.setLeveltype(new Leveltype());
		customerTemp.setCustomerGlnTemps(new HashSet<CustomerGlnTemp>());
		
		// TODO Carga Masiva: Preguntar si esto aplica, y como identificar este agrupador
		customerTemp.setGrouperInstances(new HashSet<GrouperInstance>());

		customerTemp.setAttributeValues(new HashSet<AttributeValueVO>());
		return customerTemp;
	}

	public Long findChannelTypeByCode(String code) {
		return catalogService.findChannelTypeByCode(code);
	}
	
	public Long findStateByDescription(Long idCountry, String description){
		return catalogService.findStateByDescription(idCountry, description);
	}
	
	public Long findLevelTypeByCode(Long idCountry, String code){
		return catalogService.findLevelTypeByCode(idCountry, code);
	}
	
	public Leveltype getLevelTypeByCode(Long idCountry, String code){
		return catalogService.getLevelTypeByCode(idCountry, code);
	}

	public void fillCustomerDynamicFields(CustomerTemp customerTemp, Sheet item, Long idMassiveLoad) {
		List<Field> fields = catalogService.getFlexFieldByCountry(getMassiveLoad(idMassiveLoad).getCountry().getIdCountry());
		java.lang.reflect.Field[] fieldsR = customerTemp.getClass()
				.getDeclaredFields();
		for (Field field : fields) {
			if(field.getDynamicFieldName()!=null){
				Cell cell = item.getCell(field.getDynamicFieldName());
				if (cell != null) {
					if (field.getColumn() != null) {
						fillFlexFieldAttributes(customerTemp, fieldsR, field
								.getColumn().getAttributeName(),
								(String) cell.getValue());
					}
				}
			}
		}
	}

	private void fillFlexFieldAttributes(Object object,
			java.lang.reflect.Field[] fields, String fieldName,
			String fieldValue) {
		for (java.lang.reflect.Field field : fields) {
			if (field.getName().equalsIgnoreCase(fieldName)) {
				setFieldValue(object, fieldValue, field);
			}
		}
	}

	private void setFieldValue(Object object, String value,
			java.lang.reflect.Field field) {
		field.setAccessible(true);
		try {
			field.set(object, value);
		} catch (IllegalArgumentException e) {
//			logger.error(e, e);
			writeLog(e.getMessage());
		} catch (IllegalAccessException e) {
//			logger.error(e, e);
			writeLog(e.getMessage());
		}
	}
	
	public Long getId(Catalog catalog, Long id, String description,
			Long idCountry) {
		Long idItem = null;
		switch (catalog) {
			case TOWN: {
						if(StringUtils.hasText(description)){
								if(listTown == null){
									listTown = townDao.findTownByAutocomplete(id, description);
								}
								return listTown.size() > 0 ? listTown.get(0).getIdTown() : null;
						}
			}
		}
		return idItem;
	}
	
	public Long findPaymentMethodByCode(Long idCountry, Long code) {
		return catalogService.getPaymentMethodByCode(idCountry,code);
	} 
	
	public Long findFiscalTypeByCode(Long idCountry, Long code) {
		return catalogService.getFyscalTypeByCode(idCountry,code);
	}
	
	public Long findInvoiceTypeByCode(Long idCountry, Long code) {
		return catalogService.getInvoiceTypeByCode(idCountry,code);
	} 
	
	public Long findPaymentFormByCode(Long idCountry, String code) {
		return catalogService.getPaymentFormByCode(idCountry,code);
	}
	
	public Long findPaymentPlaceByCode(Long idCountry, Integer code) {
		return catalogService.getPaymentPlaceByCode(idCountry,code);
	}
	
	public Long findConsolidationPlaceByCode(Long idCountry, Long code) {
		return catalogService.getConsolidationPlaceByCode(idCountry,code);
	}
	
	public Long findConsolidationDayByCode(Long idCountry, Integer code) {
		return catalogService.getConsolidationDayByCode(idCountry,code);
	}
	
	public Long findOrganizationByCode(Long idCountry, Long code) {
		return catalogService.getOrganizationByCode(idCountry,code);
	}
	
	public void getNeighborhoods(CustomerTemp item) {
		if (item.getAddressTemp().getTown().getIdTown() != null) {
			List<Neighborhood> neighborhoods = neighborhoodDao
					.findByAutocomplete(item.getAddressTemp().getTown()
							.getIdTown(), item.getAddressTemp()
							.getNeighborhood().getDescription());
			if (neighborhoods != null && neighborhoods.size() > 0) {
				item.getAddressTemp()
						.getNeighborhood()
						.setIdNeighborhood(
								neighborhoods.get(0).getIdNeighborhood());
			}
		}

		if (item.getFiscalImformationTemp().getAddressTemp().getTown()
				.getIdTown() != null) {
			List<Neighborhood> neighborhoodsFisc = neighborhoodDao
					.findByAutocomplete(item.getFiscalImformationTemp()
							.getAddressTemp().getTown().getIdTown(), item
							.getFiscalImformationTemp().getAddressTemp()
							.getNeighborhood().getDescription());
			if (neighborhoodsFisc != null && neighborhoodsFisc.size() > 0) {
				item.getFiscalImformationTemp()
						.getAddressTemp()
						.getNeighborhood()
						.setIdNeighborhood(
								neighborhoodsFisc.get(0).getIdNeighborhood());
			}
		}
	}

	public void getPostalCodes(CustomerTemp item) {
		if (item.getAddressTemp().getNeighborhood().getIdNeighborhood() != null) {
			List<PostalCode> postalCodes = postalCodeDao
					.findByAutocomplete(item.getAddressTemp().getNeighborhood()
							.getIdNeighborhood(), item.getAddressTemp()
							.getPostalCode().getDescription());

			if (postalCodes != null && postalCodes.size() > 0) {
				item.getAddressTemp().getPostalCode()
						.setIdPostalCode(postalCodes.get(0).getIdPostalCode());
			} else {
				writeLog("ERROR: NOT FOUND POSTAL_CODE IN DATA BASE WITH THIS VALUE "
						+ item.getAddressTemp().getPostalCode()
								.getDescription());
				// loggers.add(this.loggerParamProcessComment(log,
				// "POSTAL_CODE",
				// "ERROR: NOT FOUND POSTAL_CODE IN DATA BASE WITH THIS VALUE "
				// +item.getAddressTemp().getPostalCode().getDescription()));
			}
		}
		if (item.getFiscalImformationTemp().getAddressTemp().getNeighborhood()
				.getIdNeighborhood() != null) {
			List<PostalCode> postalCodesFisc = postalCodeDao
					.findByAutocomplete(item.getFiscalImformationTemp()
							.getAddressTemp().getNeighborhood()
							.getIdNeighborhood(), item
							.getFiscalImformationTemp().getAddressTemp()
							.getPostalCode().getDescription());
			if (postalCodesFisc != null && postalCodesFisc.size() > 0) {
				item.getFiscalImformationTemp()
						.getAddressTemp()
						.getPostalCode()
						.setIdPostalCode(
								postalCodesFisc.get(0).getIdPostalCode());
			} else {
				writeLog("ERROR: NOT FOUND FISCAL_POSTAL_CODE IN DATA BASE WITH THIS VALUE "
						+ item.getFiscalImformationTemp().getAddressTemp()
								.getPostalCode().getDescription());
			}
		}
	}

	public void getAdressByPostalCode(CustomerTemp item) {
		if(item.getAddressTemp().getNeighborhood().getIdNeighborhood() != null && StringUtils.hasText(item.getAddressTemp().getPostalCode().getDescription())){
			List<PostalCode> postalCodes = postalCodeDao.findByAutocomplete(item
					.getAddressTemp().getNeighborhood().getIdNeighborhood(), item
					.getAddressTemp().getPostalCode().getDescription());
			if (postalCodes != null && postalCodes.size() > 0) {
				item.getAddressTemp().getPostalCode()
						.setIdPostalCode(postalCodes.get(0).getIdPostalCode());
	
				Neighborhood neighborhood = postalCodes.get(0).getNeighborhood();
				Town town = neighborhood != null && neighborhood.getTown() != null ? neighborhood
						.getTown() : null;
				State state = town != null && town.getState() != null ? town
						.getState() : null;
	
				item.getAddressTemp()
						.getNeighborhood()
						.setIdNeighborhood(
								neighborhood != null ? neighborhood
										.getIdNeighborhood() : null);
				item.getAddressTemp().getTown()
						.setIdTown(town != null ? town.getIdTown() : null);
	
				item.getAddressTemp().getState()
						.setIdState(state != null ? state.getIdState() : null);
			} else {

				writeLog("ERROR: NOT FOUND POSTAL_CODE IN DATA BASE WITH THIS VALUE "
						+ item.getAddressTemp().getPostalCode().getDescription());
			}
		}

		if(item.getFiscalImformationTemp().getAddressTemp().getNeighborhood().getIdNeighborhood() != null && 
			StringUtils.hasText(item.getFiscalImformationTemp().getAddressTemp().getPostalCode().getDescription())){
			List<PostalCode> postalCodesFisc = postalCodeDao.findByAutocomplete(
					item.getFiscalImformationTemp().getAddressTemp()
							.getNeighborhood().getIdNeighborhood(), item
							.getFiscalImformationTemp().getAddressTemp()
							.getPostalCode().getDescription());
			if (postalCodesFisc != null && postalCodesFisc.size() > 0) {
				item.getFiscalImformationTemp().getAddressTemp().getPostalCode()
						.setIdPostalCode(postalCodesFisc.get(0).getIdPostalCode());
	
				Neighborhood neighborhood = postalCodesFisc.get(0)
						.getNeighborhood();
				Town town = neighborhood != null && neighborhood.getTown() != null ? neighborhood
						.getTown() : null;
				State state = town != null && town.getState() != null ? town
						.getState() : null;
				item.getFiscalImformationTemp()
						.getAddressTemp()
						.getNeighborhood()
						.setIdNeighborhood(
								neighborhood != null ? neighborhood
										.getIdNeighborhood() : null);
				item.getFiscalImformationTemp().getAddressTemp().getTown()
						.setIdTown(town != null ? town.getIdTown() : null);
				item.getFiscalImformationTemp().getAddressTemp().getState()
						.setIdState(state != null ? state.getIdState() : null);
			} else {
				writeLog("ERROR: NOT FOUND FISCAL_POSTAL_CODE IN DATA BASE WITH THIS VALUE "
						+ item.getFiscalImformationTemp().getAddressTemp()
								.getPostalCode().getDescription());
			}
		}
	}

	public void getCustomerParent(CustomerTemp item, Long idCountry) {
		if(item.getParent().getCode() != null ){
			Customer customer = customerService.getCustomerByCodeAndCountry(item
					.getParent().getCode(), idCountry);
			if (customer != null) {
				item.getParent().setIdParent(
						customer.getHierarchyCustomer().getIdHierarchyCustomer());
			} else {
				item.setParent(null);
				// loggers.add(this.loggerParamProcessComment(log,
				// "CUSTOMER_CODE_PARENT",
				// " ERROR: NOT FOUND CUSTOMER_CODE_PARENT IN DATA BASE"));
				writeLog("ERROR: NOT FOUND CUSTOMER_CODE_PARENT IN DATA BASE");
			}
		}
	}
	
	// TODO Preguntar a Vic de esta regla del Metodo de Pago fijo
	public void getPaymentMethod(CustomerTemp item) {
		if (item.getOrgDataTemp().getPaymentMethod().getCode() != null
				&& item.getOrgDataTemp().getPaymentMethod().getCode()
						.longValue() == 1l) {
			item.getOrgDataTemp().getPaymentMethod().setIdPaymentMethod(1L);
		} else {
			item.getOrgDataTemp().getPaymentMethod().setIdPaymentMethod(2l);
		}

		if (item.getOrgDataTemp().getPaymentMethod().getCode() == null) {
			// loggers.add(this.loggerParamProcessComment(log,
			// "ID_CUSTOMER_TYPE",
			// " ERROR: NOT FOUND ID_CUSTOMER_TYPE IN DATA BASE "+item.getOrgDataTemp().getPaymentMethod().getCode()));
			writeLog("ERROR: NOT FOUND ID_CUSTOMER_TYPE IN DATA BASE "
					+ item.getOrgDataTemp().getPaymentMethod().getCode());
		}
	}

	public void concatAdressPart(CustomerTemp item) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(item.getAddressTemp().getStreet());
		buffer.append(" " + item.getAddressTemp().getExteriorNumber());
		buffer.append("  " + item.getAddressTemp().getInteriorNumber());
		buffer.append(",  "
				+ item.getAddressTemp().getPostalCode().getDescription());
		buffer.append(",  "
				+ item.getAddressTemp().getNeighborhood().getDescription());
		buffer.append(",  " + item.getAddressTemp().getTown().getDescription());
		buffer.append(",  " + item.getAddressTemp().getState().getDescription());
		item.getAddressTemp().setAddressComplete(buffer.toString());

		// loggers.add(this.loggerParamComment(log, "COMPLETE_ADDRESS", "INFO:"
		// +buffer.toString()));
		writeLog("COMPLETE_ADDRESS - INFO:" + buffer.toString());

	}

	public void getGrouperInstances(CustomerTemp item, Long idCountry) {
//		GrouperInstance instance = null;
//		Set<GrouperInstance> list = new HashSet<GrouperInstance>();
		for (GrouperInstance grouperInstance : item.getGrouperInstances()) {
			Long idGrouperInstance = catalogService.getGrouperInstancesByCodeAndCountry(idCountry, grouperInstance.getCode());
			if (idGrouperInstance != null) {
				grouperInstance.setIdGrouperInstance(idGrouperInstance);
//				list.add(grouperInstance);
			} else {
				// loggers.add(this.loggerParamProcessComment(log,
				// "GROUPER_INSTANCE",
				// "ERROR: NOT FOUND GROUPER_INSTANCE IN DATA BASE WITH THIS VALUE "
				// +
				// grouperInstance.getCode()));
				writeLog("ERROR: NOT FOUND GROUPER_INSTANCE IN DATA BASE WITH THIS VALUE "
						+ grouperInstance.getCode());
			}
		}
//		item.setGrouperInstances(list);
	}

	public Long getPriceListByCode(Long idCountry, Long code, Long idOrganization) {
		if(code == null){
			return code;
		}
		return catalogService.getPriceListByCode(idCountry, code, idOrganization);
	}
	
	public Long getCustomerTypeByCode(Long idCountry, Long code) {
		if(code == null){
			return code;
		}
		return catalogService.getCustomerTypeByCode(idCountry, code);
	}
	public void fillNullFieldsWithDefaulValues(CustomerTemp item) {
		
		List<FieldVO> fields = new ArrayList<FieldVO>();
		boolean isCredit = Boolean.FALSE;
		
		if(item.getLeveltype().getIdLeveltype() != null){
			fields = catalogService.findAllFieldsByLevelType(item.getLeveltype().getIdLeveltype());
		}
		
		if(item.getOrgDataTemp().getPaymentMethod().getIdPaymentMethod() != null){
			isCredit = isCredit(item.getOrgDataTemp().getPaymentMethod().getIdPaymentMethod());
		}
		
		fillDefaulCustomerValues(item, fields, isCredit);

	}
	
	private boolean isCredit(Long idPaymentMethod){
		
		List<PaymentMethod> paymentMethods = catalogService.getAllPaymentMethod();
		
		if(paymentMethods != null){
			for(PaymentMethod p: paymentMethods){
				if(p.getIdPaymentMethod().equals(idPaymentMethod)){
					if (p.getDescription().equals(PAYMENT_METHOD_CREDIT)) {
						return true;
					}
				}
			}
		}
		return false;
	}

//	private void fillDefaulCustomerValues(CustomerTemp customer,List<FieldVO> fieldVOs, Long idPaymentMethod) {
	private void fillDefaulCustomerValues(CustomerTemp customer,List<FieldVO> fields, boolean isCredit) {

		for (FieldVO vo : fields) {

			if (!vo.getAttributeName().startsWith("addressTemp")
					&& !vo.getAttributeName().startsWith(
							"fiscalImformationTemp")
					&& !vo.getAttributeName().startsWith("orgDataTemp")
					&& !vo.getAttributeName().startsWith("classification")
					&& !vo.getAttributeName().startsWith("category")
					&& !vo.getAttributeName().startsWith("customerType")
					&& !vo.getAttributeName().startsWith("priceList")) {
				java.lang.reflect.Field[] fieldsCust = customer.getClass()
						.getDeclaredFields();
				if (isCredit) {
					if (StringUtils.hasText(vo.getDefaultValueCredit())) {
						fillFlexFieldAttributes(customer, fieldsCust,
								vo.getAttributeName(),
								vo.getDefaultValueCredit());
					} else if (StringUtils.hasText(vo.getDefaultValueCash())) {
						fillFlexFieldAttributes(customer, fieldsCust,
								vo.getAttributeName(), vo.getDefaultValueCash());
					}
				}
			} else if (vo.getAttributeName().startsWith("classification")) {
				fillClassification(customer, vo);
			} else if (vo.getAttributeName().startsWith("category")) {
				fillCategory(customer, vo);
			} else if (vo.getAttributeName().startsWith("priceList")) {
				logger.write(getClass(), "se cambia Lista de precios");
				//fillPriceList(customer, vo);
			} else if (vo.getAttributeName().startsWith("customerType")) {
				fillCustomerType(customer, vo);
			} else if (vo.getAttributeName().startsWith("addressTemp")) {
				fillAddresss(customer, vo);
			} else if (vo.getAttributeName()
					.startsWith("fiscalImformationTemp")) {
				fillFiscalInfo(customer, vo);
			} else if (vo.getAttributeName().startsWith("orgDataTemp")) {
				fillOrganizationData(customer, vo);
			}

		}
	}
	
	private void fillClassification(CustomerTemp customer, FieldVO vo) {
		java.lang.reflect.Field[] fieldsClassification = customer.getClassification().getClass().getDeclaredFields();
		if(StringUtils.hasText(vo.getDefaultValueCredit())){
			fillFlexFieldAttributes(customer.getClassification(), fieldsClassification, vo.getAttributeName().replace("classification.", ""), vo.getDefaultValueCredit());
		}else if(StringUtils.hasText(vo.getDefaultValueCash())){
			fillFlexFieldAttributes(customer.getClassification(), fieldsClassification, vo.getAttributeName().replace("classification.", ""), vo.getDefaultValueCash());
		}
	}
	
	private void fillCategory(CustomerTemp customer, FieldVO vo) {
		java.lang.reflect.Field[] fieldsCategory = customer.getCategory().getClass().getDeclaredFields();
		if(StringUtils.hasText(vo.getDefaultValueCredit())){
			fillFlexFieldAttributes(customer.getCategory(), fieldsCategory, vo.getAttributeName().replace("category.", ""), vo.getDefaultValueCredit());
		}else if(StringUtils.hasText(vo.getDefaultValueCash())){
			fillFlexFieldAttributes(customer.getCategory(), fieldsCategory, vo.getAttributeName().replace("category.", ""), vo.getDefaultValueCash());
		}
	}	
	
	private void fillPriceList(CustomerOrgDataTemp customerOrgDataTemp, FieldVO vo) {
		// TODO: Preguntar si todos los clientes deben tener una lista de precios. Especialmente los Grupos de Cadenas
		if(customerOrgDataTemp.getPriceList() != null){
			java.lang.reflect.Field[] fieldsPriceList = customerOrgDataTemp.getPriceList().getClass().getDeclaredFields();
			if(StringUtils.hasText(vo.getDefaultValueCredit())){
				fillFlexFieldAttributes(customerOrgDataTemp.getPriceList(), fieldsPriceList, vo.getAttributeName().replace("priceList.", ""), vo.getDefaultValueCredit());
			}else if(StringUtils.hasText(vo.getDefaultValueCash())){
				fillFlexFieldAttributes(customerOrgDataTemp.getPriceList(), fieldsPriceList, vo.getAttributeName().replace("priceList.", ""), vo.getDefaultValueCash());
			}
		}
	}	
	
	private void fillCustomerType(CustomerTemp customer, FieldVO vo) {
		java.lang.reflect.Field[] fieldsCustomerType=null;
		if(StringUtils.hasText(vo.getDefaultValueCredit())){
			if(customer.getCustomerType()==null){
				customer.setCustomerType(new CustomerType());
			}
			fieldsCustomerType = customer.getCustomerType().getClass().getDeclaredFields();
			fillFlexFieldAttributes(customer.getCustomerType(), fieldsCustomerType, vo.getAttributeName().replace("customerType.", ""), vo.getDefaultValueCredit());
		}else if(StringUtils.hasText(vo.getDefaultValueCash())){
			if(customer.getCustomerType()==null){
				customer.setCustomerType(new CustomerType());
			}
			fieldsCustomerType = customer.getCustomerType().getClass().getDeclaredFields();
			fillFlexFieldAttributes(customer.getCustomerType(), fieldsCustomerType, vo.getAttributeName().replace("customerType.", ""), vo.getDefaultValueCash());
		}
	}	
	
	private void fillAddresss(CustomerTemp customer, FieldVO vo) {
		java.lang.reflect.Field[] fieldsAddress = customer.getAddressTemp().getClass().getDeclaredFields();
		if(StringUtils.hasText(vo.getDefaultValueCredit())){
			fillFlexFieldAttributes(customer.getAddressTemp(), fieldsAddress, vo.getAttributeName().replace("addressTemp.", ""), vo.getDefaultValueCredit());
		}else if(StringUtils.hasText(vo.getDefaultValueCash())){
			fillFlexFieldAttributes(customer.getAddressTemp(), fieldsAddress, vo.getAttributeName().replace("addressTemp.", ""), vo.getDefaultValueCash());
		}
	}
	
	private void fillFiscalInfo(CustomerTemp customer, FieldVO vo) {
		java.lang.reflect.Field[] fieldsFiscalInfo = customer.getFiscalImformationTemp().getClass().getDeclaredFields();

		if(!vo.getAttributeName().startsWith("fiscalImformationTemp.addressTemp")){
			if(StringUtils.hasText(vo.getDefaultValueCredit())){
				fillFlexFieldAttributes(customer.getFiscalImformationTemp(), fieldsFiscalInfo, vo.getAttributeName().replace("fiscalImformationTemp.", ""), vo.getDefaultValueCredit());
			}else if(StringUtils.hasText(vo.getDefaultValueCash())){
				fillFlexFieldAttributes(customer.getFiscalImformationTemp(), fieldsFiscalInfo, vo.getAttributeName().replace("fiscalImformationTemp.", ""), vo.getDefaultValueCash());
			}
		}else{
			java.lang.reflect.Field[] fieldsAddress = customer.getFiscalImformationTemp().getAddressTemp().getClass().getDeclaredFields();
			if(StringUtils.hasText(vo.getDefaultValueCredit())){
				fillFlexFieldAttributes(customer.getFiscalImformationTemp().getAddressTemp(), fieldsAddress, vo.getAttributeName().replace("fiscalImformationTemp.addressTemp.", ""), vo.getDefaultValueCredit());
			}else if(StringUtils.hasText(vo.getDefaultValueCash())){
				fillFlexFieldAttributes(customer.getFiscalImformationTemp().getAddressTemp(), fieldsAddress, vo.getAttributeName().replace("fiscalImformationTemp.addressTemp.", ""), vo.getDefaultValueCash());
			}
		}
	}	
	
	private void fillOrganizationData(CustomerTemp customer, FieldVO vo) {

		if(!vo.getAttributeName().startsWith("orgDataTemp.paymentTerms")){
			java.lang.reflect.Field[] fieldsOrganizationData = customer.getOrgDataTemp().getClass().getDeclaredFields();
			if(StringUtils.hasText(vo.getDefaultValueCredit())){
				fillFlexFieldAttributes(customer.getOrgDataTemp(), fieldsOrganizationData, vo.getAttributeName().replace("orgDataTemp.", ""), vo.getDefaultValueCredit());
			}else if(StringUtils.hasText(vo.getDefaultValueCash())){
				fillFlexFieldAttributes(customer.getOrgDataTemp(), fieldsOrganizationData, vo.getAttributeName().replace("orgDataTemp.", ""), vo.getDefaultValueCash());
			}
		}else{
			java.lang.reflect.Field[] fieldsPaymentTerms = null;
			if(StringUtils.hasText(vo.getDefaultValueCredit())){
				fieldsPaymentTerms = getPaymentTermFields(customer);

				fillFlexFieldAttributes(customer.getOrgDataTemp().getPaymentTerms(), fieldsPaymentTerms, vo.getAttributeName().replace("orgDataTemp.paymentTerms.", ""), vo.getDefaultValueCredit());
			}else if(StringUtils.hasText(vo.getDefaultValueCash())){
				fieldsPaymentTerms = getPaymentTermFields(customer);
				fillFlexFieldAttributes(customer.getOrgDataTemp().getPaymentTerms(), fieldsPaymentTerms, vo.getAttributeName().replace("orgDataTemp.paymentTerms.", ""), vo.getDefaultValueCash());
			}
		}
	}	
	
	private java.lang.reflect.Field[] getPaymentTermFields(CustomerTemp customer) {
		java.lang.reflect.Field[] fieldsPaymentTerms;
		if(customer.getOrgDataTemp().getPaymentTerms()!=null){
			fieldsPaymentTerms=customer.getOrgDataTemp().getPaymentTerms().getClass().getDeclaredFields();
		}else{
			customer.getOrgDataTemp().setPaymentTerms(new PaymentTerms());
			fieldsPaymentTerms=customer.getOrgDataTemp().getPaymentTerms().getClass().getDeclaredFields();
		}
		return fieldsPaymentTerms;
	}
	
	public boolean getFolioValuesInput(String value) {
		
		if(StringUtils.hasText(value)){
			if (Long.valueOf(value).longValue() == 1) {
				return true;
			}
		}
		
		return false;
	}
	
	public Customer getCustomerByCodeAndCountry(Long code, Long idCountry){
		return customerService.getCustomerByCodeAndCountry(code,idCountry);
	}
	
	public List<HierarchyCustomer> getAssociatedCustomer(Long code, Long idCountry){
		List<HierarchyCustomer> hierarchyCustomerList = new ArrayList<HierarchyCustomer>();
		int index = 1;
		
		HierarchyCustomer hierarchyCustomer = hierarchyCustomerService.getHCustomerByCodeAndCountry(code, idCountry);
		List<HierarchyCustomer> hierarchyCustomers = hierarchyCustomerService.getChildrenByHierarchyCustomer(hierarchyCustomer.getIdHierarchyCustomer());
		
		for(HierarchyCustomer hc : hierarchyCustomers){
			if(index == 1){
				hierarchyCustomerList.add(index++, hierarchyCustomer);
			}else{
				hierarchyCustomerList.add(index++, hc);
			}
		}
		
		return hierarchyCustomerList;
	}
	
	private void writeLog(String message){
    	logger.write(getClass(), message);
    }
	*/
}
