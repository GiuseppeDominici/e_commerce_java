package e_commerce_final.e_commerce.servicies;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import e_commerce_final.e_commerce.UTILITY.config.AuthenticationResponse;
import e_commerce_final.e_commerce.UTILITY.config.LoginRequest;
import e_commerce_final.e_commerce.UTILITY.config.RegisterRequest;
import e_commerce_final.e_commerce.UTILITY.exception.UserDoesNotExistException;
import e_commerce_final.e_commerce.entities.User;
import e_commerce_final.e_commerce.repositories.UserRepository;
import e_commerce_final.e_commerce.role.Role;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    
    public AuthenticationResponse register(RegisterRequest request) {
        
        User u = new User();
        u.setEmail(request.getEmail());
        u.setName(request.getName());
        u.setSurname(request.getSurname());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setRole(Role.USER);

        userRepository.save(u);
        String t = jwtService.generateToken(u);
        return new AuthenticationResponse(t);
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request){

        User u = new User();
        u.setEmail(request.getEmail());
        u.setName(request.getName());
        u.setSurname(request.getSurname());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setRole(Role.ADMIN);

        userRepository.save(u);
        String t = jwtService.generateToken(u);
        return new AuthenticationResponse(t);
    }

    public AuthenticationResponse authenticate(LoginRequest request) throws RuntimeException{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User u = userRepository.findByEmail(request.getEmail());
        if(u == null){
            throw new UserDoesNotExistException();
        }
        String jwtToken = jwtService.generateToken(u);
        return new AuthenticationResponse(jwtToken);
    }

}
