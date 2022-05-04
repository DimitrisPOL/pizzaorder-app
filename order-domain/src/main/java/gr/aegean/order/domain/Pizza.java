package gr.aegean.order.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pizza {

	public String pizzaId;
	public String orderId;
	public String pizzaName;
	public String crustType;
	public int numberfPizzas;
	public Pizza() {}
	
	public int getnumberfPizzas() {
		return this.numberfPizzas;
	}
	public String getcrustType() {
		return this.crustType;
	}

	public String getpizzaName() {
		return this.pizzaName;
	}

	public String getorderId() {
		return this.orderId;
	}

	public String getpizzaId() {
		return this.pizzaId;
	}
	
	public void setnumberfPizzas(int numberfPizzas) {
		this.numberfPizzas =  numberfPizzas;
	}
	public void setcrustType(String crustType) {
		this.crustType = crustType;
	}

	public void setpizzaName(String pizzaName) {
		this.pizzaName = pizzaName;
	}

	public void setorderId(String orderId) {
		this.orderId = orderId;
	}

	public void setpizzaId(String pizzaId) {
		this.pizzaId = pizzaId;
	}
	
}
