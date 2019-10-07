package ml.sansejin.sancolor.service.impl;

import ml.sansejin.sancolor.dao.ArticleCategoryMapper;
import ml.sansejin.sancolor.dao.CategoryMapper;
import ml.sansejin.sancolor.dto.CategoryDTO;
import ml.sansejin.sancolor.entity.ArticleCategory;
import ml.sansejin.sancolor.entity.ArticleCategoryExample;
import ml.sansejin.sancolor.entity.Category;
import ml.sansejin.sancolor.entity.CategoryExample;
import ml.sansejin.sancolor.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
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
    private ArticleCategoryMapper articleCategoryMapper;

    /**
     * 增加一种分类，需要考虑分类已经存在的情况
     * TODO 若不允许重名的情况出现，是否需要在数据库设计时就设定好
     * @param categoryDTO
     * @return boolean
     */
    @Transactional
    @Override
    public boolean addCategory(@NotNull CategoryDTO categoryDTO) {
/*
        if (ifCategoryExit(categoryDTO.getName())){
            return false;
        }
*/

        //数据库中不存在名字相同的记录
        Category category = new Category();

        category.setName(categoryDTO.getName());
        category.setCreate_by(new Date());

        categoryMapper.insertSelective(category);

        return true;
    }

    /**
     * 更新一个分类的信息（主要是category_name）
     * @param categoryDTO
     * @return boolean
     */
    @Transactional
    @Override
    public boolean updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        //首先判断是否存在相应的记录，如果不存在，那么就直接返回false
        if (!ifCategoryExit(categoryId)){
            return false;
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setModified_by(new Date());
        category.setId(categoryId);

        categoryMapper.updateByPrimaryKeySelective(category);

        return true;
    }

    /**
     * 更新文章的分类信息，因为一篇文章可以属于多个分类，所以会有多条记录.所以本函数首先删除同一篇文章的所有分类
     *      然后逐个添加
     * @param articleId
     * @param listCategoryId
     * @return boolean
     * @implNote 预计的api思路为，前端一次性发送所有文章对应的分类，其余问题由后端自行处理
     *          注意提供的CategoryId需要一定存在,否则就会发送错误
     */
    @Transactional
    @Override
    public boolean updateArticleCategory(Long articleId, List<Long> listCategoryId) {
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setArticle_id(articleId);
        articleCategory.setCreate_by(new Date());

        ArticleCategoryExample example = new ArticleCategoryExample();
        example.createCriteria().andArticle_idEqualTo(articleId);

        //删除所有的记录
        articleCategoryMapper.deleteByExample(example);

        for(Long categoryId : listCategoryId) {
            articleCategory.setCateory_id(categoryId);
            articleCategoryMapper.insertSelective(articleCategory);
        }

        return true;
    }

    /**
     * 需要注意number是否为0
     * @param categoryId
     * @return boolean
     * @implNote 请保证在分类存在的情况下再进行操作
     */
    @Transactional
    @Override
    public boolean deleteCategoryById(Long categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);

        if(category.getNumber().equals(0)) {
            categoryMapper.deleteByPrimaryKey(categoryId);
            return true;
        }else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean ifCategoryExit(Long categoryId) {
        if (categoryMapper.selectByPrimaryKey(categoryId) == null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 通过categoryId来获取相应的分类DTO
     * TODO 是否需要先检查id存在与否
     * @param id
     * @return
     */
    @Transactional
    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryMapper.selectByPrimaryKey(id);

        if (category == null){
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setNumber(category.getNumber());
        categoryDTO.setName(category.getName());
        categoryDTO.setCategoryId(category.getId());

        return categoryDTO;
    }

    /**
     * 返回所有的分类信息
     * @return
     */
    @Transactional
    @Override
    public List<CategoryDTO> listAllCategories() {
        List<Category> listCategory = categoryMapper.selectByExample(new CategoryExample());

        List<CategoryDTO> listCategoryDTO = new ArrayList<>();

        for (Category category : listCategory){
            CategoryDTO categoryDTO = new CategoryDTO();

            categoryDTO.setCategoryId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setNumber(category.getNumber());

            listCategoryDTO.add(categoryDTO);
        }

        return listCategoryDTO;
    }

}
