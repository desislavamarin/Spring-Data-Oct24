package com.example.advquerying.repositories;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findByBrand(String brand);
    List<Shampoo> findByBrandAndSize(String brand, Size size);
    List<Shampoo> findBySizeOrderById(Size size);
    List<Shampoo> findBySizeOrLabel(Size size, Label label);
    List<Shampoo> findBySizeOrLabelId(Size size, long labelId);
    List<Shampoo> findByLabelTitleContaining(String title);
    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);
    int countByPriceLessThan(BigDecimal price);
    List<Shampoo> findByIngredientsNameIn(List<String> names);
    @Query("SELECT s FROM Shampoo AS s " +
            "JOIN Ingredient AS i " +
            "WHERE i.name IN :names")
    List<Shampoo> findByIngredientsNameInQuery(List<String> names);
    @Query("SELECT s FROM Shampoo AS s " +
            "WHERE SIZE(s.ingredients) < :count")
    List<Shampoo> findByIngredientsCountLessThan(int count);
}
