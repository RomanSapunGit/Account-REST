package com.myproject.accountrest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;


@SpringBootTest
class AccountRestApplicationTests {
    private static final String PROJECT_LOCAL_URL = "http://localhost:8080";
    private static final String PROJECT_LOCAL_SEARCH_PATH = "/api/tasks/search";
    @Value("${USER_USERNAME}")
    private String username;
    @Value("${USER_PASSWORD}")
    private String password;

    @Test
    public void searchTasksByTitle_worksCorrectly_true()  {
        String queryParam = "something";
        List<Integer> listToCheck = Arrays.asList(19,20,25);
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(PROJECT_LOCAL_URL)
                .path(PROJECT_LOCAL_SEARCH_PATH)
                .queryParam("title", queryParam)
                .build();
        RequestSpecification authRequest = RestAssured.given().auth().basic(username, password);
        Response response = authRequest.get(uriComponents.toString());
        response.then().assertThat().statusCode(200).assertThat().body("tasksList[0].id", equalTo(listToCheck));
    }

}
