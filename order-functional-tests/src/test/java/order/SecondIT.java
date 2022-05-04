package order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import order.inface.TestLifecycleLogger;
import gr.aegean.order.domain.POrder;
import io.restassured.common.mapper.TypeRef;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

    @DisplayName("Adding Book Testing")
    @TestMethodOrder(OrderAnnotation.class)
    public class SecondIT implements TestLifecycleLogger{
    
	    @Test
	    @Order(1)
	    public void addWrongBook() throws Exception{
	    	gr.aegean.order.domain.POrder book = new gr.aegean.order.domain.POrder();
	    	given().contentType("application/json").body(book).when().post("rest/orders/order/create/employee").then().assertThat().statusCode(500);
	    }
	    
	    private gr.aegean.order.domain.POrder createOrder(String isbn) {
	    	Random r = new Random();
	    	gr.aegean.order.domain.POrder order = new gr.aegean.order.domain.POrder();
			order.setadress("address");
			order.setfirstName("firstName");
			order.setlastName("lastName");
			order.settelNumber("11232");
			order.setorderId(isbn);
	    	
	    	return order;
	    }
	    
	    @Test
	    @Order(2)
	    public void putCorrectBook() throws Exception{
	    	gr.aegean.order.domain.POrder book = createOrder("xxx");
	    	given().contentType("application/json").body(book).when().post("rest/orders/order/create/employee").then().assertThat().statusCode(200);
	    }
	    
	    @Test
	    @Order(3)
	    public void getExistingBook() throws Exception{
	    	given().accept("application/json").get("/rest/orders/orderinjson/xxx").then().assertThat().statusCode(200).and().body("orderId", equalTo("xxx"));
	    }
	    
	    @ParameterizedTest
	    @ValueSource(strings = { "1111", "2222", "3333" })
	    @Order(4)
	    void addCorrectBook(String isbn) {
	    	gr.aegean.order.domain.POrder book = createOrder(isbn);
	    	given().contentType("application/json").body(book).when().post("rest/orders/order/create/employee").then().assertThat().statusCode(200);
	    }
	    
	    @Test
	    @Order(5)
	    public void getExistingBooks() throws Exception{
	    	List<gr.aegean.order.domain.POrder> books = given().accept("application/json").get("/rest/orders/getordersinjson").then().assertThat().statusCode(200).extract().as(new TypeRef<List<gr.aegean.order.domain.POrder>>(){});
	    	assertThat(books, hasSize(4));
	    	assertThat(books.get(0).getIsbn(),anyOf(equalTo("xxx"),equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    	assertThat(books.get(1).getIsbn(),anyOf(equalTo("xxx"),equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    	assertThat(books.get(2).getIsbn(),anyOf(equalTo("xxx"),equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    	assertThat(books.get(3).getIsbn(),anyOf(equalTo("xxx"),equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    }
	    

    }
    
   
