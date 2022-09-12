package com.gsdai.searchengine.service.impl;

import com.gsdai.searchengine.dao.SegmentDao;
import com.gsdai.searchengine.entity.Segment;
import com.gsdai.searchengine.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SegmentServiceImpl implements SegmentService {

    @Autowired
    private SegmentDao segmentDao;

    @Override
    public List<Segment> selectAllSeg() {
        return segmentDao.selectAllSeg();
    }
}
