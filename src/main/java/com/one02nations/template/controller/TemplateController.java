package com.one02nations.template.controller;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.one02nations.template.model.User;
import com.one02nations.template.service.UserService;
import com.one02nations.template.service.UserServiceException;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class TemplateController {

	@GetMapping("/me")
	public UserInfoDto getMe(Authentication auth) {
		if (auth instanceof JwtAuthenticationToken jwtAuth) {
			final var email = (String) jwtAuth.getTokenAttributes().getOrDefault(StandardClaimNames.EMAIL, "");
			final var roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
			final var exp = Optional.ofNullable(jwtAuth.getTokenAttributes().get(JwtClaimNames.EXP)).map(expClaim -> {
				if (expClaim instanceof Long lexp) {
					return lexp;
				}
				if (expClaim instanceof Instant iexp) {
					return iexp.getEpochSecond();
				}
				if (expClaim instanceof Date dexp) {
					return dexp.toInstant().getEpochSecond();
				}
				return Long.MAX_VALUE;
			}).orElse(Long.MAX_VALUE);
			return new UserInfoDto(auth.getName(), email, roles, exp);
		}
		return UserInfoDto.ANONYMOUS;
	}

	/**
	 * @param username a unique identifier for the resource owner in the token (sub
	 *                 claim by default)
	 * @param email    OpenID email claim
	 * @param roles    Spring authorities resolved for the authentication in the
	 *                 security context
	 * @param exp      seconds from 1970-01-01T00:00:00Z UTC until the specified UTC
	 *                 date/time when the access token expires
	 */
	public static record UserInfoDto(String username, String email, List<String> roles, Long exp) {
		public static final UserInfoDto ANONYMOUS = new UserInfoDto("", "", List.of(), Long.MAX_VALUE);
	}

	@GetMapping("/test")
	public String doTest() {
		return "This is a test";
	}

	private final UserService userService;

	TemplateController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users/{id}")
	User findByUserId(@PathVariable String id) {

		try {
			return userService.findUserByID(id);
		} catch (UserServiceException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

		}

	}

	@PostMapping("/users/{id}")
	User createUser(@PathVariable String id) {
		try {
			return userService.createUser(id);
		} catch (UserServiceException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}
	@DeleteMapping("/users/{id}")
	void deleteUserById(@PathVariable String id) {
		try {
		  userService.deleteUser(id);
		} catch (UserServiceException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

		}
	}
}
