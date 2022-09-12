package com.gsdai.searchengine.dao;

import com.gsdai.searchengine.entity.DataSegment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSegmentDao {
    void initSegmentTable(@Param("segments") List<String> segments);

    void createNewTable(@Param("tableName") String tableName);

    void initDataSegRelationTable(@Param("dataSegmentList") List<DataSegment> list, @Param("tableName") String tableName);
}
