package client;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import UserThrow.LoginFailedException;
import rface.MessageInterface;
import server.MessageServer;

  /**
   *  �ͻ��˵����
   * @author ʷ�λ�
   * @date 2018-21-28
  */
public class Client {
    public static void main(String[] args) throws IOException, LoginFailedException {
        if (args.length < 2) {
            System.out.println("�����������: [localhost] [8000] ");
            return;
        }
        try {
            String url = "//" + args[0] + ":" + args[1] + "/MessageServer";
            MessageInterface service = (MessageInterface) Naming.lookup(url);
            new Handler(service).start();
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
