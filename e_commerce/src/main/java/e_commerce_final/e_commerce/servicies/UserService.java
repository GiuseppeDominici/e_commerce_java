package e_commerce_final.e_commerce.servicies;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import e_commerce_final.e_commerce.UTILITY.config.AuthenticationResponse;
import e_commerce_final.e_commerce.UTILITY.config.LoginRequest;
import e_commerce_final.e_commerce.UTILITY.config.RegisterRequest;
import e_commerce_final.e_commerce.UTILITY.exception.UserDoesNotExistsException;
import e_commerce_final.e_commerce.UTILITY.exception.UsersDoesNotExistsException;
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

    public AuthenticationResponse login(LoginRequest request) throws RuntimeException{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User u = userRepository.findByEmail(request.getEmail());
        if(u == null){
            throw new UserDoesNotExistsException();
        }
        String jwtToken = jwtService.generateToken(u);
        return new AuthenticationResponse(jwtToken);
    }

    public void deleteUser(String email)throws RuntimeException{
        User u = userRepository.findByEmail(email);
        if(u == null){throw new UserDoesNotExistsException();}
        userRepository.delete(u);
        return;
    }

    public User modifyUser(String email, User u)throws RuntimeException{
        User tmp = userRepository.findByEmail(email);
        if(tmp == null){throw new UserDoesNotExistsException();}
        tmp.setName(u.getName());
        tmp.setSurname(u.getSurname());
        tmp.setEmail(u.getEmail());
        tmp.setPassword(u.getPassword());
        return userRepository.save(tmp);
    }

    public User getUser(String email)throws RuntimeException{
        User u = userRepository.findByEmail(email);
        if (u==null){throw new UserDoesNotExistsException();}
        return u;
    }

    public List<User> getAllUsers()throws RuntimeException{
        // User tmp = userRepository.findByEmail(email);
        // Role role = Role.ADMIN;
        List<User> ul = userRepository.findAll();
            if(ul.size()==0){throw new UsersDoesNotExistsException();};
            return ul;
        // if(tmp.getRole().equals(role)){
            
        // }
        // return null;
    }
}
