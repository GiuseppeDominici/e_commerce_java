package e_commerce_final.e_commerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import e_commerce_final.e_commerce.UTILITY.config.RegisterRequest;
import e_commerce_final.e_commerce.servicies.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest u){
        System.out.println("ciao");
        try {
            return ResponseEntity.ok(userService.register(u));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity registerAdmin(@RequestBody RegisterRequest u){
        return ResponseEntity.ok(userService.register(u));
    }
}
