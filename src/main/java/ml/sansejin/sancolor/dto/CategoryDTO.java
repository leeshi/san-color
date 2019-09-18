package ml.sansejin.sancolor.dto;

/**
 * 说明：该类关联了tbl_category_info,主要用于获取对应的分类的信息
 * 对应的Api是/api/category/{id}
 * @author sansejin
 */

public class CategoryDTO {
    private Long categoryId;
    private String name;
    private Integer number;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
