package web_service;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import core.DBManager;

@WebService
@SOAPBinding(style=Style.DOCUMENT)
public interface IDBWebService {

	@WebMethod
	public void projectTable(ArrayList<Integer> numbersOfcolumns, String tableName, String dbName);
	
	@WebMethod
	public void joinTables(String dbName, String targetTableName, String destTableName, String colName);
	
	@WebMethod
	public void setDBManager(DBManager dbManager);
	
	@WebMethod
	public DBManager getDBManager();
}
