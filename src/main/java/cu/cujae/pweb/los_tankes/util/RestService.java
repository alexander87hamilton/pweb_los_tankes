package cu.cujae.pweb.los_tankes.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestService {
	
	@Value("${spring.services.backend-host}")
    private String backendHost;
	
	@Value("${spring.services.backend-schema}")
    private String backendSchema;
	
	@Value("${server.port}")
    private String backendPort;
	
	private String getUrlBackend() {
		return backendSchema + "://" + backendHost + ":" + backendPort;
	}

    public ResponseEntity<String> GET(String endpoint, MultiValueMap<String,String> queryParams, Class<String> clss) {
        return this.GET(endpoint, queryParams, clss, null);
    }

    public ResponseEntity<String> GET(String endpoint, MultiValueMap<String,String> queryParams, Class<String> clss, String jwt) {
        try {
            return buildRestTemplate().exchange(
            		getUri(getUrlBackend() + endpoint, queryParams),
                    HttpMethod.GET,
                    HttpEntity(jwt),
                    clss);
        } catch (HttpServerErrorException e) {
            return handleRequestException(e);
        }
    }
    
    public ResponseEntity<String> POST(String endpoint, Object body, Class<String> clss) {
        return this.POST(endpoint, body, clss, null);
    }

    public ResponseEntity<String> POST(String endpoint, Object body, Class<String> clss, String jwt) {
        try {
            return buildRestTemplate().exchange(
            		getUrlBackend() + endpoint,
                    HttpMethod.POST,
                    new HttpEntity<Object>(body, HttpHeadersForm(jwt)),
                    clss);
        } catch (HttpServerErrorException e) {
            return handleRequestException(e);
        }
    }

    public ResponseEntity<String> PUT(String endpoint, MultiValueMap<String, String> queryParams, Object body, Class<String> clss) {
        return this.PUT(endpoint, queryParams, body, clss, null);
    }

    public ResponseEntity<String> PUT(String endpoint, MultiValueMap<String, String> queryParams, Object body, Class<String> clss, String jwt) {
        try {
            return buildRestTemplate().exchange(
                    getUri(getUrlBackend() + endpoint, queryParams),
                    HttpMethod.PUT,
                    new HttpEntity<Object>(body, HttpHeadersForm(jwt)),
                    clss);
        } catch (HttpServerErrorException e) {
            return handleRequestException(e);
        }
    }
    
    public ResponseEntity<String> DELETE(String endpoint, MultiValueMap<String,String> queryParams, Class<String> clss) {
        return this.DELETE(endpoint, queryParams, clss, null);
    }

    public ResponseEntity<String> DELETE(String endpoint, MultiValueMap<String,String> queryParams, Class<String> clss, String jwt) {
        try {
            return buildRestTemplate().exchange(
            		getUri(getUrlBackend() + endpoint, queryParams),
                    HttpMethod.DELETE,
                    HttpEntity(jwt),
                    clss);
        } catch (HttpServerErrorException e) {
            return handleRequestException(e);
        }
    }
    
    public ResponseEntity<String> PATCH(String endpoint, MultiValueMap<String, String> queryParams, String body, Class<String> clss) {
        return this.PATCH(endpoint, queryParams, body, clss, null);
    }

    public ResponseEntity<String> PATCH(String endpoint, MultiValueMap<String, String> queryParams, Object body, Class<String> clss, String jwt) {
        try {
            return buildRestTemplate().exchange(
                    getUri(getUrlBackend() + endpoint, queryParams),
                    HttpMethod.PATCH,
                    new HttpEntity<Object>(body, HttpHeadersForm(jwt)),
                    clss);
        } catch (HttpServerErrorException e) {
            return handleRequestException(e);
        }
    }

    private String getUri(String endpoint, MultiValueMap<String, String> queryParams){
        return UriComponentsBuilder
                .fromUriString(endpoint)
                .queryParams(queryParams)
                .toUriString();
    }
    
    private HttpEntity<Object> HttpEntity(String jwt){
        return new HttpEntity<Object>(HttpHeadersJson(jwt));
    }

    private HttpHeaders HttpHeadersJson(String jwt){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers = setAuth(headers, jwt);
        return headers;
    }

    private HttpHeaders HttpHeadersForm(String jwt){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers = setAuth(headers, jwt);
        return headers;
    }

    private HttpHeaders setAuth(HttpHeaders headers, String jwt) {
        if (jwt != null) {
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        }
        return headers;
    }

    private RestTemplate buildRestTemplate(){
        return new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    private ResponseEntity<String> handleRequestException(HttpServerErrorException exception){
        HttpStatus status = exception.getStatusCode();
        String message = exception.getLocalizedMessage();
        log.error("Exception when processing request: \nstatus -> {} \nmessage -> {}", status, message);
        return ResponseEntity
                .status(status)
                .body(message);
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

}
