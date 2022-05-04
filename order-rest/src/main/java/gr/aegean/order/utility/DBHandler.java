package gr.aegean.order.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gr.aegean.order.configuration.PropertyReader;
import gr.aegean.order.domain.POrder;
import gr.aegean.order.domain.Pizza;
import gr.aegean.order.exception.MyInternalServerErrorException;
import gr.aegean.order.exception.AccessDeniedException;

import gr.aegean.order.exception.BadRequestException;

public class DBHandler {
	static {createTable();}
	
	private static void createTable() {
		if (PropertyReader.isSqlite()){
			try {
				Class.forName("org.sqlite.JDBC");
				Connection con = DriverManager.getConnection("jdbc:sqlite:./my.db");
				Statement stmt = con.createStatement();
				stmt.execute("create table pizzaorder(orderId VARCHAR(15) PRIMARY KEY, firstName VARCHAR(15), lastName VARCHAR(15), address VARCHAR(15), telNumber VARCHAR(20));");
				stmt.close();
				con.close();

			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Connection getConnection() throws MyInternalServerErrorException {
		try {
			Connection con = null;
			if (PropertyReader.isSqlite()){
				Class.forName("org.sqlite.JDBC");
				con = DriverManager.getConnection("jdbc:sqlite:./my.db");
			}
			else {
				Class.forName("com.mysql.cj.jdbc.Driver");  
				con = DriverManager.getConnection(  
			"jdbc:mysql://" + PropertyReader.getDbHost() + ":" + PropertyReader.getDbPort() + "/pizzaorder",PropertyReader.getLogin(),PropertyReader.getPwd());
			}
			return con;
		}
		catch(Exception e) {
			throw new AccessDeniedException("Cannot connect to underlying database -- "+e.getMessage());
		}
	}
	
	

	
	private static POrder getOrderFromRS(ResultSet rs) throws SQLException{
		POrder order = new POrder();
		order.setorderId(rs.getString("orderId"));
		order.setfirstName(rs.getString("firstName"));
		order.setlastName(rs.getString("lastName"));
		order.setadress(rs.getString("address"));
		//order.setpizzas( rs.getString("pizzas"));
		order.settelNumber(rs.getString("telNumber"));
		
		return order;
	}
	
	private static Pizza getPizzasFromRS(ResultSet rs) throws SQLException{
		Pizza pizza = new Pizza();
		pizza.setorderId(rs.getString("orderId"));
		pizza.setpizzaName(rs.getString("pizzaName"));
		pizza.setnumberfPizzas(rs.getInt("numberfPizzas"));
		//order.setpizzas( rs.getString("pizzas"));
		pizza.setcrustType(rs.getString("crustType"));
		
		return pizza;
	}
	
	
	public static List<POrder> getAllOrders() throws MyInternalServerErrorException{
		List<POrder> orders = new ArrayList<POrder>();
		Connection con = getConnection();
		POrder prevOrder = new POrder();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from pizzaorder");

		
				while (rs.next()) {

					POrder newOrder = getOrderFromRS(rs);	
				
					orders.add(newOrder);
				//	newOrder.setpizzas(getPizzas(newOrder.getorderId()));
					

				}
			con.close();
			
		}
		catch(Exception e) {
			throw new MyInternalServerErrorException("An internal error prevented from getting the information of the pizza orders --- :" + e.getMessage());
		}
		return orders;
	}
	
	public static List<Pizza> getPizzas(String orderId) throws MyInternalServerErrorException{
		List<Pizza> pizzas = null;
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from PizzaInfo where orderId='" + orderId +"'");
			if (rs.next()) {
				pizzas = new ArrayList<Pizza>();
				pizzas.add(getPizzasFromRS(rs));
				while (rs.next()) {
					
					pizzas.add(getPizzasFromRS(rs));
				}
			}
			
			con.close();
		}
		catch(Exception e) {
			throw new MyInternalServerErrorException("An internal error prevented from getting the information of the pizza orders --- :" + e.getMessage());
		}
		return pizzas;
	}
	
	public static List<POrder> getOrders(String orderId, String firstName, String lastName) throws MyInternalServerErrorException{
		List<POrder> orders = null;
		boolean hasorderId = false, hasfirstName = false, haslastName = false;
		hasorderId = (orderId != null && !orderId.trim().equals(""));
		hasfirstName = (firstName != null && !firstName.trim().equals(""));
		haslastName = (lastName != null && !lastName.trim().equals(""));

		String prevOrderId = null;
		POrder prevOrder = new POrder();
		
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "select * from PizzaOrder";
			if (hasorderId || hasfirstName || haslastName) query += " where ";
			if (hasorderId) query += "orderId = '" + orderId + "' ";
			if (hasfirstName) query += "firstName Like '%" + firstName + "%' ";
			if (haslastName) query += "lastName Like '%" + lastName + "%' ";
			
			

			System.out.println("query is: " + query);
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				orders = new ArrayList<POrder>();
				orders.add(getOrderFromRS(rs));

				while (rs.next()) {
					List<Pizza> pizzas = null;
					POrder newOrder = getOrderFromRS(rs);
					
					String query2 = "select * from PizzaInfo Where orderId='" + orderId +"'";


					Statement stmt2 = con.createStatement();
					
					ResultSet rs2 = stmt2.executeQuery(query2);
					
					
					while (rs2.next()) {
						Pizza neePizza = new Pizza();
						neePizza.setpizzaName("test");
						pizzas.add(new Pizza());
						//getPizzasFromRS(rs2)
					}
					
					
					newOrder.setpizzas(pizzas);
					
					orders.add(newOrder);

				}
			}
			
			con.close();
		}
		catch(Exception e) {
			throw new MyInternalServerErrorException("An internal error prevented from getting the information of the pizza orders --- :" + e.getMessage());
		}
		return orders;
	}
	
	public static POrder getOrder(String orderId) throws MyInternalServerErrorException{
		POrder order = null;
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "select * from pizzaorder where orderId='" + orderId + "'";
			System.out.println("query is: " + query);
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				order = getOrderFromRS(rs);

		}
			
			con.close();
		}
		catch(Exception e) {
			throw new MyInternalServerErrorException("An internal error prevented from getting the information of the pizza orders: " + e.getMessage());
		}
		return order;
	}
	public static POrder getOrderByLastName(String lastName) throws MyInternalServerErrorException{
		POrder order = null;
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "select * from pizzaorder join pizzainfo on pizzaorder.orderId = pizzainfo.orderId and pizzaorder.lastName='" + lastName + "' limit 1";
			System.out.println("query is: " + query);
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				order = getOrderFromRS(rs);

				order.addpizza(getPizzasFromRS(rs));

				order.addpizza(getPizzasFromRS(rs));
				while (rs.next()) {

					order.addpizza(getPizzasFromRS(rs));
			}
			}
			
			con.close();
		}
		catch(Exception e) {
			throw new MyInternalServerErrorException("An internal error prevented from getting the information of the pizza orders: " + e.getMessage());
		}
		return order;
	}
	public static POrder getOrderByFirstName(String firstName) throws MyInternalServerErrorException{
		POrder order = null;
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "select * from pizzaorder join pizzainfo on pizzaorder.orderId = pizzainfo.orderId and pizzaorder.firstName='" + firstName + "' limit 1";
			System.out.println("query is: " + query);
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				order = getOrderFromRS(rs);

				order.addpizza(getPizzasFromRS(rs));

				order.addpizza(getPizzasFromRS(rs));
				while (rs.next()) {

					order.addpizza(getPizzasFromRS(rs));
			}
			}
			con.close();
		}
		catch(Exception e) {
			throw new MyInternalServerErrorException("An internal error prevented from getting the information of the pizza orders: " + e.getMessage());
		}
		return order;
	}
	
	public static boolean existsOrder(String orderId) throws MyInternalServerErrorException , AccessDeniedException, BadRequestException{
		boolean hasOrder = false;
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "select * from pizzaorder where orderId='" + orderId + "'  limit 1";
			System.out.println("query is: " + query);
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				hasOrder = true;
			}
			
			con.close();
		}
		catch(Exception e) {
			throw new BadRequestException("An internal error prevented from getting the information of the pizza orders");
		}
		return hasOrder;
	}
	
	public static void updateOrder(String orderId, String telNumber, List<Pizza> pizzas ) throws MyInternalServerErrorException{
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = null;
			if(!pizzas.isEmpty())
			{
				query = "delete from pizzainfo  where orderId='" + orderId + "'";
				
				stmt.execute(query);
			}
			
			if(telNumber != null)
			{

				query = "update pizzaorder set " +
				"telNumber='" + telNumber+ "'" + 
				"where orderId='" + orderId + "'";
				System.out.println("query is: " + query);
				stmt.execute(query);
			}
			
			if(!pizzas.isEmpty())
			{

				for(Pizza pizza: pizzas)
				{
					query = "insert into PizzaInfo(orderId,pizzaName,crustType,numberfPizzas)" 
				     + "values('" + orderId + "','" + pizza.getpizzaName()  + "','" + pizza.getcrustType() + "','"+ pizza.getnumberfPizzas() + "')" ;
					stmt.execute(query);
				}
				
			}
			
			con.close();
		}
		catch(Exception e) {
			throw new MyInternalServerErrorException("An internal error prevented from getting the information of the pizza orders update failed " +  e.getMessage());
		}
	}
	
	private static String getFieldValue(String value) {
		if (value == null || value.trim().equals("")) return new String("NULL");
		else return "'" + value + "'";
	}
	
	public static void createOrder(POrder order) throws MyInternalServerErrorException, AccessDeniedException{
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "insert into pizzaorder(orderId,firstName,lastName,address,telNumber) "
					+ "values('" + order.getIsbn() + "','" + order.getfirstName()  + "','" + order.getlastName() + "','" +
					order.getadress() + "','" +
					order.gettelNumber()+"')";

			stmt.execute(query);
			con.close();
			
			
			
		}
		catch(Exception e) {
			throw new AccessDeniedException("An internal error prevented from getting the information of the pizza orders" + e.getMessage());
		}
	}
	
	
	public static boolean deleteOrder(String isbn) throws MyInternalServerErrorException{
		boolean deleted = false;

		Connection con2 = getConnection();
		try {
			Statement stmt2 = con2.createStatement();
			String query2 = "delete from pizzaorder  where orderId='" + isbn + "'";
			
			System.out.println("query is: " + query2);
			stmt2.execute(query2);
			if (stmt2.getUpdateCount() == 1) {
				deleted = true;
			}
			
			con2.close();
		}
		catch(Exception e) {
			throw new MyInternalServerErrorException("An internal error prevented from getting the information of the pizza orders : "+e.getMessage());
		}
		
		
		return deleted;
	}
}
