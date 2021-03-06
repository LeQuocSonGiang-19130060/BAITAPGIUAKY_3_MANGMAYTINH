package server;

import java.io.Serializable;

public class Product implements Serializable {

	private String id;
	private String name;
	private double price;

	public Product(String id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Product() {
	}

	public String getID() {
		return this.id;
	}

	public void setID(String newID) {
		id = newID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String toString() {
		return "ID: " + id + "\tName: " + name + "\tPrice: " + price;
	}
}
