package gr.aegean.order.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import gr.aegean.order.domain.Pizza;


@XmlRootElement
public class POrder {

	private String orderId = null;
    private String firstName = null;
    private String lastName = null;
    private String adress = null;
    private String telNumber = null;
    private List<Pizza> pizzas = new ArrayList<Pizza>();;
    
    public POrder() {}

    public POrder(String orderId, String firstName, String lastName, List<String> authors, String adress, String telNumber, List<Pizza> pizzas) {
        this.orderId = orderId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.telNumber = telNumber;
        this.pizzas = pizzas;
    }
    
    public String toString(){
    	return "Order(" + orderId + ", " + firstName + ", " + lastName + ", "  + adress +  ", " + pizzas.toString() +")";
    }

	public String getIsbn() {
		return orderId;
	}

	public void setorderId(String orderId) {
		this.orderId = orderId;
	}

	public String getorderId() {
		return orderId;
	}
	
	public String getfullName() {
		return this.firstName + " " + this.lastName;
	}

	public String getfirstName() {
		return this.firstName;
	}

	public String getlastName() {
		return this.lastName;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setadress(String adress) {
		this.adress = adress;
	}

	public String getadress() {
		return adress;
	}

	public void settelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String gettelNumber() {
		return telNumber;
	}

	public void setpizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	public void addpizza(Pizza pizza) {
		this.pizzas.add(pizza);
	}
	public List<Pizza> getpizzas() {
		return pizzas;
	}
}
