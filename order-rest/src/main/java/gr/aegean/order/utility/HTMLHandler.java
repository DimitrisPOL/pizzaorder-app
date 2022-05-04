package gr.aegean.order.utility;

import java.util.List;

import gr.aegean.order.domain.POrder;
import gr.aegean.order.domain.Pizza;

public class HTMLHandler {
	
	public static String getAuthors(List<String> authors) {
		String str = authors.get(0);
		for (int i = 1; i < authors.size(); i++) str += ", " + authors.get(i);
		return str;
	}
	
	private static String getStringFieldValue(String fieldValue) {
		if (fieldValue == null || fieldValue.trim().equals("")) return "";
		else return fieldValue;
	}
	
	private static String createOrderRow(POrder order) {
		String str = "<tr>";
		str += "<td>" + order.getIsbn() + "</td>";
		str += "<td>" + order.getfirstName() + "</td>";
		str += "<td>" + order.getlastName() + "</td>";
		str += "<td>" + order.getadress() + "</td>";
		str += "<td>" + order.gettelNumber() + "</td>";
		//str += "<td>" + getStringFieldValue(order.gettelNumber()) + "</td>";
		str += "<td>";
	
		str += "</td>";
		str += "</tr>";
		return str;
	}
	
	private static String createPizzaRow(Pizza pizza) {
		String str = "<tr>";
		str += "<td>" + pizza.getpizzaName() + "</td>";
		str += "<td>" + pizza.getcrustType() + "</td>";
		str += "<td>" + pizza.getnumberfPizzas() + "</td>";
		//str += "<td>" + getStringFieldValue(order.gettelNumber()) + "</td>";
		str += "</tr>\n";
		
		return str;
	}
	
	public static String createHtmlOrders(List<POrder> orders) {
		String answer = "<html>\n";
		
		answer += "<head>\n";
		answer += "<title>The Pizza Orders from Pizza App</title>\n";
		answer += "</head>\n";
		
		answer += "<body>\n";
		answer += "<h1>ORDERS</h1>\n";
		answer += "<table border=\"1\" width=\"60%\" align=\"center\">\n";
		answer += "<caption>The requested orders</caption>\n";
		answer += "<tr><th>OrderId</th><th>First Name</th><th>LastName</th><th>Address</th>";
		answer += "<th>tel Number</th><th>Pizzas</th>";
		answer += "</tr>\n";
		if (orders != null)
			for (POrder order: orders) answer += createOrderRow(order);
		answer += "</table>\n";
		answer += "</body>\n";
		
		answer += "\n</html>";
		
		return answer;
	}
	
	public static String createHtmlOrder(POrder order) {
		String answer = "<html>\n";
		
		answer += "<head>\n";
		answer += "<title>An Order from Pizza App</title>\n";
		answer += "</head>\n";
		
		answer += "<body>\n";
		answer += "<h1>Order " + order.getIsbn() + "</h1>\n";
		answer += "<table border=\"1\" width=\"60%\" align=\"center\">\n";
		answer += "<caption>The requested book</caption>\n";
		answer += "<tr><th>OrderId</th><th>First Name</th><th>Last Name</th><th>Address</th>";
		answer += "<th>Tel Number</th><th>Pizzas</th><th>Language</th>";
		answer += "<th>Pub. date</th>";
		answer += "</tr>\n";
		answer += createOrderRow(order);
		answer += "</table>\n";
		answer += "</body>\n";
		
		answer += "\n</html>";
		
		return answer;
	}
}
