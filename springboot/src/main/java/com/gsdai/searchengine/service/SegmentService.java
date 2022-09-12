package com.gsdai.searchengine.service;

import com.gsdai.searchengine.entity.Segment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SegmentService {
    List<Segment> selectAllSeg();
}
