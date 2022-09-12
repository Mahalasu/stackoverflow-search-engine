package com.gsdai.searchengine.utils.coreNLP;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Data
public class CoreNLPTokenizer {

    public List<CoreLabel> process(String sentence) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize");
        props.setProperty("tokenize.options", "splitHyphenated=false");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument doc = new CoreDocument(sentence);
        pipeline.annotate(doc);

        return new ArrayList<>(doc.tokens());
    }
}
