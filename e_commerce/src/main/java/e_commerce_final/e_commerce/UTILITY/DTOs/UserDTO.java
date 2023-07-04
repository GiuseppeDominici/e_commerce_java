package e_commerce_final.e_commerce.UTILITY.DTOs;

import e_commerce_final.e_commerce.entities.User;
import lombok.Data;

@Data
public class UserDTO {

    private String email;

    private boolean hasBuyed;

    public UserDTO(User u){
        this.email = u.getEmail();
        this.hasBuyed = u.isHasBuyed();
    }
}
