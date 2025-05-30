package com.tnbin.puttly.global.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tnbin.puttly.global.common.response.base.ResponseType;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collections;
import java.util.List;

/**
 * {@link ApiResponse}의 {@code data} 필드의 값이 {@code null}일 경우, 빈 객체 또는 빈 리스트 객체를
 * {@code data} 필드에 담아 반환하는 클래스.
 *
 * @see ApiResponse
 */

@RestControllerAdvice(basePackages = "com.tnbin.puttly.domain")
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * 컨트롤러 메소드의 반환 타입이 {@link ApiResponse}가 아닐 경우, {@code true}를 반환하며, 이를 통해
     * 응답 데이터의 실제 처리를 수행할 {@code beforeBodyWrite}메소드를 호출한다.
     *
     * @param returnType        메소드의 반환 타입
     * @param converterType     메시지 컨버터 타입
     * @return                  컨트롤러 메소드의 반환 타입이 {@link ApiResponse}가 아닐 경우 {@code true}, 그렇지 않을 경우 {@code false} 반환
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !ApiResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    /**
     * 컨트롤러 메소드의 반환 데이터에 공통 처리하는 메소드로서, 성공 응답의 {@code data}의 값이 {@code null}일 경우,
     * 빈 객체 또는 빈 리스트 객체를 할당하여 응답을 반환 한다.
     *
     * @return  응답 객체
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        final Object wrappedBody = (body == null) ? generateEmptyCollection(returnType.getParameterType()) : body;
        final ApiResponse<?> apiResponse = ApiResponse.ok(ResponseType.OK, wrappedBody);
        if (MappingJackson2HttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
            return apiResponse;
        }

        try {
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialized an object to JSON!", e);
        }
    }

    private Object generateEmptyCollection(Class<?> returnType) {
        return List.class.isAssignableFrom(returnType) ? Collections.emptyList() : Collections.emptyMap();
    }
}