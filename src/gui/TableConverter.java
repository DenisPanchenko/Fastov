package gui;

import javax.swing.JTable;

import core.Table;

public class TableConverter {

	public static JTable convertTableToJTable(Table table) {
		Object[] columnNames = table.getColumnNames().toArray();
		Integer width = table.getWidth();
		Integer height = table.getHeight();
		Object[][] tableContent = new Object[height][width];
		for(int i = 0; i < table.getHeight(); i++)
			for(int j = 0; j < table.getWidth(); j++) 
				tableContent[i][j] = table.getCell(i, j).toString();
		return new JTable(tableContent, columnNames);
	}
}
