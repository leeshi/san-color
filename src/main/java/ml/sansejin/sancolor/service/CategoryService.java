package ml.sansejin.sancolor.service;

import ml.sansejin.sancolor.dto.ArticleCategoryDTO;
import ml.sansejin.sancolor.dto.CategoryDTO;
import ml.sansejin.sancolor.entity.ArticleCategory;

import java.util.List;

public interface CategoryService {
    boolean addCategory(CategoryDTO categoryDTO);

    boolean updateCategory(CategoryDTO categoryDTO);

    //更新一个新的文章归类
    boolean updateArticleCategory(ArticleCategory articleCategory);

    boolean deleteCategoryById(Long id);

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> listAllCategories();

    List<ArticleCategoryDTO> listAllArticleCategories();

}