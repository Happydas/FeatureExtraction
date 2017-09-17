package dbpedia.vec.format.tokenization.tokenizerfactory;

import dbpedia.vec.format.tokenization.tokenizer.TokenPreProcess;
import dbpedia.vec.format.tokenization.tokenizer.Tokenizer;

import java.io.InputStream;


public interface TokenizerFactory {

    /**
     * The tokenizer to createComplex
     * @param toTokenize the string to createComplex the tokenizer with
     * @return the new tokenizer
     */
    Tokenizer create(String toTokenize);

    /**
     * Create a tokenizer based on an input stream
     * @param toTokenize
     * @return
     */
    Tokenizer create(InputStream toTokenize);

    /**
     * Sets a token pre processor to be used
     * with every tokenizer
     * @param preProcessor the token pre processor to use
     */
    void setTokenPreProcessor(TokenPreProcess preProcessor);

    /**
     * Returns TokenPreProcessor set for this TokenizerFactory instance
     *
     * @return TokenPreProcessor instance, or null if no preprocessor was defined
     */
    TokenPreProcess getTokenPreProcessor();
}