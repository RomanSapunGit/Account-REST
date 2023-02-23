package com.myproject.accountrest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
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
    private static final String PROJECT_LOCAL_CHANGE_AUTHORITY_PATH = "/api/users/change-authority";
    private static final String ROLE_DISABLE = "ROLE_DISABLE";
    private static final String QUERY_NAME = "title";
    private static final String QUERY_PARAM = "something";
    @Value("${USER_USERNAME}")
    private String username;
    @Value("${USER_PASSWORD}")
    private String password;

    @Test
    public void searchTasksByTitle_worksCorrectly_true() {
        List<Integer> listToCheck = Arrays.asList(19, 20, 25);
        UriComponents uriComponents = setURLWithQuery();
        RequestSpecification authRequest = RestAssured.given().auth().basic(username, password);
        Response response = authRequest.get(uriComponents.toString());
        response.then().assertThat().statusCode(200).and().body("tasksList[0].id", equalTo(listToCheck));
    }

    @Test
    public void changeUserAuthority_worksCorrectly_true() throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", username);
        requestBody.put("action", "add");
        requestBody.put("role", ROLE_DISABLE);
        RequestSpecification authRequest = RestAssured
                .given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(requestBody.toString());
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(PROJECT_LOCAL_URL)
                .path(PROJECT_LOCAL_CHANGE_AUTHORITY_PATH)
                .build();
        Response response = authRequest.put(uriComponents.toString());
        response.then().statusCode(200);
        authRequest
                .contentType(ContentType.JSON)
                .body(requestBody.toString());
        UriComponents uriComponentsAfterRoleDisable = setURLWithQuery();
        Response responseAfterRoleDisable = authRequest.get(uriComponentsAfterRoleDisable.toString());
        responseAfterRoleDisable.then().assertThat().statusCode(403);
    }

    private UriComponents setURLWithQuery() {
        return UriComponentsBuilder
                .fromHttpUrl(PROJECT_LOCAL_URL)
                .path(PROJECT_LOCAL_SEARCH_PATH)
                .queryParam(QUERY_NAME, QUERY_PARAM)
                .build();
    }
}
