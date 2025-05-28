package com.movies.service;

import com.movies.dao.UserRepository;
import com.movies.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user authentication and retrieving user details.
 * Implements Spring Security's UserDetailsService interface.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Repository for performing User related database operations.
    @Autowired
    UserRepository userRepository;

    /**
     * Loads a user's details by their username.
     *
     * @param username the username of the user to retrieve
     * @return UserDetails object containing user's information
     * @throws UsernameNotFoundException if the user with the specified username is not found
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Look for the user in the database using the username. If not found, throw an exception.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Build and return a UserDetails object for the found user.
        return UserDetailsImpl.build(user);
    }

}
