package com.gsdai.searchengine.service.impl;

import com.gsdai.searchengine.dao.DataDao;
import com.gsdai.searchengine.dao.SegmentDao;
import com.gsdai.searchengine.entity.Data;
import com.gsdai.searchengine.service.SearchService;
import com.gsdai.searchengine.utils.coreNLP.CoreNLPTokenizer;
import edu.stanford.nlp.ling.CoreLabel;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private DataDao dataDao;

    @Autowired
    private SegmentDao segmentDao;

    private final CoreNLPTokenizer coreNLPTokenizer = new CoreNLPTokenizer();

    @Override
    public Tuple2<List<Data>, Integer> search(String searchInfo, int pageSize, int pageNum) {
        String[] searchWords = searchInfo.split("\\s+");
        StringBuilder temp = new StringBuilder();
        for (String searchWord : searchWords) {
            temp.append(' ');
            temp.append(searchWord.toLowerCase());
        }

        int offset = pageSize * (pageNum - 1);
        searchInfo = temp.toString().trim();
        System.out.println("searchInfo " + searchInfo);
        StringBuilder stringBuilder = new StringBuilder();
        List<CoreLabel> segTokens = coreNLPTokenizer.process(searchInfo);

        boolean flag = true;
        for (CoreLabel segToken : segTokens) {
            String word = segToken.word().trim();
            if ("".equals(word)) continue;
            if (segmentDao.selectSegByWord(word) == null) continue;
            int segId = segmentDao.selectSegByWord(word).getId();
            int idx = segId % 100;
            if (flag) {
                stringBuilder.append("select * from data_seg_relation_").append(idx).append(" where seg_id = ").append(segId).append('\n');
                flag = false;
            } else {
                stringBuilder.append("union").append('\n');
                stringBuilder.append("select * from data_seg_relation_").append(idx).append(" where seg_id = ").append(segId).append('\n');
            }
        }
        String sql = stringBuilder.toString();
        if ("".equals(sql)) return null;

        List<Data> data = dataDao.getDataBySplit(sql, pageSize, offset);
        int pageCount = dataDao.getDataCount(sql) / pageSize + 1;
        return Tuple.of(data, pageCount);
    }
}
