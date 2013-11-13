package core;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
	public DBManager getManager() throws RemoteException;
}
