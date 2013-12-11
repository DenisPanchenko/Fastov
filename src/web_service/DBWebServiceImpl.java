package web_service;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import core.DBManager;

@WebService(endpointInterface = "web_service.IDBWebService")
@SOAPBinding(style=Style.DOCUMENT)
public class DBWebServiceImpl implements IDBWebService {

	private DBManager dbManager;
	
	public DBWebServiceImpl() {
	}
	
	@Override
	public void projectTable(ArrayList<Integer> numbersOfcolumns, String tableName,
			String dbName) {
		dbManager.projectTable(numbersOfcolumns, tableName, dbName);
		
	}

	@Override
	public void joinTables(String dbName, String targetTableName,
			String destTableName, String colName) {
		dbManager.tableJoin(dbName, targetTableName, destTableName, colName);
	}

	@Override
	public void setDBManager(DBManager dbManager) {
		this.dbManager = dbManager;
		
	}

	@Override
	public DBManager getDBManager() {
		return dbManager;
	}
}
