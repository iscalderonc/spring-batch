package com.batch.process;

import com.batch.excel.model.Sheet;

public interface IProcessor<T> {
	
	public T setData(Sheet item, Long idMassiveLoad);
	
}
