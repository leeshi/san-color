# San-Color: A blog system based on SSM framework

## I.Data Transfer Object

1. **ArticleDTO**

```java
public class ArticleDTO {
    //tbl_article_info基础字段
    private Long id;
    private String summary;
    private String title;
    private Integer visibillity;
    private Integer traffic;
    private Date createBy;
    private Date modifiedBy;

    //tbl_article_user基础字段
    private Long userId;
    private String userName;

    //tbl_article_content基础字段
    private String content;

    //tbl_article_picture基础字段
    private String pictureUrl;

    //tbl_category_article基础字段
    private Long categoryId;
}
```

2. **CommentDTO**

```java
public class CommentDTO {
    private Long commentId;  //评论的id
    private String content;  //评论的内容
    private String userName; //用户名
    private String email;
    private String ip;
}
```

3. **CategoryDTO**

```java
public class CategoryDTO {
    private Long categoryId;
    private String name;
    private Integer number;
}
```

## II. RESTful APIs

1. **Article**:
    1. `fetchAllArticles: Method=GET, Url=/api/article/`  
	根据时间顺序获取最新的文章列表。  
	返回状态码*200*  与 List\<DTO\> 
	2. `fetchArticlesOfCategory: Method=GET, Url=/api/article/category/{categoryId}`  
	获取特定分类的所有文章。  
	若存在对应Category返回状态码*200*  与 List\<DTO\> ，否则返回*404*
	3. `fetchArticle: Method=GET, Url=/api/article/{articleId}`  
	获取指定ID的文章。  
	存在对应文章返回状态码*200* 与 DTO，否则返回*404*
	4. `addArticle: Method=POST, Url=/api/article/`  
	通过*Post Data* 增加一篇新的文章。注意:*createBy*, *modifiedBy*, *traffic*, *id*数据将不会被处理; *pictureUrl(null)*, *visibility(1)*可以为空，括号内为默认值。  
	创建成功返回状态码*201*
	5. `deleteArticle: Method=DELETE, Url=/api/article/{articleId}`  
	根据ID删除文章。  
	删除成功返回状态码*200*
	6. `updateArticle: Method=PUT, Url=/api/article/{articleId}`  
	根据ID更新文章信息。注意，原有文章的信息除了*createBy*, *modifiedBy*,*traffic*, *id*之外，其他信息都要包装到请求体中进行发送。  
	更新成功返回状态码*200*  
	***TODO: 只更新请求体中存在的数据域。***

2. **Comment**:
	1. `fetchAllComments: Method=GET, Url=/api/comment/`  
	获取所有的评论信息。  
	返回状态码*200* 与 List\<DTO\> 
	2. `fetchCountOfArticle: Method=GET, Url=/api/comment/count/{articleId}`  
	根据ID获取文章的评论数量。  
	返回如果文章存在，返回状态码*200* 与 Long  
	***TODO: 先检测文章是否存在，返回404***
	***TODO: 设计触发器，自动更新数据库中的值***
	3. `fetchCommentByArticleId: Method=GET, Url=/api/comment/aritcle/{articleId}`  
	根据ID获取文章的所有评论信息。  
	如果文章存在，返回状态码*404* 与 List\<DTO\> ，否则，返回*404*  
	4. `addComment: Method=GET, Url=/api/comment/`  
	通过*Post Data* 增加一条新的评论。注意:*createBy*, *modifiedBy*, *id*数据将不会被处理; *email(null)*可以为空; 其余的数据域均不可为空；*articleId*一定要对应存在的一篇文章。  
	如果对应的文章ID存在，且数据域格式正确，则添加成功，返回状态码*200*，否则返回状态码*409*  
	5. `updateComment: Method=PUT, Url=/api/comment/{commentId}`  
	通过ID更新评论信息。注意，原有文章的信息除了*createBy*, *modifiedBy*, *id*之外，其他信息都要包装到请求体中进行发送。  
	如果对应的文章ID存在，且数据域格式正确，则更新成功，返回状态码*200*，否则返回状态码*409*  
	***TODO: 设置不可以更改评论的文章ID，缩减需要发送的数据域***  
	6. `deleteComment: Method=DELETE, Url=/api/comment/{commentId}`  
	通过ID删除评论信息。  
	如果对应ID的评论存在，则返回状态码*200*，否则返回状态码*404*  

3. **Category**  
	1. `fetchAllCategories： Method=GET, Url=/api/category/`  
	获取所有分类信息。  
	返回状态码*200* 与 List\<DTO\>  
	2. `fetchCategory: Method=GET, Url=/api/category/{categoryId}`  
	根据ID获取分类信息。  
	如果对应ID分类存在，则返回状态码*200* 与 DTO，否则返回*404*  
	3. `deleteCategory: Method=DELETE, Url=/api/category/{cateforyId}`  
	根据ID删除分类信息。  
	如果对应ID分类存在，则返回状态码*200*，否则返回*404*
	4. `addCategory: Method=POST, Url=/api/category/`  
	通过*Post Data* 增加一个新的分类。注意:*createBy*, *modifiedBy*, *id*数据将不会被处理; 其余的数据域均不可为空。
	如果数据域格式正确，则添加成功，返回状态码*200*，否则返回状态码*409*  
	5. `updateCategory: Method=PUT, Url=/api/categoty/{categoryId}`  
	通过ID更新分类信息。注意，原有分类的信息除了*createBy*, *modifiedBy*, *id*之外，其他信息都要包装到请求体中进行发送。  
	如果数据域格式正确，则更新成功，返回状态码*200*，否则返回状态码*409* 
	6. `updateArticleCategory: Method=PUT, Url=/api/category/article/{articleId}`  
	通过ID更新文章所属的分类，*Put Data* 为一个分类的ID。  
	如果对应文章ID存在，并且分类ID也存在，则返回状态码*201*，否则返回*404*  
	**TODO: 先进行分类与文章是否存在再进行请求处理***