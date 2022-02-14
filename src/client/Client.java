package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import server.Product;

public class Client {

	BufferedReader netIn;
	PrintWriter netOut;
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public Client() throws Exception {
		Socket socket = new Socket("localhost", 3000);
		netIn = new BufferedReader(new InputStreamReader(System.in));
		netOut = new PrintWriter(socket.getOutputStream(), true);
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());

		System.out.println("Enter request.....");
		String line;
		loop: while (true) {
			line = netIn.readLine();
			if (line.equalsIgnoreCase("QUIT"))
				break loop;
			try {
				requestAnal(line);
				switch (command) {
				case "FINDBYID":
					send(command, param);
					receive();
					break;
				case "FINDBYPRICE":
					receiveList();
					break;
				case "FINDBYNAME":
					send(command, param);
					receiveList();
					break;
				case "DELETEBYID":
					send(command, param);
					receiveDel();
					break;
				case "SAVEPRODUCT":
					send(command, param, param2, doubleValue);
					receiveIns();
					break;
				case "EDITPRICE":
					send(command, param, doubleValue);
					receiveUpdate();
					break;
				case "EDITNAME":
					send(command, param, param2);
					receiveUpdate();
					break;
				}

			} catch (MyException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
	}

	public void send(String command, String param) throws IOException {
		netOut.println("aa");
		netOut.println(command);
		netOut.println(param);
	}

	public void send(String command, String id, String name, double price) {
		netOut.println("aa");
		netOut.println(command);
		netOut.println(id);
		netOut.println(name);
		netOut.println(price);
	}

	public void send(String cmd, String id, double price) {
		netOut.println("aa");
		netOut.println(cmd);
		netOut.println(id);
		netOut.println(price);
	}

	public void send(String cmd, String id, String name) {
		netOut.println("aa");
		netOut.println(cmd);
		netOut.println(id);
		netOut.println(name);
	}

	String command, param, param2;
	double doubleValue;

	public void requestAnal(String line) throws MyException {
		try {
			StringTokenizer stk = new StringTokenizer(line);
			command = stk.nextToken().trim().toUpperCase();
			param = stk.nextToken();
			switch (command) {
			case "FINDBYNAME":
				break;
			case "FINDBYID", "DELETEBYID":
				break;
			case "FINDBYPRICE":
				try {
					doubleValue = Double.parseDouble(param);
					break;
				} catch (NumberFormatException e) {
					throw new MyException("Tham số giá tiền không hợp lệ");
				}
			case "SAVEPRODUCT":
				try {
					param2 = stk.nextToken();
					doubleValue = Double.parseDouble(stk.nextToken());
					break;
				} catch (NumberFormatException e) {
					throw new MyException("Tham số không hợp lệ");
				}
			case "EDITPRICE":
				try {
					doubleValue = Double.parseDouble(stk.nextToken());
					break;
				} catch (NumberFormatException e) {
					throw new MyException("Tham số không hợp lệ");
				}
			case "EDITNAME":
				param2 = stk.nextToken();
				break;
			default:
				throw new MyException("Lenh khong hop le");
			}
		} catch (NoSuchElementException e) {
			throw new MyException("Cú pháp không hợp lệ|");
		}
	}

	public void receive() throws Exception {
		Product result = (Product) ois.readObject();
		if (result != null) {
			System.out.println(result.toString());
		} else {
			System.out.println("Not FOUND!!!");
		}
	}

	public void receiveList() throws Exception {
		List<Product> listResult = (List<Product>) ois.readObject();
		if (listResult != null && listResult.size() > 0) {
			for (Product p : listResult)
				System.out.println(p.toString());
		} else {
			System.out.println("Not FOUND!!!");
		}
	}

	public void receiveDel() throws IOException {
		boolean result = ois.readBoolean();
		System.out.println(result);
		if (result) {
			System.out.println("Delete successfull");
		} else {
			System.out.println("Error! Product is not exist!");
		}
	}

	public void receiveIns() throws IOException {
		boolean result = ois.readBoolean();
		System.out.println(result);
		if (result) {
			System.out.println("Insert successfull");
		} else {
			System.out.println("Error! Insert is Failed!");
		}
	}

	public void receiveUpdate() throws IOException {
		boolean result = ois.readBoolean();
		System.out.println(result);
		if (result) {
			System.out.println("Update successfull");
		} else {
			System.out.println("Error! Update is Failed!");
		}
	}

	public static void main(String[] args) {
		try {
			new Client();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
