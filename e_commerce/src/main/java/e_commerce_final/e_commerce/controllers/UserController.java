package e_commerce_final.e_commerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import e_commerce_final.e_commerce.UTILITY.DTOs.UserDTO;
import e_commerce_final.e_commerce.UTILITY.config.LoginRequest;
import e_commerce_final.e_commerce.UTILITY.config.RegisterRequest;
import e_commerce_final.e_commerce.entities.User;
import e_commerce_final.e_commerce.servicies.JwtService;
import e_commerce_final.e_commerce.servicies.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController{
    
    private final UserService userService;
    
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest u){
        try {
            return ResponseEntity.ok(userService.register(u));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity registerAdmin(@RequestBody RegisterRequest u){
        try {
            return ResponseEntity.ok(userService.registerAdmin(u));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest u){
        try {
            return ResponseEntity.ok(userService.login(u));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity deleteUser(HttpServletRequest t){
        try {
            String email = jwtService.getEmailFromT(t);
            userService.deleteUser(email);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modifyUser")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity modifyUser(HttpServletRequest t, @RequestBody User u){
        try {
            String email = jwtService.getEmailFromT(t);
            return ResponseEntity.ok(userService.modifyUser(email, u));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity getUser(HttpServletRequest t){
        try {
            String email = jwtService.getEmailFromT(t);
            User u = userService.getUser(email);
            UserDTO uD = new UserDTO(u);
            return ResponseEntity.ok(uD);
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST); 
        }
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getAllUsers(@RequestParam("nPage")int nPage,@RequestParam("dPage")int dPage){
        try {
            return ResponseEntity.ok(userService.getAllUsers(nPage, dPage));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addProductToCart")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity addProductToCart(HttpServletRequest t, @RequestParam("codP") String codP, @RequestParam("qty") int q){
        try{
            String email = jwtService.getEmailFromT(t);
            return ResponseEntity.ok(userService.addProductToCart(email, codP, q));
        }catch (Exception e){
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeProductFromCart")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity removeProductFromCart(HttpServletRequest t, @RequestParam("codP")String codP){
        try {
            String email = jwtService.getEmailFromT(t);
            return ResponseEntity.ok(userService.removeProductFromCart(email, codP));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/clearCart")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity clearCart(HttpServletRequest t){
        try {
            String email = jwtService.getEmailFromT(t);
            return ResponseEntity.ok(userService.clearCart(email));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCart")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getCart(HttpServletRequest t){
        try {
            String email = jwtService.getEmailFromT(t);
            return ResponseEntity.ok(userService.getCart(email));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/buy")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity buy(HttpServletRequest t, @RequestParam("codP")String codP){
        try {
            String email = jwtService.getEmailFromT(t);
            return ResponseEntity.ok(userService.buy(email, codP));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modifyQty")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity modifyQty(HttpServletRequest t, @RequestParam("codP")String codP, @RequestParam("qty")int q){
        try {
            String email = jwtService.getEmailFromT(t);
            return ResponseEntity.ok(userService.modifyQty(email, codP, q));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(),HttpStatus.BAD_REQUEST);
        }
    }

}
