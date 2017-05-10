package ch.heigvd.res.labs.http.utils;

public class MIMEType {
    private final String formatStr;

    public static final MIMEType APPLICATION_JSON = new MIMEType("application/json");
    public static final MIMEType APPLICATION_XML = new MIMEType("application/xml");
    public static final MIMEType TEXT_HTML = new MIMEType("text/html");

    public MIMEType(String formatStr) {
        this.formatStr = formatStr;
    }

    public String getFormat() {
        return formatStr;
    }
}
