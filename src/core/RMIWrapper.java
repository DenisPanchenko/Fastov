package core;

import java.awt.Dimension;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class RMIWrapper extends UnicastRemoteObject implements RMIInterface{
	private DBManager _dbManager;
	private Registry _registery;
	public RMIWrapper() throws RemoteException
	{
		super();
		_dbManager = new DBManager();
		_registery = LocateRegistry.createRegistry(12345);
		_registery.rebind("RMIWrapper", this);
	}
	@Override
	public DBManager getManager() throws RemoteException
	{
		return _dbManager;
	}
	
	public static void main(String [] args)
		{
			try
			{
				RMIWrapper rmi = new RMIWrapper();
				
				//Naming.rebind("RMIWrapper", rmi);
				
				JFrame window = new JFrame("Manager");
				JLabel label = new JLabel("Launched");
				window.setMinimumSize(new Dimension(100,100));
				window.add(label);
				window.pack();
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.show();
				window.setLocationRelativeTo(null);
			} catch(Exception e) {
				e.printStackTrace();
			}
			  
		}
	
}
