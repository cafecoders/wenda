package com.nowcoder.dao;

import com.nowcoder.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */
@Mapper
public interface FeedDAO {
    String TABLE_NAME = "feed";
    String INSERT_FIELDS = " user_id, type, data, created_date  ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Select({"select * from ", TABLE_NAME, " where id = #{id}" })
    Feed getById(int id);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId},  #{type}," +
            "#{data}, #{createdDate})"})
    int addFeed(Feed feed);

    List<Feed> getUserFeeds(@Param("maxId") int maxId, @Param("userIds") List<Integer> userIds, @Param("count") int count);
}
