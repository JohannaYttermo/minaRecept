package com.johanna.minaRecept.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.johanna.minaRecept.models.RecipeEntity;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    List<RecipeEntity> findByTitleContaining(String keyword);

    List<RecipeEntity> findTop3ByOrderByCreatedAtDesc();

    List<RecipeEntity> findAllByOrderByTitleAsc();





}

