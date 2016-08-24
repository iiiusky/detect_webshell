package chaitin.webshell.decoder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QueryString extends Decoder{
	
    @Override
    List<byte[]> decode(byte[] input) {
        List<byte[]> result = new LinkedList<byte[]>();
        return result;
    }

    public static List<Pair> query_string(byte[] input) {
        List<Pair> result = new LinkedList<Pair>();
        int pos = 0;
        while (pos < input.length) {
            int pos_key_start = pos;

            while (pos < input.length
                    && input[pos] != '='
                    && input[pos] != '&') {
                pos += 1;
            }
            int pos_key_end = pos;

            int pos_value_start = 0;
            int pos_value_end = 0;

            if (pos < input.length
                    && input[pos] == '=') {
                pos += 1;
                pos_value_start = pos;
                while (pos < input.length
                        && input[pos] != '&') {
                    pos += 1;
                }
                pos_value_end = pos;
            }

            if (pos < input.length && input[pos] == '&') {
                pos += 1;
            }

            byte[] key = Unquote.unquote(Arrays.copyOfRange(input, pos_key_start, pos_key_end));
            byte[] value = Unquote.unquote(Arrays.copyOfRange(input, pos_value_start, pos_value_end));
            if (key.length != 0 || value.length != 0) {
                Pair kv_pair = new Pair(key, value);
                result.add(kv_pair);
            }
        }
        return result;
    }
}