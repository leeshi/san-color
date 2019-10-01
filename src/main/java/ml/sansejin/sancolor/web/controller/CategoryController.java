package ml.sansejin.sancolor.web.controller;

import ml.sansejin.sancolor.dto.CategoryDTO;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sansejin
 * @className CategoryController
 * @description TODO
 * @create 9/22/19 5:12 PM
 **/

@RestController
@RequestMapping("/api/category")
public class CategoryController extends BaseController {
    private static final Logger logger = Logger.getLogger(CategoryController.class);

    @GetMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<CategoryDTO>> fetchAllCategories(){
        List<CategoryDTO> listCategoryDTO = categoryService.listAllCategories();

        logger.info("Fetch all categories");
        return new ResponseEntity<>(listCategoryDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{categoryId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<CategoryDTO> fetchCategory(@PathVariable Long categoryId){
        CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);

        if (categoryDTO != null) {
            logger.info(String.format("Category found! ID: %d", categoryId));
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        }else{
            logger.info(String.format("Category not found! ID: %d", categoryId));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{categoryId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        if (!categoryService.ifCategoryExit(categoryId)){
            //不存在对应的记录
            logger.info(String.format("Category not exit! ID: %d", categoryId));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (categoryService.deleteCategoryById(categoryId)){
            //number等于0,删除成功
            logger.info(String.format("Category deleted! ID: %d", categoryId));
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            //number并不等于0，请求失败
            logger.info(String.format("Category cannot be deleted(number is not 0)! ID: %d", categoryId));
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> addCategory(@RequestBody CategoryDTO categoryDTO){
        if (categoryService.addCategory(categoryDTO)){
            logger.info(String.format("Add a Category! NAME: %s", categoryDTO.getName()));
           return new ResponseEntity<>(HttpStatus.CREATED);
        }else{
            logger.info(String.format("Category exits! NAME: %s", categoryDTO.getName()));
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{categoryId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO){
        if (categoryService.updateCategory(categoryId, categoryDTO)){
            logger.info(String.format("Update a Category! ID: %d", categoryId));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            logger.info(String.format("Category not found! ID: %d", categoryId));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/article/{articleId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> updateArticleCategory(@PathVariable Long articleId, @RequestBody List<Long> listCategoryId){
        if (!articleService.isArticleExit(articleId)){
            logger.info(String.format("Article not found! ID: %d", articleId));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        categoryService.updateArticleCategory(articleId, listCategoryId);

        logger.info(String.format("Update all article-category record! ID: %d", articleId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
