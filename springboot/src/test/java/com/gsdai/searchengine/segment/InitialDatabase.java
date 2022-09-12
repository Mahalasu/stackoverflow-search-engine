package com.gsdai.searchengine.segment;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.gsdai.searchengine.dao.DataSegmentDao;
import com.gsdai.searchengine.entity.Data;
import com.gsdai.searchengine.entity.DataSegment;
import com.gsdai.searchengine.entity.Segment;
import com.gsdai.searchengine.service.DataService;
import com.gsdai.searchengine.service.SegmentService;
import com.gsdai.searchengine.utils.coreNLP.CoreNLPTokenizer;
import com.gsdai.searchengine.utils.coreNLP.Keyword;
import com.gsdai.searchengine.utils.coreNLP.TFIDFAnalyzer;
import edu.stanford.nlp.ling.CoreLabel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SpringBootTest
public class InitialDatabase {

    @Autowired
    private DataService dataService;

    @Autowired
    private SegmentService segmentService;

    @Autowired
    private DataSegmentDao dataSegmentDao;

    private final TFIDFAnalyzer tfidfAnalyzer = new TFIDFAnalyzer();
    private final CoreNLPTokenizer coreNLPTokenizer = new CoreNLPTokenizer();
    static HashSet<String> stopWordsSet = new HashSet<>();

    @Test
    public void initSegmentTable() {
        // init
        List<String> segments = new ArrayList<>();
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 10000000);
        loadStopWords(stopWordsSet, this.getClass().getResourceAsStream("/nlp/coreNLP_English_stop_words.txt"));

        List<Data> data = dataService.getAllData();
        for (Data d : data) {
            String caption = d.getCaption();
            List<CoreLabel> segTokens = coreNLPTokenizer.process(caption);
            for (CoreLabel token : segTokens) {
                String word = token.word().toLowerCase();
                if (stopWordsSet.contains(word)) continue;
                if (!bloomFilter.mightContain(word)) {
                    bloomFilter.put(word);
                    segments.add(word);
                }
            }
        }
        dataSegmentDao.initSegmentTable(segments);
    }

    @Test
    public void initDataSegRelationTable() {
        List<Segment> segments = segmentService.selectAllSeg();
        Map<String, Integer> wordToId = new HashMap<>();
        for (Segment seg : segments) {
            wordToId.put(seg.getWord(), seg.getId());
        }

        loadStopWords(stopWordsSet, this.getClass().getResourceAsStream("/nlp/coreNLP_English_stop_words.txt"));

        Map<Integer, List<DataSegment>> dataSegmentListMap = new HashMap<>();
        int count = 0;

        List<Data> data = dataService.getAllData();
        for (Data d : data) {
            String caption = d.getCaption();
            List<CoreLabel> tokens = coreNLPTokenizer.process(caption);
            List<Keyword> keywords = tfidfAnalyzer.analyze(caption, 5);
            Map<String, DataSegment> segmentMap = new HashMap<>();
            for (CoreLabel token : tokens) {
                String word = token.word().toLowerCase();
                if (stopWordsSet.contains(word)) continue;
                if (!wordToId.containsKey(word)) continue;
                int segId = wordToId.get(word);
                int dataId = d.getId();
                double tfidfValue = 0;

                for (Keyword keyword : keywords) {
                    if (keyword.getName().toLowerCase().equals(word)) {
                        tfidfValue = keyword.getTfidfvalue();
                        break;
                    }
                }

                if (!segmentMap.containsKey(word)) {
                    segmentMap.put(word, new DataSegment(dataId, segId, tfidfValue, 1));
                } else {
                    DataSegment dataSegment = segmentMap.get(word);
                    dataSegment.setCount(dataSegment.getCount() + 1);
                    segmentMap.put(word, dataSegment);
                }
            }

            for (DataSegment dataSegment : segmentMap.values()) {
                int segId = dataSegment.getSegId();
                int idx = segId % 100;
                List<DataSegment> list = dataSegmentListMap.getOrDefault(idx, new ArrayList<DataSegment>());
                list.add(dataSegment);
                dataSegmentListMap.put(idx, list);
                count++;
            }
        }

        if (count > 0) {
            for (Integer idx : dataSegmentListMap.keySet()) {
                String tableName = "data_seg_relation_" + idx;
                dataSegmentDao.createNewTable(tableName);
                dataSegmentDao.initDataSegRelationTable(dataSegmentListMap.get(idx), tableName);
            }
        }
    }

    private void loadStopWords(Set<String> set, InputStream in) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                set.add(line.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
