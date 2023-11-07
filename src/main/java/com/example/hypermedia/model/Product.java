package com.example.hypermedia.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Product {
    private Long id;
    private String name;
    private double price;
    private String description;

    private List<Product> relatedProducts;


    public void addRelatedProduct(Product relatedProduct) {
        // Implement your logic to establish the relationship
        // This could be a list or another data structure
        // For simplicity, we'll assume a list of related products
        if (relatedProducts == null) {
            relatedProducts = new ArrayList<>();
        }
        relatedProducts.add(relatedProduct);
    }

    public Product getRelatedProduct(Long relatedProductId) {
        if (relatedProducts != null) {
            for (Product relatedProduct : relatedProducts) {
                if (relatedProduct.getId().equals(relatedProductId)) {
                    return relatedProduct;
                }
            }
        }
        return null;
    }
}
