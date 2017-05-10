package ch.heigvd.res.labs.http.utils;

import java.io.*;

public class ReadResponseFilterInputStream extends FilterInputStream {

    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param in the underlying input stream, or <code>null</code> if
     *           this instance is to be created without an underlying stream.
     */
    public ReadResponseFilterInputStream(InputStream in) {
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
