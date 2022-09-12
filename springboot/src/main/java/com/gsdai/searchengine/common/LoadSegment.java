package com.gsdai.searchengine.common;

import com.gsdai.searchengine.dao.SegmentDao;
import com.gsdai.searchengine.entity.Segment;
import com.gsdai.searchengine.utils.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class LoadSegment {

    public static Trie trie = new Trie();

    @Autowired
    private SegmentDao segmentDao;

    @PostConstruct
    public void init() {
        List<Segment> segmentList = segmentDao.selectAllSeg();
        for (Segment segment : segmentList) {
            trie.add(segment.getWord());
        }
    }
}
