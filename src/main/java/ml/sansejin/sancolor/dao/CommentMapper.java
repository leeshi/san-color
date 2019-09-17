package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.Comment;
import ml.sansejin.sancolor.entity.CommentExample;

public interface CommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}