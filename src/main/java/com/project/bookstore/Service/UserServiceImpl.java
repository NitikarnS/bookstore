package com.project.bookstore.Service;

import java.util.HashSet;
import java.util.Set;

import com.project.bookstore.Exception.UsernameAlreadyExistException;
import com.project.bookstore.Model.User;
import com.project.bookstore.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(String.format("%s not found", username));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
    }

    public User createUser(User user) throws UsernameAlreadyExistException {
        User userData = userRepository.findByUsername(user.getUsername());
        if (userData != null)
            throw new UsernameAlreadyExistException("Username already exist");
        String encodedPasswod = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPasswod);
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User login(String username, String password) throws BadCredentialsException {
        User user = userRepository.findByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("Invalid username/password");

        UserDetails principal = loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,
                principal.getPassword(), principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return user;
    }

    @Override
    public User getCurrentUser() throws UsernameNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(String.format("%s not found", username));
        return user;
    }

}
