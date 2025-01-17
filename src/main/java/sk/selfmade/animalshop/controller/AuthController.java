package sk.selfmade.animalshop.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sk.selfmade.animalshop.model.Role;
import sk.selfmade.animalshop.model.User;
import sk.selfmade.animalshop.payload.JwtResponse;
import sk.selfmade.animalshop.payload.LoginRequest;
import sk.selfmade.animalshop.payload.MessageResponse;
import sk.selfmade.animalshop.payload.SignupRequest;
import sk.selfmade.animalshop.repository.RoleRepository;
import sk.selfmade.animalshop.repository.UserRepository;
import sk.selfmade.animalshop.services.UserDetailsImpl;
import sk.selfmade.animalshop.utils.JwtUtils;

@RestController
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId().toString(), userDetails.getUsername(),
				userDetails.getEmail(), roles));
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		
		
		

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName("ROLE_USER")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "owner":
					Role modRole = roleRepository.findByName("ROLE_OWNER")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));

					Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
					UserDetailsImpl currentUserDetails = (UserDetailsImpl) currentAuthentication.getPrincipal();
					List<String> currentRoles = currentUserDetails.getAuthorities().stream().map(item -> item.getAuthority())
							.collect(Collectors.toList());
					
					if(!currentRoles.contains("ROLE_OWNER")) {
						throw new RuntimeException("Error: Error: Role 'ROLE_OWNER' need to be authorized by token.");
					}
					
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName("ROLE_USER")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}