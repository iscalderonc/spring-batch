package com.batch.writer;

public interface IWrite<T> {
	
	public T writeData(Object item, Long idMassiveLoad);

}
