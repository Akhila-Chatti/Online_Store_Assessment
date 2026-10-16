package online.store.repositories;

import online.store.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT pc FROM ProductCategory pc ORDER BY pc.id")
    List<ProductCategory> findAll();

}
