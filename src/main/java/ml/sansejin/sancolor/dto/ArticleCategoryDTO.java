package ml.sansejin.sancolor.dto;


/**
 * 说明：该类关联了tbl_article_category，用于获取文章所对应的的分类
 * 对应的Api为/api/category/article/{id}
 * @author sansejin
 */
public class ArticleCategoryDTO {
    //tbl_article_category基础字段
    private Long articleId;
    private Long articleCategoryId;
    private Long categoryId;

    //tbl_category_info基础字段
    private String categoryName;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(Long articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
