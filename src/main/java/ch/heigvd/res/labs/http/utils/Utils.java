package ch.heigvd.res.labs.http.utils;

public class Utils {
    public enum MIMETypes {
        APPLICATION_JSON("application/json"),
        APPLICATION_XML("application/xml"),
        APPLICATION_XHTML("application/xhtml"),
        TEXT_HTML("text/html");

        private final String formatStr;
        MIMETypes(String formatStr) {
            this.formatStr = formatStr;
        }

        public String getFormatStr() {
            return formatStr;
        }
    }
}
