package com.gsdai.searchengine.service.impl;

import com.gsdai.searchengine.dao.DataDao;
import com.gsdai.searchengine.entity.Data;
import com.gsdai.searchengine.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataDao dataDao;

    @Override
    public List<Data> getAllData() {
        return dataDao.getAllData();
    }

}
