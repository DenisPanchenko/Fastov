package web_service;

import javax.xml.ws.Endpoint;


public class Publisher {

public static void main(String[] args) {
                            
        Endpoint.publish("http://localhost:8080/db_service", new DBWebServiceImpl());
        
        System.out.println("Ready!");
                   
    }
}
