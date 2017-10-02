package com.bimbo.cuc.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:com/batch/spring/applicationContext.xml",
		"classpath:com/batch/spring/batch-definition.xml",
		"classpath:com/batch/spring/batch-reader.xml",
		"classpath:com/batch/spring/batch-process.xml",
		"classpath:com/batch/spring/batch-writer.xml"
})
public class ReadExcel {

	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	private ApplicationContext context;
	
	Job importCustomer;

	private JobParametersConverter jobParametersConverter = new DefaultJobParametersConverter();

	@Test
	public void readExcel1Test() throws Exception{
		importCustomer=(Job)context.getBean("importCustomer");
		String[] parameters = { getIdMassiveLoadParamter(1L),getResourceParam("CUS_8001_1.xlsx")};
		JobParameters jobParameters = jobParametersConverter.getJobParameters(StringUtils.splitArrayElementsIntoProperties(parameters, "="));
		JobExecution execution =jobLauncher.run(importCustomer, jobParameters);
		System.out.println(execution.getStatus().isRunning());
	}
	 
	private String getResourceParam(String filaName) {
		String resource = "resource="+"file:C:/icac/archivos/batch_masivas/in/";
//		return "resource="+parametersDao.getValueFor(CUC_MASSIVE_LOAD_FILESYSTEM);
        return resource += filaName;
	}
	
	private String getIdMassiveLoadParamter(Long idMassiveLoad) {
		return "idMassiveLoad=" + idMassiveLoad;
	}
	
}
