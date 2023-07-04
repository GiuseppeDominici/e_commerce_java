package e_commerce_final.e_commerce.servicies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import e_commerce_final.e_commerce.UTILITY.config.AuthenticationResponse;
import e_commerce_final.e_commerce.UTILITY.config.LoginRequest;
import e_commerce_final.e_commerce.UTILITY.config.RegisterRequest;
import e_commerce_final.e_commerce.UTILITY.exceptions.NotEnoughMoneyException;
import e_commerce_final.e_commerce.UTILITY.exceptions.ProductDoesNotExistsException;
import e_commerce_final.e_commerce.UTILITY.exceptions.UserDoesNotExistsException;
import e_commerce_final.e_commerce.entities.Product;
import e_commerce_final.e_commerce.entities.ProductInCart;
import e_commerce_final.e_commerce.entities.User;
import e_commerce_final.e_commerce.repositories.ProductInCartRepository;
import e_commerce_final.e_commerce.repositories.ProductRepository;
import e_commerce_final.e_commerce.repositories.UserRepository;
import e_commerce_final.e_commerce.role.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductInCartRepository productInCartRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
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

    @Transactional
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

    @Transactional
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

    @Transactional
    public AuthenticationResponse modifyUser(String email, User u)throws RuntimeException{
        User tmp = userRepository.findByEmail(email);
        if(tmp == null){throw new UserDoesNotExistsException();}
        tmp.setName(u.getName());
        tmp.setSurname(u.getSurname());
        tmp.setEmail(u.getEmail());
        tmp.setPassword(u.getPassword());
        userRepository.save(tmp);

        String t = jwtService.generateToken(u);
        return new AuthenticationResponse(t);
    }

    public User getUser(String email)throws RuntimeException{
        User u = userRepository.findByEmail(email);
        if (u==null){throw new UserDoesNotExistsException();}
        return u;
    }

    public Page<User> getAllUsers(int nPage, int dPage){
        PageRequest page = PageRequest.of(nPage, dPage);
        Page<User> users = userRepository.findAll(page);
        return users;
    }

    @Transactional
    public User addProductToCart(String email, String codP, int q)throws RuntimeException{ 
        User u = userRepository.findByEmail(email);
        if(u==null){throw new UserDoesNotExistsException();}
        Product p = productRepository.findByCodP(codP);
        if(p==null){throw new ProductDoesNotExistsException();}
        ProductInCart productInCart = productInCartRepository.findByUserAndProduct(u, p);
        if(productInCart == null){
            ProductInCart pC = new ProductInCart(null, p, u, q);
            productInCartRepository.save(pC);
            return u;
        }else{
            productInCart.setQuantity(productInCart.getQuantity()+q);
            productInCartRepository.save(productInCart);
            return u;
        }
    }

    @Transactional
    public User removeProductFromCart(String email, String codP)throws RuntimeException{
        User u = userRepository.findByEmail(email);
        if(u==null){throw new UserDoesNotExistsException();}
        Product p = productRepository.findByCodP(codP);
        if(p==null){throw new ProductDoesNotExistsException();}
        ProductInCart pC = productInCartRepository.findByUserAndProduct(u, p);
        productInCartRepository.delete(pC);
        return u;
    }

    @Transactional
    public User getCart(String email)throws RuntimeException{
        User u = userRepository.findByEmail(email);
        if(u==null){throw new UserDoesNotExistsException();}
        return u;
    }


    @Transactional
    public User clearCart(String email)throws RuntimeException{
        User u = userRepository.findByEmail(email);
        if(u==null){throw new UserDoesNotExistsException();}
        productInCartRepository.deleteAll(u.getProductInCart());
        return userRepository.save(u);
    }

    @Transactional
    public User buy(String email, String codP)throws RuntimeException{
        User u = userRepository.findByEmail(email);
        if(u==null){throw new UserDoesNotExistsException();}
        Product p = productRepository.findByCodP(codP);
        if(p==null){throw new ProductDoesNotExistsException();}
        ProductInCart pC = productInCartRepository.findByUserAndProduct(u, p);
        if(pC==null){return null;}
        double saldo = p.getPrice()*pC.getQuantity();
        if(saldo>u.getBudget()){
            throw new NotEnoughMoneyException();
        }
        u.setBudget(u.getBudget() - saldo);
        u.setHasBuyed(true);
        userRepository.save(u);
        p.setQty(p.getQty() - pC.getQuantity());
        productRepository.save(p);
        removeProductFromCart(email, codP);
        return userRepository.save(u);
    }

    @Transactional 
    public User modifyQty(String email, String codP, int q)throws RuntimeException{
        User u = userRepository.findByEmail(email);
        if(u == null){throw new UserDoesNotExistsException();}
        Product p = productRepository.findByCodP(codP);
        if(p == null){throw new ProductDoesNotExistsException();}
        ProductInCart pC = productInCartRepository.findByUserAndProduct(u, p);
        if (pC==null){
            return null;
        }
        pC.setQuantity(q);
        productInCartRepository.save(pC);
        return userRepository.save(u);
    }

    
}
