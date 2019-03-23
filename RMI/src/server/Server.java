package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import rface.MeetingInterface;

/*
 * 启动服务器
 * @author 史嘉辉
 * @date 2018-21-12
 */
public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(8000);
            MeetingInterface meetingServer = new MeetingServer();
            Naming.rebind("//localhost:8000/MeetingServer", meetingServer);
        } catch (MalformedURLException me) {
            System.out.println("Malformed URL: " + me.toString());
        } catch (RemoteException re) {
            System.out.println("Remote Exception: " + re.toString());
        }
    }
}
