package ml.sansejin.sancolor.web.controller;

import ml.sansejin.sancolor.dto.ArticleCategoryDTO;
import ml.sansejin.sancolor.dto.CategoryDTO;
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


    @GetMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<CategoryDTO>> fetchAllCategories(){
        List<CategoryDTO> listCategoryDTO = categoryService.listAllCategories();

        return new ResponseEntity<>(listCategoryDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{categoryId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<CategoryDTO> fetchCategory(@PathVariable Long categoryId){
        CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);

        if (categoryDTO != null) {
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{categoryId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        if (!categoryService.ifCategoryExit(categoryId)){
            //不存在对应的记录
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (categoryService.deleteCategoryById(categoryId)){
            //number等于0,删除成功
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            //number并不等于0，请求失败
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> addCategory(@RequestBody CategoryDTO categoryDTO){
        if (categoryService.addCategory(categoryDTO)){
           return new ResponseEntity<>(HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{categoryId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO){
        if (categoryService.updateCategory(categoryId, categoryDTO)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/article/{articleId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> updateArticleCategory(@PathVariable Long articleId, @RequestBody List<Long> listCategoryId){
        if (!articleService.ifArticleExit(articleId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        categoryService.updateArticleCategory(articleId, listCategoryId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
