package gr.aegean.order.service;

import java.util.ArrayList;
import java.util.List;

import gr.aegean.order.domain.POrder;
import gr.aegean.order.domain.Pizza;
import gr.aegean.order.exception.AccessDeniedException;
import gr.aegean.order.exception.BadRequestException;
import gr.aegean.order.exception.MyInternalServerErrorException;
import gr.aegean.order.exception.NotAcceptedMapper;
import gr.aegean.order.exception.BadRequestException;
import gr.aegean.order.exception.NotFoundException;
import gr.aegean.order.utility.DBHandler;
import gr.aegean.order.utility.HTMLHandler;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/orders")
public class OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	
	public Response getOrdersInHtml(@QueryParam("firstName") @DefaultValue("") String firstName, 
			@QueryParam("lastName") String lastName, @QueryParam("orderId") @DefaultValue("") String orderId, @Context SecurityContext
			sc)
					throws NotFoundException, MyInternalServerErrorException, AccessDeniedException{
		if ((orderId != null && !orderId.trim().equals("")) || (lastName != null && !lastName.trim().equals("") || (lastName != null && !lastName.trim().equals("")))){
			
			if(!sc.isUserInRole("employee"))
				throw new AccessDeniedException();
			
			List<POrder> orders = DBHandler.getOrders(orderId,firstName,lastName);
			if (orders != null) {
				String answer = HTMLHandler.createHtmlOrders(orders);
				return Response.ok(answer, MediaType.TEXT_HTML).build();
			}
			else throw new NotFoundException();
		}
		else {
			List<POrder> orders = DBHandler.getAllOrders();
			String answer = HTMLHandler.createHtmlOrders(orders);
			return Response.ok(answer, MediaType.TEXT_HTML).build();
		}
	}
	
	@GET
	@Path ("/getordersinjson")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	
	public List<POrder> getOrdersInJson()
					throws NotFoundException, MyInternalServerErrorException, AccessDeniedException{
		
			List<POrder> orders = DBHandler.getAllOrders();
			if(orders != null)
				return orders;
			else throw new NotFoundException();
				
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public List<POrder> getOrders(@QueryParam("firstName") @DefaultValue("") String firstName, 
			@QueryParam("lastName") String lastName, @QueryParam("orderId") @DefaultValue("") String orderId)
					throws NotFoundException, MyInternalServerErrorException{
		if ((orderId != null && !orderId.trim().equals("")) || (lastName != null && !lastName.trim().equals("") || (lastName != null && !lastName.trim().equals("")))){
			List<POrder> orders = DBHandler.getOrders(orderId,firstName,lastName);
			if (orders != null) {
				return orders;
			}
			else throw new NotFoundException();
		}
		else {
			List<POrder> orders = DBHandler.getAllOrders();
			return orders;
		}
	}
	
	@GET
	@Path ("/orderinjson/{isbn}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public POrder getOrderInJsom(@PathParam("isbn") String isbn2) throws NotFoundException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got isbn: " + isbn2);
		
		POrder order = DBHandler.getOrder(isbn2);
		if (order != null) {
			return order;
		}
		else throw new NotFoundException();
	}
	
	@GET
	@Path ("/order/{isbn}")
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response getOrderInHtml(@PathParam("isbn") String isbn) throws NotFoundException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got isbn: " + isbn);
		
		POrder order = DBHandler.getOrder(isbn);
		if (order != null) {
			String answer = HTMLHandler.createHtmlOrder(order);
			return Response.ok(answer + "", MediaType.TEXT_HTML).build();
		}
		else throw new NotFoundException();
	}
	
	@GET
	@Path ("/OrderByLastName/{isbn}")
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response getOrderByLastNameInHtml(@PathParam("isbn") String isbn) throws NotFoundException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got isbn: " + isbn);
		
		POrder order = DBHandler.getOrderByLastName(isbn);
		if (order != null) {
			String answer = HTMLHandler.createHtmlOrder(order);
			return Response.ok(answer + "", MediaType.TEXT_HTML).build();
		}
		else throw new NotFoundException();
	}
	
	@GET
	@Path ("/OrderByFirstName/{isbn}")
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response getOrderByFirstNameInHtml(@PathParam("isbn") String isbn) throws NotFoundException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got isbn: " + isbn);
		
		POrder order = DBHandler.getOrderByFirstName(isbn);
		if (order != null) {
			String answer = HTMLHandler.createHtmlOrder(order);
			return Response.ok(answer + "", MediaType.TEXT_HTML).build();
		}
		else throw new NotFoundException();
	}
	
	@GET
	@Path ("/order/{isbn}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public POrder getOrder(@PathParam("isbn") String isbn) throws NotFoundException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got isbn: " + isbn);
		
		POrder order = DBHandler.getOrder(isbn);
		if (order != null) {
			return order;
		}
		else throw new NotFoundException();
	}
	
	@POST
	@Path ("/order/create/employee")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response postPizzaOrder(POrder order)  throws NotFoundException, MyInternalServerErrorException {
	// Store the message

		try {

			if(order.getIsbn() == null)
				throw new BadRequestException("An internal error prevented from getting the information of the pizza orders --- :");
			
			boolean exist =  DBHandler.existsOrder(order.getIsbn());
			
			if(exist)
			{
				throw new MyInternalServerErrorException("An internal error prevented from getting the information of the pizza orders --- :");
			}		

			
			DBHandler.createOrder(order);
			
			return Response.ok("<p>success  was created </p>", MediaType.TEXT_HTML).build();
		}
		catch(Exception e) {
			throw new MyInternalServerErrorException("An internal error prevented from getting the information of the pizza orders --- :" + e.getMessage());
		}
	}

	@POST
	@Path ("/order/admin/update")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response updatePizzaOrder(@FormParam("orderId") String orderId, @FormParam("telNumber") String telNumber, @FormParam("pizzas") String[] pizzas,
			@FormParam("numberfPizzas") int[] numberfPizzas, @FormParam("crustType") String[] crustType) {
	// Store the message
		

		boolean exist =  DBHandler.existsOrder(orderId);
		
		if(!exist)
		{
			return Response.ok("<p>failed:  This order Id doesn't exists </p>", MediaType.TEXT_HTML).build();
		}		
		POrder order = new POrder();
		order.settelNumber(telNumber);
		order.setorderId(orderId);
		
		List<Pizza> newPizzas = new ArrayList<>();
		
		for(int i = 0; i < pizzas.length; i++)
		{
			Pizza newPizza = new Pizza();
			newPizza.setpizzaName(pizzas[i]);
			newPizza.setcrustType(crustType[i]);
			newPizza.setnumberfPizzas(numberfPizzas[i]);
			newPizza.setorderId(orderId);
			newPizza.setpizzaId(pizzas[i]);
			
			newPizzas.add(newPizza);
		}
		
		order.setpizzas(newPizzas);
		
		DBHandler.updateOrder(orderId, telNumber, newPizzas);
		
		return Response.ok("<p>success  "+orderId +" updated </p>", MediaType.TEXT_HTML).build();
	}

	@POST
	@Path ("/order/admin/delete/{orderId}")
	public Response deleteBook(@PathParam("orderId") String orderId) throws NotFoundException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got isbn: " + orderId);
		
		boolean deleted = DBHandler.deleteOrder(orderId);
		if (deleted) {
			return Response.ok().build();
		}
		else throw new NotFoundException();
	}
}
