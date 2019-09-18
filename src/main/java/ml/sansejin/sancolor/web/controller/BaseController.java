package ml.sansejin.sancolor.web.controller;

import ml.sansejin.sancolor.service.ArticleService;
import ml.sansejin.sancolor.service.CategoryService;
import ml.sansejin.sancolor.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sansejin
 * @className BaseController
 * @description 基础控制器
 * @create 9/18/19 4:00 PM
 **/
public class BaseController {
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;
    @Autowired
    CategoryService categoryService;
}
