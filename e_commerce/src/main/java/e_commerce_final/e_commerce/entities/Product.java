package e_commerce_final.e_commerce.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private long id;

    @Column(name="nameP")
    private String nameP;

    @Column(name="category")
    private String category;

    @Column(name="code", nullable = false, unique = true)
    private String codP;

    @Column(name="price", nullable = false)
    private double price;

    @Column(name="qty")
    private int qty;

    @Version
    @Column(name="version")
    private int version;
}
