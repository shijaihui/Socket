package ex01;

public class Cd {

	public Cd() {
		// TODO Auto-generated constructor stub
	}
	//¥¶¿Ìcd/get√¸¡Ó
	public String cdcmd(String cmd) {
		String filename = null;
		if(cmd.equals("get") || cmd.equals("cd")) {
			return "null";	
		}
		else {
			String[] tt=cmd.split("\\s+");
			filename=tt[tt.length-1];
			return filename;
		}
		
			
	}
}
