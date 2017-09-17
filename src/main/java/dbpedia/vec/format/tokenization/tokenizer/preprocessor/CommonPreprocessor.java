package dbpedia.vec.format.tokenization.tokenizer.preprocessor;

import dbpedia.vec.format.tokenization.tokenizer.TokenPreProcess;


public class CommonPreprocessor implements TokenPreProcess {
    @Override
    public String preProcess(String token) {
        return StringCleaning.stripPunct(token).toLowerCase();
    }
}