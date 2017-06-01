package com.example.disruptor.bhz.multi;


public class Order {  
	
	private String id;//ID  
	private String name;
	private double price;//金额  
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Order{");
		sb.append("id='").append(id).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", price=").append(price);
		sb.append('}');
		return sb.toString();
	}
}