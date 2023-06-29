package e_commerce_final.e_commerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class UserController {
    
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
    public ResponseEntity deleteUser(@RequestParam("email")String email){
        try {
            userService.deleteUser(email);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modifyUser")
    public ResponseEntity modifyUser(@RequestParam("email")String email, @RequestBody User u){
        try {
            return ResponseEntity.ok(userService.modifyUser(email, u));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity getUser(@RequestParam("email")String email){
        try {
            return ResponseEntity.ok(userService.getUser(email));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST); 
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers(@RequestParam("email")String email){
        try {
            return ResponseEntity.ok(userService.getAllUsers(email));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

}
