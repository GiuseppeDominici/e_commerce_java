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

import e_commerce_final.e_commerce.entities.Product;
import e_commerce_final.e_commerce.servicies.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity addProduct(@RequestBody Product p){
        try {
            return ResponseEntity.ok(productService.addProduct(p));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity deleteProduct(@RequestParam("codP")String codP){
        try {
            return ResponseEntity.ok(productService.deleteProduct(codP));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modifyProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity modifyProduct(@RequestParam("codP")String codP, @RequestBody Product p){
        try {
            return ResponseEntity.ok(productService.modifyProduct(codP, p));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    
    @GetMapping("/getProduct")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity getProduct(@RequestParam("codP")String codP){
        try {
            return ResponseEntity.ok(productService.getProduct(codP));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getAllProducts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getAllProducts(@RequestParam("nPage")int nPage,@RequestParam("dPage")int dPage){
        try {
            return ResponseEntity.ok(productService.getAllProducts(nPage, dPage));
        } catch (Exception e) {
            return new ResponseEntity(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
}
