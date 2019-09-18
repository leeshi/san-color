package ml.sansejin.sancolor.service.impl;

import ml.sansejin.sancolor.dto.ArticleCategoryDTO;
import ml.sansejin.sancolor.dto.CategoryDTO;
import ml.sansejin.sancolor.entity.ArticleCategory;
import ml.sansejin.sancolor.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sansejin
 * @className CategoryServiceImpl
 * @description TODO
 * @create 9/18/19 8:09 PM
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ArticleCategoryDTO articleCategoryDTO;

    @Override
    public boolean addCategory(CategoryDTO categoryDTO) {
        return false;
    }

    @Override
    public boolean updateCategory(CategoryDTO categoryDTO) {
        return false;
    }

    @Override
    public boolean updateArticleCategory(ArticleCategory articleCategory) {
        return false;
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        return false;
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return null;
    }

    @Override
    public List<CategoryDTO> listAllCategories() {
        return null;
    }

    @Override
    public List<ArticleCategoryDTO> listAllArticleCategories() {
        return null;
    }
}
