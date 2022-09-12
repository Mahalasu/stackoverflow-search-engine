package com.gsdai.searchengine.dao;

import com.gsdai.searchengine.entity.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDao {
    List<Data> getAllData();

    int getDataCount(@Param("sql") String sql);

    List<Data> getDataBySplit(@Param("sql") String sql, @Param("pageSize") int pageSize, @Param("offset") int offset);
}
