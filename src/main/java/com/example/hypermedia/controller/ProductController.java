package com.example.hypermedia.controller;

import com.example.hypermedia.model.Product;
import com.example.hypermedia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{id}")
    public EntityModel<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);

        Link selfLink = linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel();
        Link relatedProductsLink = linkTo(methodOn(ProductController.class).getRelatedProducts(id)).withRel("related-products");

        return EntityModel.of(product, selfLink, relatedProductsLink);
    }

    @GetMapping("/products/{id}/related-products")
    public CollectionModel<EntityModel<Product>> getRelatedProducts(@PathVariable Long id) {
        List<Product> relatedProducts = productService.getRelatedProducts(id);

        List<EntityModel<Product>> productResources = relatedProducts.stream().map(product -> EntityModel.of(product, linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel())).collect(Collectors.toList());

        return CollectionModel.of(productResources);
    }

    @PostMapping("/products")
    public ResponseEntity<EntityModel<Product>> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);

        // Create a self-link for the newly created product
        EntityModel<Product> resource = EntityModel.of(createdProduct);
        resource.add(linkTo(methodOn(ProductController.class).getProduct(createdProduct.getId())).withSelfRel(), linkTo(methodOn(ProductController.class).getRelatedProducts(createdProduct.getId())).withRel("related-products"));

        // Build the URI for the newly created product
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdProduct.getId()).toUri();

        return ResponseEntity.created(location).body(resource);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<EntityModel<Product>> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product existingProduct = productService.getProduct(id);

        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        Product updated = productService.updateProduct(id, updatedProduct);

        // Create a self-link for the updated product
        EntityModel<Product> resource = EntityModel.of(updated);
        resource.add(linkTo(methodOn(ProductController.class).getProduct(updated.getId())).withSelfRel(), linkTo(methodOn(ProductController.class).getRelatedProducts(updated.getId())).withRel("related-products"));

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/products/{productId}/related-products/{relatedProductId}")
    public ResponseEntity<EntityModel<Product>> getRelatedProduct(
            @PathVariable Long productId,
            @PathVariable Long relatedProductId) {

        Product parentProduct = productService.getProduct(productId);
        if (parentProduct == null) {
            return ResponseEntity.notFound().build();
        }

        Product relatedProduct = parentProduct.getRelatedProduct(relatedProductId);
        if (relatedProduct == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Product> resource = EntityModel.of(relatedProduct);
        resource.add(
                linkTo(methodOn(ProductController.class).getRelatedProduct(productId, relatedProductId)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getProduct(productId)).withRel("parent-product")
        );

        return ResponseEntity.ok(resource);
    }

    @PostMapping("/products/{productId}/related-products")
    public ResponseEntity<EntityModel<Product>> createRelatedProduct(
            @PathVariable Long productId,
            @RequestBody Product relatedProductRequest) {

        Product parentProduct = productService.getProduct(productId);

        if (parentProduct == null) {
            return ResponseEntity.notFound().build();
        }

        // Create the related product from the request
        Product relatedProduct = productService.createProduct(relatedProductRequest);

        // Add the related product to the parent product
        parentProduct.addRelatedProduct(relatedProduct);

        // You may want to persist the changes in your data storage mechanism here

        EntityModel<Product> resource = EntityModel.of(parentProduct);
        resource.add(
                linkTo(methodOn(ProductController.class).getProduct(productId)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getRelatedProduct(productId, relatedProduct.getId())).withRel("related-product")
        );

        return ResponseEntity.created(linkTo(methodOn(ProductController.class)
                .getRelatedProduct(productId, relatedProduct.getId())).toUri()).body(resource);
    }
}
