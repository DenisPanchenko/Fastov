package gui;

import javax.swing.JTable;

import core.Table;

public class TableConverter {

	public static JTable convertTableToJTable(Table table) {
		Object[] columnNames = table.get_columnNames().toArray();
		Integer width = table.get_WIDTH();
		Integer height = table.get_HEIGHT();
		Object[][] tableContent = new Object[height][width];
		for(int i = 0; i < table.get_HEIGHT(); i++)
			for(int j = 0; j < table.get_WIDTH(); j++) 
				tableContent[i][j] = table.getCell(i, j).toString();
		return new JTable(tableContent, columnNames);
	}
}
