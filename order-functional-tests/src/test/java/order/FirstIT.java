package order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import order.inface.TestLifecycleLogger;

import static io.restassured.RestAssured.*;

/**
 * Tests the Web application, by checking that the index page returns a code 200.
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("RestBook API Testing")
public class FirstIT implements TestLifecycleLogger
{
    /**
     * Call the index page of the REST API in the container.
     * @throws Exception if anything goes wrong.
     */
    @Test
    @Order(1)
    public void callIndexPage() throws Exception
    {
        expect().statusCode(200).when().get();
    }
    
    @Test
    @Order(2)
    public void callBooks() throws Exception
    {
        given().accept("text/html").get("/rest/orders").then().assertThat().contentType("text/html").and().statusCode(200);
    }
    
    @Test
    @Order(3)
    public void getBookWhileNoOneExists() throws Exception{
    	given().accept("text/html").get("/rest/orders/order/xxx").then().assertThat().statusCode(500);
    }    
}
