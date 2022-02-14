package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class OneConnect extends Thread {

	private Socket socket;
	private BufferedReader netIn;
	private ObjectOutputStream netOut;

	public OneConnect(Socket socket) {
		this.socket = socket;
		try {
			netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			netOut = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {

		String lineCommand;
		String lineParam;
		DAO dao = new DAO();
		try {
			while (true) {
				System.out.println("1:  "+ netIn.readLine());
				lineCommand = netIn.readLine();
				System.out.println("hello"+lineCommand);
				if (lineCommand.equalsIgnoreCase("QUIT")) {
					System.out.println("break");
					break;}
				lineParam = netIn.readLine();
				System.out.println("he"+lineParam);
				switch (lineCommand) {
				case "FINDBYNAME":
					ArrayList<Product> result = (ArrayList<Product>) dao.getByName(lineParam);
					System.out.println(result);
					send(result);
					break;
				case "FINDBYID":
					System.out.println("id");
					Product result1 = dao.getByID(lineParam);
					System.out.println(result1);
					send(result1);
					break;
				case "FINDBYPRICE":
					ArrayList<Product> result2 = (ArrayList<Product>) dao.getByPrice(Double.parseDouble(lineParam));
					send(result2);
					break;
				case "DELETEBYID":
					boolean success = dao.deleteProduct(lineParam);
					send(success);
					break;
				}
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(List<Product> list) throws IOException {
		netOut.writeObject(list);
		netOut.flush();
	}

	public void send(Product p) throws IOException {
		netOut.writeObject(p);
		netOut.flush();
	}

	public void send(boolean s) throws IOException {
		netOut.writeBoolean(s);
		netOut.flush();
	}

}
