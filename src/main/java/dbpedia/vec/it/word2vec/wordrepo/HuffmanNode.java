package dbpedia.vec.it.word2vec.wordrepo;

import lombok.Data;
import lombok.NonNull;


@Data
public class HuffmanNode {
    @NonNull
    private byte[] code;
    @NonNull
    private int[] point;
    private int idx;
    private byte length;

    public HuffmanNode() {

    }

    public HuffmanNode(byte[] code, int[] point, int index, byte length) {
        this.code = code;
        this.point = point;
        this.idx = index;
        this.length = length;
    }
}