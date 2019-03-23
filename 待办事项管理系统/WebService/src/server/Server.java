package server;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
/**
 * 启动WebService的服务器
 *  @author 史嘉辉
 *	@date 2019-1-4
 */
@WebService
public class Server {
	// publish MatterWebService
	public static void main(String[] args){
		Endpoint.publish("http://127.0.0.1:8888/WebServices/Service", new ServiceImplement());
	}
}
