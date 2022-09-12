package com.gsdai.searchengine.service;

import com.gsdai.searchengine.entity.Data;
import io.vavr.Tuple2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
    Tuple2<List<Data>, Integer> search(String searchInfo, int pageSize, int pageNum);
}
