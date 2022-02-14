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
		String lineParam2;
		String lineParam3;
		DAO dao = new DAO();
		try {
			while (true) {
				netIn.readLine();
				lineCommand = netIn.readLine();
				if (lineCommand.equalsIgnoreCase("QUIT")) {
					break;
				}
				lineParam = netIn.readLine();
				switch (lineCommand) {
				case "FINDBYNAME":
					ArrayList<Product> result = (ArrayList<Product>) dao.getByName(lineParam);
					send(result);
					break;
				case "FINDBYID":
					Product result1 = dao.getByID(lineParam);
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
				case "SAVEPRODUCT":
					lineParam2 = netIn.readLine();
					lineParam3 = netIn.readLine();
					Product p = new Product();
					p.setID(lineParam);
					p.setName(lineParam2);
					p.setPrice(Double.parseDouble(lineParam3));
					boolean success2 = dao.insertProduct(p);
					send(success2);
					break;
				case "EDITPRICE":
					lineParam2 = netIn.readLine();
					boolean success3 = dao.updatePrice(lineParam, Double.parseDouble(lineParam2));
					send(success3);
					break;
				case "EDITNAME":
					lineParam2 = netIn.readLine();
					boolean success4 = dao.updateName(lineParam, lineParam2);
					send(success4);
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
