package web_client;

import gui.MainForm;
import gui.MainFormMng;

import java.awt.EventQueue;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import web_service.IDBWebService;

public class WebServiceClient {
	
	public static void main(String[] args) {
        
        try {
                       
            URL url = new URL("http://localhost:8080/db_service?wsdl");
            
            QName name = new QName("http://web_service/", "DBWebServiceImplService");
            
            Service service = Service.create(url, name);
            
            final IDBWebService dbService = service.getPort(IDBWebService.class);
            
            MainFormMng.setDbService(dbService);
            EventQueue.invokeLater(new Runnable() {
    			public void run() {
    				try {
    					MainForm dbBaseMainForm = new MainForm();
    					dbService.setDBManager(dbBaseMainForm.getDBManager());
    					dbBaseMainForm.setVisible(true);
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		});
                        
        } catch (Exception e) {
            
            //System.out.println(e.getMessage());
        }
    }
}
