package com.ivanxhb.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

/*
 * Updated during Lab: implementing GET
@SpringBootTest
class CashcardApplicationTests {

	@Test
	void contextLoads() {
	}

}
*/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardApplicationTests {
	
	/*
	 * What does @Autowired do?
	 * 
	 * A: We tell Spring to "inject" a test helper that'll allow us to make HTTP request to the locally running app.
	 * 
	 * @Autowired is a form of Spring dependecy injection it's best used only in tests
	 * 
	 */
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnACashCardWhenDataIsSaved() {
    	/*
    	 * Look more into restTemplate and TestRestTemplate.
    	 * And the ResponseEntity Class.
    	 */

        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class); // Why is the responseType String here? If in the backend this URI is returning a CashCard type?
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		//assertThat(id).isNotNull(); // Test 1
		assertThat(id).isEqualTo(99); // Test 2

		Double amount = documentContext.read("$.amount");
		assertThat(amount).isEqualTo(123.45);
	
	}

	@Test
    void shouldNotReturnACashCardWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/1000", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

	/*
	@Test
	void shouldCreateANewCashCard() {
		CashCard newCashCard = new CashCard(null, 250.00);
		// NOTE: Here we are using the postForEntity method along with a responseType class that is Void.class?
		// What is ResponseEntity? what is Void?
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/cashcards", newCashCard, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	*/
	@Test
	void shouldCreateANewCashCard() {
		CashCard newCashCard = new CashCard(null, 250.00);
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/cashcards", newCashCard, Void.class);

		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);// Because per the RFC the POST request shold return a CREATED
		
		// We are told that... send a 201(CREATED) response containing a Location header field that provides an identifier for the primary resource created...
		// That is , When a POST request results in the successful creation of a resource, such a as a new CashCard, the response
		// should include information for how to retrieve that resource. We'll do this by supplying a URI in a Response Header named "Location"
		URI locationOfNewCashCard = createResponse.getHeaders().getLocation();

		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewCashCard, String.class);
		//we'll use the Location header's information to fetch teh newly created CashCard
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		// Add assertions such as these
		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		Double amount = documentContext.read("$.amount");
		assertThat(id).isNotNull();
		assertThat(amount).isEqualTo(250.00);
	}




}