package gui;

import javax.swing.JTable;

import core.Table;

public class TableConverter {

	public static JTable convertTableToJTable(Table table) {
		Object[] columnNames = table.get_columnNames().toArray();
		Object[][] tableContent = new Object[table.get_WIDTH()][];
		for(int i = 0; i < table.get_WIDTH(); i++) {
			for(int j = 0; j < table.get_HEIGHT(); j++) {
				tableContent[i][j] = table.getCell(i, j);
			}
		}
		
		return new JTable(tableContent, columnNames);
	}
}
