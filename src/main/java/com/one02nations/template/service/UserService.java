package com.one02nations.template.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.one02nations.template.model.User;
import com.one02nations.template.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;

	/**
	 * Gets the user by ID.
	 *
	 * @param id the id
	 * @return the user by ID
	 */
	public User findUserByID(final String id) throws UserServiceException {
		Optional<User> usr = userRepository.findByUserId(id);
		if (usr.isPresent()) {
			return usr.get();
		} else {
			throw new UserServiceException("User with id = " + id + " Does Not Exists");
		}
	}

	public User createUser(final String id) throws UserServiceException {
		User usr = com.one02nations.template.model.User.builder().email("test").userId(id).build();
		if (userRepository.findByUserId(id).isPresent()) {
			throw new UserServiceException("User with id = " + id + " Already Exists");
		} else {
			return userRepository.insert(usr);
		}
	}

}