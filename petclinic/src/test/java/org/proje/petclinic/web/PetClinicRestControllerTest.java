package org.proje.petclinic.web;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.proje.exception.OwnerNotFoundException;
import org.proje.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("dev")
public class PetClinicRestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = restTemplate.withBasicAuth("user2", "secret");
    }

    @Test
    public void testGetOwnerById(){
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Zeynep"));
    }

    @Test
    public void testServiceLevelValidation(){
        Owner owner=new Owner();
        //owner.setFirstName("k");
        //owner.setLastName("s");

        ResponseEntity<URI> responseEntity=restTemplate.postForEntity("http://localhost:8080/rest/owner",owner, URI.class);

        MatcherAssert.assertThat(responseEntity.getStatusCode(),Matchers.equalTo(HttpStatus.PRECONDITION_FAILED));
    }

    @Test
    public void testGetOwnersByLastName(){
        ResponseEntity<List>response=restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=Sevindik", List.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<Map<String,String>> body=response.getBody();
        List<String> firstNames=body.stream().map(e->e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstNames,Matchers.containsInAnyOrder("Kenan","Hümeyra","Salim"));
    }

    @Test
    public void testUpdateOwner(){
        Owner owner=restTemplate.getForObject("http://localhost:8080/rest/owner/4",Owner.class);
        MatcherAssert.assertThat(owner.getFirstName(),Matchers.equalTo("Salim"));
        owner.setFirstName("Salim Güray");
        restTemplate.put("http://localhost:8080/rest/owner/4",owner);
        owner=restTemplate.getForObject("http://localhost:8080/rest/owner/4", Owner.class);
        MatcherAssert.assertThat(owner.getFirstName(),Matchers.equalTo("Salim Güray"));
    }
    @Test
    public void testDeleteOwner(){
        //restTemplate.delete("http://localhost:8080/rest/owner/1");
        ResponseEntity<Void> responseEntity=restTemplate.exchange("http://localhost:8080/rest/owner/1", HttpMethod.DELETE,null, Void.class);
        try {
            restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
            Assert.fail("Should have not returned owner");
        }
        catch (HttpClientErrorException ex){
            MatcherAssert.assertThat(ex.getStatusCode().value(),Matchers.equalTo(404));
        }
    }

    @Test
    public void testGetOwners(){
        ResponseEntity<List> response=restTemplate.getForEntity("http://localhost:8080/rest/owners",List.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(),Matchers.equalTo(200));

        List<Map<String,String>> body=response.getBody();

        List<String> firstNames=body.stream().map(e->e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Kenan","Hümeyra","Salim","Muammer"));
    }

    @Test
    public void testCreateOwner(){
        Owner owner=new Owner();
        owner.setFirstName("Beyza");
        owner.setLastName("Alıcı");

       URI location= restTemplate.postForLocation("http://localhost:8080/rest/owner",owner);
       Owner owner2=restTemplate.getForObject(location,Owner.class);

       MatcherAssert.assertThat(owner2.getFirstName(),Matchers.equalTo(owner.getFirstName()));
       MatcherAssert.assertThat(owner2.getLastName(),Matchers.equalTo(owner.getLastName()));

    }
}
