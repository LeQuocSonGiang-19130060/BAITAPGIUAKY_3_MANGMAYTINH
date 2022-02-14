package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{


	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket = new ServerSocket(3000);
		while(true){
			Socket socket = serverSocket.accept();
			OneConnect connect = new OneConnect(socket);
			Thread th = new Thread(connect);
			th.start();
		}
	}



}
