package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class OneConnect implements Runnable{

	private Socket socket;
	private BufferedReader netIn;
	private PrintWriter netOut;

	public OneConnect(Socket socket){
		this.socket = socket;
		try{
		netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		netOut = new PrintWriter(socket.getOutputStream(),true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public void run(){
		netOut.println("hello");
		String line;
		try{
			while(true){
				line = netIn.readLine();
				if(line.equalsIgnoreCase("QUIT")) break;
				netOut.println("echo" + line);
			}
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
