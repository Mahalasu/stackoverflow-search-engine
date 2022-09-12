package com.gsdai.searchengine.controller;

import com.gsdai.searchengine.common.LoadSegment;
import com.gsdai.searchengine.entity.Data;
import com.gsdai.searchengine.service.SearchService;
import com.gsdai.searchengine.utils.Trie;
import io.vavr.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gsdai.searchengine.constants.ConstantInterface.resultNumInOnePage;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/api/search")
    public Tuple2<List<Data>, Integer> search(@RequestParam("keyword") String searchInfo, @RequestParam("pageNum") int pageNum) {
        return searchService.search(searchInfo, resultNumInOnePage, pageNum);
    }

    @GetMapping("/api/relative_segments")
    public List<String> getRelativeSegments(@RequestParam("word") String searchInfo) {
        Trie trie = LoadSegment.trie;
        return trie.getRelatedWords(searchInfo);
    }
}
