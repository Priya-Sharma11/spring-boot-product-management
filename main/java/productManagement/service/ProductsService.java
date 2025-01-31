package productManagement.service;

import productManagement.entities.Products;
import productManagement.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    private final int PAGE_SIZE =5;
    @Autowired
    private ProductsRepository productsRepository;

    public Products createProduct(Products product) {
        return productsRepository.save(product);
    }

    private Pageable createPageable(int page, String sortBy, String order) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, PAGE_SIZE, Sort.by(direction, sortBy));
    }
    public Page<Products> getAllProducts(int page,String sortBy,String order) {
        Pageable pageable = createPageable(page, sortBy, order);
        return productsRepository.findAll(pageable);
    }

    //filters
   public Page<Products> getAllProductsByNameOrDescription(String productName ,String description,int page, String sortBy, String order){
       Pageable pageable = createPageable(page, sortBy, order);
        return productsRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(productName,description,pageable);
    }
    public Page<Products> getProductsByProductName(String productName,int page, String sortBy, String order){
        Pageable pageable = createPageable(page, sortBy, order);
        return productsRepository.findByProductNameContainingIgnoreCase(productName,pageable);
    }
    public Page<Products> getProductsByDescription(String description,int page, String sortBy, String order){
        Pageable pageable = createPageable(page, sortBy, order);
        return productsRepository.findByDescriptionContainingIgnoreCase(description,pageable);
    }
    public Page<Products> getAllProductsByCategory(String category,int page, String sortBy, String order){
        Pageable pageable = createPageable(page, sortBy, order);
        return productsRepository.findByCategoryIgnoreCase(category,pageable);
    }

    public Page<Products> getProductsByPriceRange(double minPrice,double maxPrice,int page, String sortBy, String order){
        Pageable pageable = createPageable(page, sortBy, order);
        return productsRepository.findByPriceBetween(minPrice,maxPrice,pageable);
    }


    //sorting
    public List<Products> getProductSorted(String sortBy,String order){
        Sort.Direction direction = order.equalsIgnoreCase("desc")?Sort.Direction.DESC:Sort.Direction.ASC;
        return productsRepository.findAll(Sort.by(direction,sortBy));
    }


}
