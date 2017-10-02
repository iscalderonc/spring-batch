package com.batch.excel.model;

import java.util.Collections;
import java.util.List;

public class Sheet {
	
	private String name;
	private List<Cell> cell;
	private long currentRow;
	private long numberOfRows;
	
	public Sheet(){		
	}
	
	public Sheet(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Cell> getCell() {
		return cell;
	}
	public void setCell(List<Cell> cell) {
		this.cell = cell;
	}
	
	public Cell getCellQueryItem(String nameCell){
		return getItemCell(nameCell,false);
	}
	
	public Cell getCell(String nameCell){
		return getItemCell(nameCell,true);
	}
	
	private Cell getItemCell(String nameCell, boolean remove){
		Cell c = null;
		Collections.sort(this.cell);
		for(Cell cl : this.cell){
			if(cl.getName().compareTo(nameCell) == 0 || cl.getName().compareTo(nameCell) > 0){
				if(cl.getName().compareTo(nameCell) == 0){
					c = cl;
				}
				break;
			}
		}
		// remove element
		if(remove){
			this.cell.remove(c);
		}
		return c;
	}

	public long getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(long currentRow) {
		this.currentRow = currentRow;
	}

	public long getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(long numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
}
