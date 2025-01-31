package productManagement.repositories;

import productManagement.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {

    Page<Products> findByProductNameContainingIgnoreCase(String productName,Pageable pageable);
    Page<Products> findByDescriptionContainingIgnoreCase(String description ,Pageable pageable);
    Page<Products> findByCategoryIgnoreCase(String category ,Pageable pageable);
    Page<Products> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String productName,String description ,Pageable pageable);
    Page<Products> findByPriceBetween(double minPrice,double maxPrice ,Pageable pageable);

    Page<Products> findAll(Pageable pageable);

}
