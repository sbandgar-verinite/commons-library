package com.verinite.commons.serviceimpl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verinite.commons.model.User;
import com.verinite.commons.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("${user.service.base.url}")
	private String userServiceUrl;

	@Override
	public UserDetails loadUserByUsername(String email) {
		HttpClient httpClient = HttpClient.newHttpClient();
		URI uri;
		try {
			uri = new URI(userServiceUrl + "/user/details");
			HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() >= 200 && response.statusCode() < 300) {
				ObjectMapper objectMapper = new ObjectMapper();
				UserDetails responseObject = objectMapper.readValue(response.body(), UserDetails.class);
				System.out.println("Response Object: " + responseObject);
				return responseObject;
			} else {
				System.out.println("Error response: " + response.body());
			}
		} catch (URISyntaxException | IOException | InterruptedException e) {
			System.out.println("Exception : " + e.getMessage());
		}

		return null;
	}
	
	@Override
	public User findByEmail(String email) {
		HttpClient httpClient = HttpClient.newHttpClient();
		URI uri;
		try {
			uri = new URI(userServiceUrl + "/user");
			HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() >= 200 && response.statusCode() < 300) {
				ObjectMapper objectMapper = new ObjectMapper();
				User responseObject = objectMapper.readValue(response.body(), User.class);
				System.out.println("Response Object: " + responseObject);
				return responseObject;
			} else {
				System.out.println("Error response: " + response.body());
			}
		} catch (URISyntaxException | IOException | InterruptedException e) {
			System.out.println("Exception : " + e.getMessage());
		}

		return null;
	}

}