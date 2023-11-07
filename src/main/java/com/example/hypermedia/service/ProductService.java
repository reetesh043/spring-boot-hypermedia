package com.example.hypermedia.service;

import com.example.hypermedia.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private long nextId = 1;

    public Product createProduct(Product product) {
        product.setId(nextId++);
        products.add(product);
        return product;
    }

    public Product getProduct(long id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public Product updateProduct(long id, Product updatedProduct) {
        for (Product product : products) {
            if (product.getId() == id) {
                product.setName(updatedProduct.getName());
                product.setDescription(updatedProduct.getDescription());
                product.setPrice(updatedProduct.getPrice());
                return product;
            }
        }
        return null;
    }

    public boolean deleteProduct(long id) {
        return products.removeIf(product -> product.getId() == id);
    }

    public List<Product> getRelatedProducts(long productId) {
        // Simulated method to retrieve related products based on your business logic
        // For this example, we assume related products are all other products except the one with productId
        List<Product> relatedProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getId() != productId) {
                relatedProducts.add(product);
            }
        }
        return relatedProducts;
    }

}
