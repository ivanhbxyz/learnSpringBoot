package com.ivanxhb.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
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
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
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

        //ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class); // Why is the responseType String here? If in the backend this URI is returning a CashCard type?
        
		// Adding Authentication to this call.
        ResponseEntity<String> response = restTemplate
										.withBasicAuth("sarah1", "abc123")
										.getForEntity("/cashcards/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(99);

        Double amount = documentContext.read("$.amount");
        assertThat(amount).isEqualTo(123.45);
    }

	@Test
    void shouldNotReturnACashCardWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate
								.withBasicAuth("sarah1", "abc123")
								.getForEntity("/cashcards/1000", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

	/*
	// Updated version below
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
    @DirtiesContext
    void shouldCreateANewCashCard() {
        CashCard newCashCard = new CashCard(null, 250.00, "sarah1");
        
		ResponseEntity<Void> createResponse = restTemplate
						.withBasicAuth("sarah1", "abc123")
						.postForEntity("/cashcards", newCashCard, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewCashCard = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
									.withBasicAuth("sarah1", "abc123")
									.getForEntity(locationOfNewCashCard, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        Double amount = documentContext.read("$.amount");

        assertThat(id).isNotNull();
        assertThat(amount).isEqualTo(250.00);
    }


	@Test
	@DirtiesContext // Why?
	void shouldReturnAllCashCardsWhenListIsRequested() {
		ResponseEntity<String> response = restTemplate
								.withBasicAuth("sarah1", "abc123")
								.getForEntity("/cashcards", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
   
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int cashCardCount = documentContext.read("$.length()");
		assertThat(cashCardCount).isEqualTo(3);
   
		JSONArray ids = documentContext.read("$..id");
		assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);
   
		JSONArray amounts = documentContext.read("$..amount");
		assertThat(amounts).containsExactlyInAnyOrder(123.45, 1.0, 150.00);
	}

	@Test
	void shouldReturnAPageOfCashCards() {
		ResponseEntity<String> response = restTemplate
									.withBasicAuth("sarah1", "abc123")
									.getForEntity("/cashcards?page=0&size=1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);
	}


	@Test
    void shouldReturnASortedPageOfCashCards() {
        ResponseEntity<String> response = restTemplate
								.withBasicAuth("sarah1", "abc123")
								.getForEntity("/cashcards?page=0&size=1&sort=amount,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray read = documentContext.read("$[*]");
        assertThat(read.size()).isEqualTo(1);

        double amount = documentContext.read("$[0].amount");
        assertThat(amount).isEqualTo(150.00);
    }


	@Test
    void shouldReturnASortedPageOfCashCardsWithNoParametersAndUseDefaultValues() {
        ResponseEntity<String> response = restTemplate
								.withBasicAuth("sarah1", "abc123")
								.getForEntity("/cashcards", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(3);

        JSONArray amounts = documentContext.read("$..amount");
        assertThat(amounts).containsExactly(1.00, 123.45, 150.00);
    }


	@Test
	void shouldNotReturnACashCardWhenUsingBadCredentials() {
		ResponseEntity<String> response= restTemplate
									.withBasicAuth("BAD_USER", "abc123")
									.getForEntity("/cashcards/99",String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

			response = restTemplate
								.withBasicAuth("sarah1", "bad_password")
								.getForEntity( "/cashcards/99",String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}
	
	//Test Role Verification
	@Test
	void shouldRejectUsersWhoAreNotCardOwners() {
		ResponseEntity<String> response = restTemplate
					.withBasicAuth("hank-owns-no-cards", "qrs456")
					.getForEntity( "/cashcards/99", String.class);
		//But wait! CashCard with ID 99 belongs to sarah1, right? 
		//Shouldn't only sarah1 have access to that data regardless of role?
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	
	// Test that users cannot access each other's data.
	@Test
	void shouldNotAllowAccessToCashCardsTheyDoNotOwn() {
		ResponseEntity<String> response = restTemplate
					.withBasicAuth("sarah1", "abc123")
					.getForEntity("/cashcards/102", String.class); // kumar2's data

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	// Testing update functionality
	/*
	@Test
	@DirtiesContext
	void shouldUpdateAnExistingCashCard() {
    CashCard cashCardUpdate = new CashCard(null, 19.99, null);
    HttpEntity<CashCard> request = new HttpEntity<>(cashCardUpdate);
    ResponseEntity<Void> response = restTemplate
            .withBasicAuth("sarah1", "abc123")
            .exchange("/cashcards/99", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	*/


	// Stricter test
	@Test
	@DirtiesContext
	void shouldUpdateAnExistingCashCard() {
		CashCard cashCardUpdate = new CashCard(null, 19.99, null);
		HttpEntity<CashCard> request = new HttpEntity<>(cashCardUpdate);
		
		
		ResponseEntity<Void> response = restTemplate
            .withBasicAuth("sarah1", "abc123")
            .exchange("/cashcards/99", HttpMethod.PUT, request, Void.class);
    	
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
			
			
		ResponseEntity<String> getResponse = restTemplate
			.withBasicAuth("sarah1", "abc123")
			.getForEntity("/cashcards/99", String.class);
  
			assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
  
			DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
			Number id = documentContext.read("$.id");
			Double amount = documentContext.read("$.amount");
			 
			assertThat(id).isEqualTo(99);
			assertThat(amount).isEqualTo(19.99);
		}



}