package ru.pavel2107.neostoreRest.client;

/**
 * Created by lenovo on 15.12.2015.
 */

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.*;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;


@Component
public class BasicAthRestClient extends RestTemplate implements AuthorizedRestOperations{

    public BasicAthRestClient(){}

    public BasicAthRestClient(String username, String password){
        super();
        setCredentials( username, password);
    }


    public void setCredentials( String username, String password) {
        HttpClientBuilder builder = HttpClientBuilder.create();
        CredentialsProvider provider = new BasicCredentialsProvider();
        AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(scope, credentials);
        builder.setDefaultCredentialsProvider(provider);
        ClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory(builder.build());

        setRequestFactory(rf);
    }



}
