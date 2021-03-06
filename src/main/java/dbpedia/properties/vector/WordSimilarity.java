package dbpedia.properties.vector;

        import org.datavec.api.util.ClassPathResource;
        import org.deeplearning4j.models.word2vec.Word2Vec;
        import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
        import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
        import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
        import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
        import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import java.util.Collection;



public class WordSimilarity {

    private static Logger log = LoggerFactory.getLogger(WordSimilarity.class);

    public static void main(String[] args) throws Exception {

        // Gets Path to Text file
        String filePath = new ClassPathResource("data.txt").getFile().getAbsolutePath();

        log.info("Load & Vectorize Sentences....");
        // Strip white space before and after for each line
        SentenceIterator iter = new BasicLineIterator(filePath);
        // Split on white spaces in the line to get words
        TokenizerFactory t = new DefaultTokenizerFactory();

        /*
            CommonPreprocessor will apply the following regex to each token: [\d\.:,"'\(\)\[\]|/?!;]+
            So, effectively all numbers, punctuation symbols and some special symbols are stripped off.
            Additionally it forces lower case for all tokens.
         */
        t.setTokenPreProcessor(new CommonPreprocessor());

        log.info("Building model....");
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(5)
                .iterations(1)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        log.info("Fitting Word2Vec model....");
        vec.fit();

        log.info("Writing word vectors to text file....");

        // Prints out the closest 10 words to "day". An example on what to do with these Word Vectors.
        log.info("Closest Words:");
        Collection<String> lst = vec.wordsNearest("paris", 1);
        System.out.println("Words near to 'paris': " + lst);

        // TODO resolve missing UiServer
//        UiServer server = UiServer.getInstance();
//        System.out.println("Started on port " + server.getPort());
    }
}


