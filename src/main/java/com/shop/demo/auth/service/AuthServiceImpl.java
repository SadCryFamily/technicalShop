package com.shop.demo.auth.service;

import com.shop.demo.auth.entity.ERole;
import com.shop.demo.auth.entity.Role;
import com.shop.demo.auth.pojo.JwtResponse;
import com.shop.demo.auth.pojo.LoginRequest;
import com.shop.demo.auth.pojo.MessageResponse;
import com.shop.demo.auth.pojo.SignupRequest;
import com.shop.demo.auth.repository.RoleRepository;
import com.shop.demo.config.jwt.JwtUtils;
import com.shop.demo.dto.CreateCustomerDto;
import com.shop.demo.enitity.Customer;
import com.shop.demo.mapper.CustomerMapper;
import com.shop.demo.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> authUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        log.info("LOGGED Customer[LOGIN: {}, PASSWORD: {}",
                loginRequest.getUsername(), loginRequest.getPassword());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {

        if (customerRepository.existsByCustomerName(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }

        if (customerRepository.existsByCustomerEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist"));
        }

        CreateCustomerDto customerDto = new CreateCustomerDto(
                signupRequest.getUsername(),
                signupRequest.getPassword(),
                signupRequest.getEmail(),
                signupRequest.getRoles());

        Customer customer = customerMapper.toCustomer(customerDto);

        String encryptedPassword = passwordEncoder.encode(customerDto.getPassword());
        customer.setCustomerPassword(encryptedPassword);

        Set<String> requestRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (requestRoles == null) {
            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            requestRoles.forEach(r -> {

                if (r.equals("moderator")) {
                    Role modRole = roleRepository
                            .findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                    roles.add(modRole);
                } else {
                    throw new RuntimeException("Invalid role setted!");
                }

            });
        }

        Role userRole = roleRepository
                .findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
        roles.add(userRole);

        customer.setRoles(roles);
        customerRepository.save(customer);

        log.info("CREATED Customer[NAME: {}, EMAIL: {}",
                customer.getCustomerName(), customer.getCustomerEmail());

        return ResponseEntity.ok("Customer created!");
    }
}
