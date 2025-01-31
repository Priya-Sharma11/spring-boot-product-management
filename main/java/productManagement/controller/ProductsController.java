package productManagement.controller;

import productManagement.entities.Products;
import productManagement.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @PostMapping("/create")
    public ResponseEntity<Products> createProducts(@RequestBody Products product){
        System.out.println("Received Product: " + product);
        try{
            Products savedProduct =  productsService.createProduct(product);
            return ResponseEntity.ok(savedProduct);
        }catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public Page<Products> getAllProducts(@RequestParam(defaultValue = "0")int page,
                                         @RequestParam(defaultValue = "price") String sortBy,
                                         @RequestParam(defaultValue = "asc") String order

    ){
        return productsService.getAllProducts(page,sortBy,order);
    }


    //Filters

    //filter by product name
    @GetMapping("/search/by-name-or-description")
    public Page<Products> getProductsByProductNameOrByDescription(@RequestParam(required = false ,defaultValue = "") String productName,
                                                                  @RequestParam(required = false,defaultValue = "") String description,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "price") String sortBy,
                                                                  @RequestParam(defaultValue = "asc") String order
    ){
        if(!productName.isBlank() && !description.isBlank()){
            return productsService.getAllProductsByNameOrDescription(productName,description,page,sortBy,order);
        }else if(!productName.isBlank()){
            return productsService.getProductsByProductName(productName,page,sortBy,order);
        }else if(!description.isBlank()){
            return productsService.getProductsByDescription(description,page,sortBy,order);
        }else  return productsService.getAllProducts(page,sortBy,order);
    }

    //filter by category
    @GetMapping("/search/by-category")
    public Page<Products> getAllProductsByCategory(@RequestParam(defaultValue = "") String category,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "price") String sortBy,
                                                   @RequestParam(defaultValue = "asc") String order
    ){
        return productsService.getAllProductsByCategory(category,page,sortBy,order);
    }
    //filter by price range
    @GetMapping("/filter")
    public Page<Products> getProductsByPriceRange(@RequestParam double minPrice,
                                                  @RequestParam double maxPrice,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "price") String sortBy,
                                                  @RequestParam(defaultValue = "asc") String order
    ){
        return  productsService.getProductsByPriceRange(minPrice,maxPrice,page,sortBy,order);
    }


    //sorting
    @GetMapping("/sorted")
    public List<Products> getProductsSorted(@RequestParam String sortBy, @RequestParam String order){
        return productsService.getProductSorted(sortBy,order);
    }


}
