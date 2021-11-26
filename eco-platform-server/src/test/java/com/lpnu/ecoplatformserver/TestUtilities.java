package com.lpnu.ecoplatformserver;

import com.lpnu.ecoplatformserver.user.dto.AuthenticationResponseDto;
import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;
import lombok.experimental.UtilityClass;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.AUTH_LOGIN_URL;

@UtilityClass
public class TestUtilities {

    public <T> ResponseEntity<T> getParametrizedResponse(TestRestTemplate restTemplate, String url, ParameterizedTypeReference<T> type) {
        return restTemplate.exchange(url, HttpMethod.GET, null, type);
    }

    public AuthenticationResponseDto login(TestRestTemplate restTemplate, int port, UserLoginDto loginDto) {
        return restTemplate.postForEntity(String.format(AUTH_LOGIN_URL, port), loginDto, AuthenticationResponseDto.class).getBody();
    }

    public void performAuthenticated(Supplier<AuthenticationResponseDto> login, Consumer<HttpHeaders> headersConsumer) {
        var authResponse = login.get();
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + authResponse.authenticationToken());
        headersConsumer.accept(headers);
        headers.clear();
    }

}
