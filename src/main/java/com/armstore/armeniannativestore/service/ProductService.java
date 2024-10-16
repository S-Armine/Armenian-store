package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.model.company.*;
import com.armstore.armeniannativestore.model.order.Order;
import com.armstore.armeniannativestore.model.order.OrderItem;
import com.armstore.armeniannativestore.repository.ProductRepository;
import com.armstore.armeniannativestore.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyService companyService;

    public ProductService(ProductRepository productRepository, CompanyService companyService) {
        this.productRepository = productRepository;
        this.companyService = companyService;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> search(String keyword) {
        List<Product> allProducts = findByNameContaining(keyword);
        allProducts.addAll(findByDescriptionContaining(keyword));
        return allProducts;
    }

    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> findByDescriptionContaining(String description) {
        return productRepository.findByDescriptionContainingIgnoreCase(description);
    }

    public List<Product> findByCompany(Company company) {
        return productRepository.findByCompany(company);
    }

    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    public Product updateProduct(Product oldProduct, Product newProduct) {
        oldProduct.setName(newProduct.getName());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setInStock(newProduct.getInStock());
        return productRepository.save(oldProduct);
    }

    @Transactional
    public Product updateProduct(Principal principal, Long productID, Product updatedProduct) {
        Product oldProduct = getProduct(principal, productID);
        return updateProduct(oldProduct, updatedProduct);
    }


    public void updateRating(Product product, Review review) {
        if (product.getReviews() == null) {
            product.setReviews(new HashSet<>());
        }
        product.getReviews().add(review);
        double newRating = product.getReviews().stream().mapToInt(Review::getRating).average().orElse(0);
        product.setRating(newRating);
        productRepository.save(product);
    }

    public Product getProduct(Principal principal, Long productID) {
        Company company = companyService.getCompany(principal);
        Product product = productRepository.findById(productID).orElseThrow(() -> new NotFoundException("Product not found"));
        if (!product.getCompany().equals(company)) {
            throw new NotFoundException("Product does not belong to this company");
        }
        return product;
    }

    @Transactional
    public void deleteProductFromCompany(Principal principal, Long productID) {
        Product product = getProduct(principal, productID);
        productRepository.delete(product);
    }

    public boolean isInActiveOrder(List<Product> products, Product product) {
        return products.stream().anyMatch(pr -> pr.equals(product));
    }

    @Transactional
    public Product createProduct(
            Principal principal, Category category, String name, String description,
            Integer price, Integer inStock, MultipartFile image) {
        Company company = companyService.getCompany(principal);
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setDescription(description);
        product.setPrice(price);
        product.setInStock(inStock);
        product.setCompany(company);
        try {
            uploadImage(image);
        } catch (IOException e) {
            return null;
        }
        String imagePath = "/images/products/" + image.getOriginalFilename();
        product.setImage(imagePath);
        productRepository.save(product);
        return product;
    }

    public void uploadImage(MultipartFile image) throws IOException {
        String fileName = "src/main/resources/static/images/products/" + image.getOriginalFilename();
        Path imagePath = Paths.get(fileName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public void updateInStockQuantity(Product product, int quantity) {
        productRepository.updateInStockQuantity(product.getId(), quantity);
    }

}
