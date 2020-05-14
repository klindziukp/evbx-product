package com.evbx.product.apiclient;

import com.evbx.product.config.ServiceConfig;
import com.evbx.product.model.error.ErrorResponse;
import com.evbx.product.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public final class ApiClient {

    private static final Logger LOGGER = LogManager.getLogger(ApiClient.class);
    private final RestTemplate restTemplate;
    private final ServiceConfig serviceConfig;

    public ApiClient(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
        this.restTemplate = new RestTemplateBuilder().rootUri(serviceConfig.getBaseUrl()).basicAuthentication(
                serviceConfig.getUserName(), serviceConfig.getPassword()).build();
    }

    /**
     * Method to execute GET request against a URI.
     *
     * @param path     the URI template representing the path to request
     * @param classOfT the type of object that will be returned in the response body
     * @param <T>      the type of object in the body
     * @return Instance of T
     */
    public <T> T get(String path, Class<T> classOfT) {
        try {
            LOGGER.info("GET {}{}", serviceConfig.getBaseUrl(), path);
            return restTemplate.exchange(path, HttpMethod.GET, null, classOfT).getBody();
        } catch (HttpStatusCodeException httpEx) {
            return getErrorOfT(httpEx);
        }
    }

    /**
     * Method to execute GET request against a URI.
     *
     * @param path                       the URI template representing the path to request
     * @param parameterizedTypeReference the type of object that will be returned in the response body
     * @param <T>                        the type of object in the body
     * @return Instance of T
     */
    public <T> T get(String path, ParameterizedTypeReference<T> parameterizedTypeReference) {
        try {
            return restTemplate.exchange(path, HttpMethod.GET, null, parameterizedTypeReference).getBody();
        } catch (HttpStatusCodeException httpEx) {
            return getErrorOfT(httpEx);
        }
    }

    /**
     * Method to handle  HttpStatusCodeException.
     *
     * @param httpEx HttpStatusCodeException
     * @param <T> the type of object in the body
     * @return NULL as fail-safe
     */
    private <T> T getErrorOfT(HttpStatusCodeException httpEx) {
        String errorBody = httpEx.getResponseBodyAsString();
        ErrorResponse errorResponse = JsonUtil.fromJson(errorBody, ErrorResponse.class);
        LOGGER.warn("WARN {}", errorResponse.getMessage());
        return null;
    }
}
