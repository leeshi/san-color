package ml.sansejin.sancolor.service.impl;

import ml.sansejin.sancolor.dao.*;
import ml.sansejin.sancolor.dto.ArticleDTO;
import ml.sansejin.sancolor.entity.*;
import ml.sansejin.sancolor.exception.NoArticleContentException;
import ml.sansejin.sancolor.service.ArticleService;
import ml.sansejin.sancolor.util.MarkdownUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sansejin
 * @className ArticleServiceImpl
 * @description TODO
 * @create 9/18/19 5:05 PM
 **/
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticlePictureMapper articlePictureMapper;

    @Resource
    private ArticleContentMapper articleContentMapper;

    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    /**
     * 添加一篇文章，主要与其他表之间的关系
     * @param articleDTO
     * @return boolean
     */
    @Override
    @Transactional
    public boolean addArticle(@NotNull ArticleDTO articleDTO, String userName) {
        //------------------------插入tbl_article_info 记录--------------------------------
        Article article = new Article();
        Date addDate = new Date();

        //使用添加的日期来获取找回添加后的文章实体
        article.setSummary(articleDTO.getSummary());
        article.setTitle(articleDTO.getTitle());
        article.setVisibillity(articleDTO.getVisibillity());
        article.setCreate_by(addDate);
        article.setUser_name(userName);

        articleMapper.insertSelective(article);

        //先获取一个Example，然后再去进行查找
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andCreate_byEqualTo(addDate);

        //搜寻具有相同信息的记录
        List<Article> listArticle = articleMapper.selectByExample(articleExample);
        if(listArticle.isEmpty()){
            return false;
        }

        article = listArticle.get(0);


        //------------------------插入tbl_article_content 记录----------------------------
        ArticleContent articleContent = new ArticleContent();
        articleContent.setRaw_content(articleDTO.getContent());
        //tbl_article_content 中id直接与tbl_article_info 中的id相同
        //对markdown脚本进行解析
        articleContent.setParsed_content(MarkdownUtil.parseMarkdown(articleDTO.getContent()));
        articleContent.setArticle_id(article.getId());

        articleContentMapper.insertSelective(articleContent);

        //------------------------插入tbl_article_pic 记录---------------------------------
        //如果图片地址不为空
        if(articleDTO.getPictureUrl() != null && !articleDTO.getPictureUrl().equals("")){

            ArticlePicture articlePicture = new ArticlePicture();

            articlePicture.setAtricle_id(article.getId());
            articlePicture.setPicture_url(articleDTO.getPictureUrl());

            articlePictureMapper.insertSelective(articlePicture);

        }

        //------------------------插入tbl_category_article 记录----------------------------
        //TODO 检查category是否存在
        if (articleDTO.getCategoryId() != null){
            ArticleCategory articleCategory = new ArticleCategory();

            articleCategory.setCateory_id(articleDTO.getCategoryId());
            articleCategory.setArticle_id(article.getId());

            articleCategoryMapper.insertSelective(articleCategory);
        }

        return true;

    }

    /**
     * 根据id删除一篇文章
     * @param articleId
     * @return boolean
     */
    @Override
    @Transactional
    public boolean deleteArticleById(Long articleId, String userName) {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        if (article.getUser_name().equals(userName)) {
            articleMapper.deleteByPrimaryKey(articleId);
            return true;
        } else {
            return false;
        }

    }

    /**
     * 通过ArticleDTO更新article表的信息
     * @param articleDTO
     * @return boolean
     * @implNote 仅仅更新tbl_article_info 表的数据，其他表的数据更新需要调用其他接口
     * 管理员无法修改别人的文章
     */
    @Override
    @Transactional
    public boolean updateArticle(Long articleId, ArticleDTO articleDTO, String userName) {
        //检查用户是否有权修改文章
        Article article = articleMapper.selectByPrimaryKey(articleId);

        //TODO 检测article是否存在与检查用户是否为创建者 应不应该 在同一个地方进行
        if (article == null || !article.getUser_name().equals(userName)) {
            return false;
        }

        //TODO get时自动修改traffic
        article.setModified_by(new Date());
        article.setTitle(articleDTO.getTitle());
        article.setVisibillity(articleDTO.getVisibillity());
        article.setSummary(articleDTO.getSummary());
        //插入到tbl_article_info中，如果DTO中没有的info就不需要修改
        articleMapper.updateByPrimaryKeySelective(article);

        //如果DTO中content为null，表示不需要修改content
        if (articleDTO.getContent() != null) {
            ArticleContent articleContent = new ArticleContent();
            articleContent.setRaw_content(articleDTO.getContent());
            articleContent.setParsed_content(MarkdownUtil.parseMarkdown(articleDTO.getContent()));

            ArticleContentExample articleContentExample = new ArticleContentExample();
            articleContentExample.createCriteria().andArticle_idEqualTo(articleId);

            articleContentMapper.updateByExampleSelective(articleContent, articleContentExample);
        }

        return true;
    }

/*    *//**
     * 更新文章的分类信息，因为一篇文章可以属于多个分类，所以会有多条记录.所以本函数首先删除同一篇文章的所有分类
     *      然后逐个添加
     * @param articleId
     * @param listCategoryId
     * @return boolean
     * @implNote 预计的api思路为，前端一次性发送所有文章对应的分类，其余问题由后端自行处理
     *//*
    @Override
    @Transactional
    public boolean updateArticleCategory(Long articleId, List<Long> listCategoryId) {
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setArticle_id(articleId);

        ArticleCategoryExample example = new ArticleCategoryExample();
        example.createCriteria().andArticle_idEqualTo(articleId);

        //删除所有的记录
        articleCategoryMapper.deleteByExample(example);

        for(Long categoryId : listCategoryId) {
            articleCategory.setCateory_id(categoryId);
            articleCategoryMapper.insert(articleCategory);
        }

        return true;
    }*/


    /**
     * 对文章的图片地址进行修改
     * @param articleId
     * @param pictureUrl
     * @return boolean
     * @apiNote tbl_article_pic 记录中，articleId是唯一的
     */
    @Override
    @Transactional
    public boolean updateArticlePicture(Long articleId, String pictureUrl) {
        ArticlePicture articlePicture = new ArticlePicture();
        articlePicture.setPicture_url(pictureUrl);
        articlePicture.setModified_by(new Date());

        ArticlePictureExample example = new ArticlePictureExample();
        example.createCriteria().andAtricle_idEqualTo(articleId);

        articlePictureMapper.updateByExample(articlePicture, example);

        return true;
    }

    @Override
    public boolean isArticleExit(Long articleId) {
        if (articleMapper.selectByPrimaryKey(articleId) == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     *
     * @param articleId
     * @return ArticleDTO
     * TODO 使用连接操作来获取DTO
     */
    @Override
    @Transactional
    public ArticleDTO getArticleDTOById(Long articleId) throws NoArticleContentException {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        //若文章不存在则返回null
        if(article == null)
            return null;

        ArticleDTO articleDTO = new ArticleDTO();

        //tbl_article_info 信息
        articleDTO.setModifiedBy(article.getModified_by());
        articleDTO.setCreateBy(article.getCreate_by());
        articleDTO.setId(articleId);
        articleDTO.setVisibillity(article.getVisibillity());
        articleDTO.setTraffic(article.getTraffic());
        articleDTO.setTitle(article.getSummary());
        articleDTO.setSummary(article.getSummary());
        articleDTO.setUserName(article.getUser_name());

        //tbl_article_content 信息
        ArticleContentExample articleContentExample = new ArticleContentExample();
        articleContentExample.createCriteria().andArticle_idEqualTo(articleId);
        List<ArticleContent> listArticleContent = articleContentMapper.selectByExampleWithBLOBs(articleContentExample);
        //如果没有对应的文章信息，则抛出异常
        if (listArticleContent.isEmpty()){
            throw new NoArticleContentException();
        }

        //返回已经经过解析的html 文本
        articleDTO.setContent(listArticleContent.get(0).getParsed_content());

        //tbl_category_article 信息
        ArticleCategoryExample articleCategoryExample = new ArticleCategoryExample();
        articleCategoryExample.createCriteria().andArticle_idEqualTo(articleId);

        //暂时设置只有一个分类
        ArticleCategory articleCategory = articleCategoryMapper.selectByExample(articleCategoryExample).get(0);
        if (articleCategory != null){
            //获取ID信息后，需要自行调用 category API获取分类信息
            articleDTO.setCategoryId(articleCategory.getCateory_id());
        }

        //tbl_article_pic 信息
        ArticlePictureExample articlePictureExample = new ArticlePictureExample();
        articlePictureExample.createCriteria().andAtricle_idEqualTo(articleId);
        List<ArticlePicture> listArticlePicture = articlePictureMapper.selectByExample(articlePictureExample);
        //文章没有首图是不需要进行特殊处理的
        if(listArticlePicture.size() != 0){
            ArticlePicture articlePicture = listArticlePicture.get(0);
            articleDTO.setPictureUrl(articlePicture.getPicture_url());
        }


        return articleDTO;
    }


    @Override
    @Transactional
    public List<ArticleDTO> listArticlesByCategoryId(Long categoryId) {
        ArticleCategoryExample articleCategoryExample = new ArticleCategoryExample();
        articleCategoryExample.createCriteria().andCateory_idEqualTo(categoryId);

        List<ArticleCategory> listArticleCategory = articleCategoryMapper.selectByExample(articleCategoryExample);

        List<ArticleDTO> listArticleDTO = new ArrayList<>();

        for (ArticleCategory articleCategory : listArticleCategory) {
            try {
                listArticleDTO.add(this.getArticleDTOById(articleCategory.getArticle_id()));
            }catch (NoArticleContentException e) {
                e.printStackTrace();
            }
        }

        return listArticleDTO;
    }

    /**
     * 获取最新的文章DTO
     * @return List<ArticleDTO>
     */
    @Override
    @Transactional
    public List<ArticleDTO> listLatesetArticles() {
        ArticleExample articleExample = new ArticleExample();

        articleExample.setOrderByClause("create_by asc");

        List<Article> listArticle = articleMapper.selectByExample(articleExample);

        //TODO 如果没有文章，返回的是null还是empty list
        if(listArticle == null){
            return null;
        }

        List<ArticleDTO> listArticleDTO = new ArrayList<>();

        for (Article article : listArticle) {
            try {
                listArticleDTO.add(this.getArticleDTOById(article.getId()));
            }catch (NoArticleContentException e){
                e.printStackTrace();
            }
        }

        return listArticleDTO;
    }

}
