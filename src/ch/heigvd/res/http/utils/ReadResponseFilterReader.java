package ch.heigvd.res.http.utils;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class ReadResponseFilterReader extends FilterReader {
    /**
     * Creates a new filtered reader.
     *
     * @param in a Reader object providing the underlying stream.
     * @throws NullPointerException if <code>in</code> is <code>null</code>
     */
    protected ReadResponseFilterReader(Reader in) {
        super(in);
    }

    public String readLine() throws IOException {
        boolean readBackslashR = false;
        String line = "";
        int c = in.read();
        while(c != -1 && (c != '\n' || !readBackslashR)) {
            if(c != '\r' && c != '\n')
                line += (char) c;
            if(c != '\n' && readBackslashR)
                line += '\r' + (char) c;
            readBackslashR = (c == '\r');
            c = in.read();
        }
        return line;
    }
}
