package e_commerce_final.e_commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}
	// @Bean
    // CommandLineRunner run(UsersService userService, ProductsService productService){
    //     return args -> {
	// 		Users u1 = new Users(0, "Michele", "Bello", "bello@io.it", "123", false, 1200, null);
	// 		Users u2 = new Users(0, "Giovanni", "Brutto", "brutto@io.it", "456", false, 1200, null);
	// 		Users u3 = new Users(0, "Valentino", "Rossi", "thedoctor46@io.it", "789", false, 1200, null);

	// 		userService.saveUser(u1);
	// 		userService.saveUser(u2);
	// 		userService.saveUser(u3);

	// 		Products p1 = new Products(0, "Pizzetta", "Pizzeria", 1, 0.50, 10, null);
	// 		Products p2 = new Products(0, "Double CheeseBurger", "Paninoteca", 2, 4.50, 20, null);
	// 		Products p3 = new Products(0, "Arancino", "Rosticceria", 3, 1.20, 15, null);

	// 		productService.saveProduct(p1);
	// 		productService.saveProduct(p2);
	// 		productService.saveProduct(p3);
    //     };
    // }
}
