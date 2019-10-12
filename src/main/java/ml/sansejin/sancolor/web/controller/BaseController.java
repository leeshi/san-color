package ml.sansejin.sancolor.web.controller;

import ml.sansejin.sancolor.service.ArticleService;
import ml.sansejin.sancolor.service.CategoryService;
import ml.sansejin.sancolor.service.CommentService;
import ml.sansejin.sancolor.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    SysService sysService;

    @Autowired
    HttpServletRequest request;
}
