package com.gsdai.searchengine.dao;

import com.gsdai.searchengine.entity.Segment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SegmentDao {
    List<Segment> selectAllSeg();

    Segment selectSegByWord(@Param("word") String word);
}
