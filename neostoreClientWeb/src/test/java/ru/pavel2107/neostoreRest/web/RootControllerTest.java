package ru.pavel2107.neostoreRest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import ru.pavel2107.neostoreRest.client.BasicAthRestClient;
import ru.pavel2107.neostoreRest.model.User;
import ru.pavel2107.neostoreRest.utils.ErrorInfo;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 26.12.2015.
 */
public class RootControllerTest {
    String REST_SERVICE_URI = "http://localhost:8070/neoStore";
    BasicAthRestClient restClient = null;

    @Before
    public void setUp() throws Exception {
        restClient = new BasicAthRestClient( "alex", "alex");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRoot() throws Exception {
        String uri = REST_SERVICE_URI + "/rest/anonym/users/error4";
        try {
            User user = restClient.getForObject(uri, User.class);
        }
        catch ( HttpClientErrorException e){
            System.out.println( "message=" + e.getMessage());
            System.out.println( "body" + e.getResponseBodyAsString());
            HttpHeaders headers = e.getResponseHeaders();
            System.out.println( headers);

            ObjectMapper mapper = new ObjectMapper();
            ErrorInfo errorInfo = mapper.readValue( e.getResponseBodyAsString(), ErrorInfo.class);
            System.out.println( "code=" + errorInfo.getErrCode());
            System.out.println( "message=" + errorInfo.getErrMessage());

        }
        catch ( Exception e){
            e.printStackTrace();
        }
    }
}