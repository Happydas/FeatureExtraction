package dbpedia.vec.format.tokenization.tokenizerfactory;

import dbpedia.vec.format.tokenization.tokenizer.DefaultStreamTokenizer;
import dbpedia.vec.format.tokenization.tokenizer.DefaultTokenizer;
import dbpedia.vec.format.tokenization.tokenizer.TokenPreProcess;
import dbpedia.vec.format.tokenization.tokenizer.Tokenizer;

import java.io.InputStream;


public class DefaultTokenizerFactory implements TokenizerFactory {

    private TokenPreProcess tokenPreProcess;

    @Override
    public Tokenizer create(String toTokenize) {
        DefaultTokenizer t = new DefaultTokenizer(toTokenize);
        t.setTokenPreProcessor(tokenPreProcess);
        return t;
    }

    @Override
    public Tokenizer create(InputStream toTokenize) {
        Tokenizer t = new DefaultStreamTokenizer(toTokenize);
        t.setTokenPreProcessor(tokenPreProcess);
        return t;
    }

    @Override
    public void setTokenPreProcessor(TokenPreProcess preProcessor) {
        this.tokenPreProcess = preProcessor;
    }

    /**
     * Returns TokenPreProcessor set for this TokenizerFactory instance
     *
     * @return TokenPreProcessor instance, or null if no preprocessor was defined
     */
    @Override
    public TokenPreProcess getTokenPreProcessor() {
        return tokenPreProcess;
    }


}