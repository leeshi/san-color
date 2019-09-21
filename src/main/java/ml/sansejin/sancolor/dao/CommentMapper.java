package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.Comment;
import ml.sansejin.sancolor.entity.CommentExample;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}