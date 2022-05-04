package order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import order.inface.TestLifecycleLogger;
import gr.aegean.order.domain.POrder;
import io.restassured.common.mapper.TypeRef;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;

    @DisplayName("Deleting Book Testing")
    @TestMethodOrder(OrderAnnotation.class)
    public class ThirdIT implements TestLifecycleLogger{
    	@Test
        @Order(1)
        public void deleteWrongBook() throws Exception{
        	given().post("/rest/orders/order/admin/delete/111").then().assertThat().statusCode(500);
        }
        
        @Test
        @Order(2)
        public void deleteCorrectBook() throws Exception{
        	given().post("/rest/orders/order/admin/delete/xxx").then().assertThat().statusCode(200);
        }
        
        @Test
        @Order(3)
        public void getNonExistingBook() throws Exception{
        	given().accept("application/json").get("/rest/orders/order/xxx").then().assertThat().statusCode(500);
        }
        
        @Test
	    @Order(4)
	    public void getExistingBooks() throws Exception{
        	List<gr.aegean.order.domain.POrder> books = given().accept("application/json").get("/rest/orders/").then().assertThat().statusCode(200).extract().as(new TypeRef<List<gr.aegean.order.domain.POrder>>(){});
	    	assertThat(books, hasSize(3));
	    	assertThat(books.get(0).getIsbn(),anyOf(equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    	assertThat(books.get(1).getIsbn(),anyOf(equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    	assertThat(books.get(2).getIsbn(),anyOf(equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    }
    }
