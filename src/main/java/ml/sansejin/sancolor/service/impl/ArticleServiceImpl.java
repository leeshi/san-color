package ml.sansejin.sancolor.service.impl;

import ml.sansejin.sancolor.dao.*;
import ml.sansejin.sancolor.dto.ArticleDTO;
import ml.sansejin.sancolor.entity.*;
import ml.sansejin.sancolor.service.ArticleService;
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
    private ArticleCategoryMapper articleCategoryMapper;

    @Resource
    private ArticlePictureMapper articlePictureMapper;

    @Resource
    private ArticleUserMapper articleUserMapper;

    @Resource
    private ArticleContentMapper articleContentMapper;

    @Resource
    private ArticleCommentMapper articleCommentMapper;

    /**
     * 添加一篇文章，主要与其他表之间的关系
     * @param articleDTO
     * @return boolean
     */
    @Override
    @Transactional
    public boolean addArticle(@NotNull ArticleDTO articleDTO) {

        //------------------------插入tbl_article_info 记录--------------------------------
        Article article = new Article();

        article.setSummary(articleDTO.getSummary());
        article.setTitle(articleDTO.getTitle());
        article.setVisibillity(articleDTO.getVisibillity());
        article.setCreate_by(new Date());

        System.out.println(article);

        articleMapper.insertSelective(article);

        //先获取一个Example，然后再去进行查找
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andCreate_byEqualTo(article.getCreate_by()).andTitleEqualTo(article.getTitle());

        //搜寻具有相同信息的记录
        List<Article> listArticle = articleMapper.selectByExample(articleExample);
        if(listArticle.isEmpty()){
            return false;
        }

        article = listArticle.get(0);


        //------------------------插入tbl_article_content 记录----------------------------
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(articleDTO.getContent());
        //tbl_article_content 中id直接与tbl_article_info 中的id相同
        articleContent.setArticle_id(article.getId());

        articleContentMapper.insertSelective(articleContent);

        //------------------------插入tbl_article_user 记录--------------------------------
        ArticleUser articleUser = new ArticleUser();
        articleUser.setArticle_id(article.getId());
        articleUser.setUser_id(articleDTO.getUserId());
        articleUser.setCreate_by(new Date());

        articleUserMapper.insertSelective(articleUser);

        //------------------------插入tbl_article_pic 记录---------------------------------
        //如果图片地址不为空
        if(articleDTO.getPictureUrl().equals("")){
            ArticlePicture articlePicture = new ArticlePicture();

            articlePicture.setAtricle_id(article.getId());
            articlePicture.setPicture_url(articleDTO.getPictureUrl());

            articlePictureMapper.insert(articlePicture);
        }

        return true;

    }

    /**
     * 根据id删除一篇文章
     * @param id
     * @return boolean
     */
    @Override
    @Transactional
    public boolean deleteArticleById(Long id) {
        //-----------------------删除tbl_article_info 记录----------------------
        articleMapper.deleteByPrimaryKey(id);

        //-----------------------删除tbl_article_user 记录----------------------
        ArticleUserExample articleUserExample = new ArticleUserExample();
        articleUserExample.createCriteria().andArticle_idEqualTo(id);

        articleUserMapper.deleteByExample(articleUserExample);

        //-----------------------删除tbl_article_pic 记录-----------------------
        ArticlePictureExample articlePictureExample = new ArticlePictureExample();
        articlePictureExample.createCriteria().andAtricle_idEqualTo(id);

        articlePictureMapper.deleteByExample(articlePictureExample);

        //-----------------------删除tbl_article_category 记录------------------
        ArticleCategoryExample articleCategoryExample = new ArticleCategoryExample();
        articleCategoryExample.createCriteria().andArticle_idEqualTo(id);

        articleCategoryMapper.deleteByExample(articleCategoryExample);

        //-----------------------删除tbl_article_content 记录-------------------
        ArticleContentExample articleContentExample = new ArticleContentExample();
        articleCategoryExample.createCriteria().andArticle_idEqualTo(id);

        articleContentMapper.deleteByExample(articleContentExample);

        //TODO 是否需要保留评论，或者标记文章已经被删除
        //·----------------------删除tbl_article_comment 记录-------------------
        ArticleCommentExample articleCommentExample = new ArticleCommentExample();
        articleCommentExample.createCriteria().andArticle_idEqualTo(id);

        articleCommentMapper.deleteByExample(articleCommentExample);

        return true;
    }

    /**
     * 通过ArticleDTO更新article表的信息
     * @param articleDTO
     * @return boolean
     * @implNote 仅仅更新tbl_article_info 表的数据，其他表的数据更新需要调用其他接口
     */
    @Override
    @Transactional
    public boolean updateArticle(ArticleDTO articleDTO) {
        //新建一个Article对象
        Article article = new Article();

        article.setId(articleDTO.getId());

        article.setIs_effective(articleDTO.getEffective());
        article.setTraffic(articleDTO.getTraffic());
        article.setModified_by(new Date());
        article.setCreate_by(articleDTO.getCreateBy());
        article.setTitle(articleDTO.getTitle());
        article.setVisibillity(articleDTO.getVisibillity());
        article.setSummary(articleDTO.getSummary());

        articleMapper.updateByPrimaryKey(article);

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
     * TODO 由于数据库设计不合理，用户名还不可以进行显示
     * @param articleId
     * @return ArticleDTO
     */
    @Override
    @Transactional
    public ArticleDTO getArticleDTOById(Long articleId) {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        //若文章不存在则返回null
        if(article == null)
            return null;

        ArticleDTO articleDTO = new ArticleDTO();

        //tbl_article_info 信息
        articleDTO.setModifiedBy(article.getModified_by());
        articleDTO.setCreateBy(article.getCreate_by());
        articleDTO.setEffective(article.getIs_effective());
        articleDTO.setId(articleId);
        articleDTO.setVisibillity(article.getVisibillity());
        articleDTO.setTraffic(article.getTraffic());
        articleDTO.setTitle(article.getSummary());
        articleDTO.setSummary(article.getSummary());

        ArticlePictureExample articlePictureExample = new ArticlePictureExample();
        articlePictureExample.createCriteria().andAtricle_idEqualTo(articleId);
        //tbl_article_pic 信息
        List<ArticlePicture> listArticlePicture = articlePictureMapper.selectByExample(articlePictureExample);
        if(listArticlePicture.size() != 0){
            ArticlePicture articlePicture = listArticlePicture.get(0);
            articleDTO.setPictureUrl(articlePicture.getPicture_url());
        }


        ArticleUserExample articleUserExample = new ArticleUserExample();
        articleUserExample.createCriteria().andArticle_idEqualTo(articleId);
        //tbl_article_user 信息
        List<ArticleUser> listUser = articleUserMapper.selectByExample(articleUserExample);
        if(!listUser.isEmpty()) {
            articleDTO.setUserId(listUser.get(0).getUser_id());
            //articleDTO.setUserName(user.get);
        }


        return articleDTO;
    }


    /**
     * 该函数获取所有文章的DTO
     * TODO 如果文章过多，是否会造成很大的延迟
     * @return List<ArticleDTO>
     */
    @Override
    @Transactional
    public List<ArticleDTO> listAllArticles() {
        ArticleExample articleExample = new ArticleExample();
        //example 条件全部为空
        List<Article> listArticle = articleMapper.selectByExample(articleExample);

        List<ArticleDTO> listArticleDTO = new ArrayList<>();

        //循环获取单个DTO，最后组合成List
        for(Article article : listArticle){
            listArticleDTO.add(this.getArticleDTOById(article.getId()));
        }

        return null;
    }


    @Override
    @Transactional
    public List<ArticleDTO> listArticlesByCategoryId(Long categoryId) {
        return null;
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
        for(Article article : listArticle){
            listArticleDTO.add(this.getArticleDTOById(article.getId()));
        }

        return listArticleDTO;
    }

}
