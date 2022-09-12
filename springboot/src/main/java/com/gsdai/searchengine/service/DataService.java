package com.gsdai.searchengine.service;

import com.gsdai.searchengine.entity.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataService {
    List<Data> getAllData();
}
