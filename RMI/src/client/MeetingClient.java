package client;


import java.rmi.Naming;
import java.rmi.RemoteException;

import rface.MeetingInterface;
import server.MeetingServer;

  /*
   * �ͻ��˵����
   * @author ʷ�λ�
   * @date 2018-21-12
  */
public class MeetingClient {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("�����������: [hostAddress] [portNumber] 'login'or'register' [username] [password]");
            return;
        }
        try {
            String url = "//" + args[0] + ":" + args[1] + "/MeetingServer";
            MeetingInterface service = (MeetingInterface) Naming.lookup(url);
            new Handler(service).process(args);
        } catch (RemoteException rex) {
            System.err.println("Error in lookup: " + rex.toString());
            System.exit(1);
        } catch (java.net.MalformedURLException me) {
            System.err.println("Malformed URL: " + me.toString());
            System.exit(1);
        } catch (java.rmi.NotBoundException ne) {
            System.err.println("NotBound: " + ne.toString());
            System.exit(1);
        }
    }
}

