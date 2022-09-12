package com.gsdai.searchengine.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private Integer id;
    private String url;
    private String caption;
    private String views;
    private String votes;
}
