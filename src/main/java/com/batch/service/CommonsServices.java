package com.batch.service;


import com.batch.util.Utils.BATCH_CHUNK;
import com.batch.util.Utils.BATCH_PROCESS;
import com.batch.util.Utils.BATCH_WRITE;

public class CommonsServices{
	/*
	@Autowired
	MassiveLoadService massivmLoadService;
	@Autowired
	MassiveOperationCountryService massiveOperationCountryService;
	*/
	private Long idMassiveLoad;
	/*
	private static CommonsServices instance = new CommonsServices();
	private static Map<Long,MassiveLoad> mapMassive = new HashMap<Long,MassiveLoad>();
	private static Map<Long,Set<MassiveOperationCountry>> mapOperation = new HashMap<Long, Set<MassiveOperationCountry>>();

	protected CommonsServices(){
		if (instance == null){
			getInstance();
		}
	}
	
	private static CommonsServices getInstance(){
		return instance;
	}
	*/
	public void setIdMassiveLoad(Long idMassiveLoad) {
		this.idMassiveLoad = idMassiveLoad;
	}
	
	public Long getIdMassiveLoad() {
		return this.idMassiveLoad;
	}
	/*
	public MassiveLoad getMassiveLoad(Long idMassiveLoad) {
		MassiveLoad mass = getMassive(idMassiveLoad);
		if(mass == null){
			mass = massivmLoadService.getMassiveLoad(idMassiveLoad);
			addMassive(idMassiveLoad,mass);
		}
		return mass;
	}
	
	private MassiveLoad getMassive(Long idMassiveLoad){
		if(mapMassive.containsKey(idMassiveLoad)){
			return mapMassive.get(idMassiveLoad);
		}
		return null;
	}
	
	private void addMassive(Long idMassiveLoad, MassiveLoad massiveLoad){
		mapMassive.put(idMassiveLoad, massiveLoad);
	}
	*/
	public String getBean(String bean, String sheet){
		
		switch(BATCH_CHUNK.valueOf(sheet)){
					case PROCESSOR:{
							switch(BATCH_PROCESS.valueOf(bean)){
								case CUSTOMER:
									return BATCH_PROCESS.CUSTOMER.getValue();
								case GLN:
									return BATCH_PROCESS.GLN.getValue();
							}
					}break;
					case WRITER:{
						switch(BATCH_WRITE.valueOf(bean)){
							case CUSTOMER:
								return BATCH_WRITE.CUSTOMER.getValue();
							case GLN:
								return BATCH_WRITE.GLN.getValue();
						}
					}break;
		}
		
		return null; 
	}
	/*
	private MassiveOperationCountry getMassiveOperation(Long idMassiveLoad){
		
		MassiveLoad mass = getMassiveLoad(idMassiveLoad);
		Iterator<MassiveOperationCountry> iter = getMassiveOperations(idMassiveLoad).iterator();
		while(iter.hasNext()){
			MassiveOperationCountry item = (MassiveOperationCountry)iter.next();
			if(item.getOperationMassiveLoad().getIdOperation() == mass.getOperationMassiveLoad().getIdOperation()){
				return item;
			}
		}
		
		return null;
	}
	
	private Set<MassiveOperationCountry> getMassiveOperations(Long idMassiveLoad){
		
		MassiveLoad mass = getMassiveLoad(idMassiveLoad);
		Set<MassiveOperationCountry> massOperations = getOperations(mass.getCountry().getIdCountry(), mass.getOperationMassiveLoad().getIdOperation());

		if(massOperations == null){
			MassiveOperationCountry massOperation = massiveOperationCountryService.getLayouts(mass.getCountry().getIdCountry(), mass.getOperationMassiveLoad().getIdOperation());
			massOperations = addOperation(mass.getCountry().getIdCountry(), massOperation);
		}
		
		return massOperations;
	} 
	
	private Set<MassiveOperationCountry> getOperations(Long idCountry, Long idOperation){
		if(mapOperation.containsKey(idCountry)){
			Iterator<MassiveOperationCountry> iter = mapOperation.get(idCountry).iterator();
			while(iter.hasNext()){
				MassiveOperationCountry item = (MassiveOperationCountry)iter.next();
				if(item.getOperationMassiveLoad().getIdOperation() == idOperation){
					return mapOperation.get(idCountry);
				}
			}
		}
		return null;
	} 
	
	private Set<MassiveOperationCountry> addOperation(Long idCountry, MassiveOperationCountry operation){
		if(mapOperation.containsKey(idCountry)){
			mapOperation.get(idCountry).add(operation);	
		}else{
			Set<MassiveOperationCountry> operations = new HashSet<MassiveOperationCountry>();
			operations.add(operation);
			mapOperation.put(idCountry, operations);
		}
		
		return mapOperation.get(idCountry);
	}
	*/
}
