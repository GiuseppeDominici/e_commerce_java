package e_commerce_final.e_commerce.servicies;

import java.util.List;
import org.springframework.stereotype.Service;

import e_commerce_final.e_commerce.UTILITY.exception.ProductAlreadyExistsException;
import e_commerce_final.e_commerce.UTILITY.exception.ProductDoesNotExistsException;
import e_commerce_final.e_commerce.UTILITY.exception.UserDoesNotExistsException;
import e_commerce_final.e_commerce.entities.Product;
import e_commerce_final.e_commerce.entities.User;
import e_commerce_final.e_commerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;

    public Product addProduct(Product p)throws RuntimeException{
        Product tmp = productRepository.findByCodP(p.getCodP());
        if(tmp!=null){throw new ProductAlreadyExistsException();}
        return productRepository.save(p);
    }

    public String deleteProduct(String codP)throws RuntimeException{
        Product p = productRepository.findByCodP(codP);
        if(p==null){throw new ProductDoesNotExistsException();}
        productRepository.delete(p);
        return "Il prodotto è stato eliminato correttamente";
    }

    public Product modifyProduct(String codP, Product p){
        Product tmp = productRepository.findByCodP(codP);
        if(tmp == null){throw new ProductDoesNotExistsException();}
        tmp.setNameP(p.getNameP());
        tmp.setCategory(p.getCategory());
        tmp.setCodP(p.getCodP());
        tmp.setPrice(p.getPrice());
        tmp.setQuantity(p.getQuantity());
        return productRepository.save(tmp);
    }

    public Product getProduct(String codP)throws RuntimeException{
        Product p = productRepository.findByCodP(codP);
        if(p==null){throw new ProductDoesNotExistsException();}
        return p;
    }

    public List<Product> getAllProducts(){
        List<Product> lp = productRepository.findAll();
        if(lp.size()==0){return null;}
        return lp;
    }
}
