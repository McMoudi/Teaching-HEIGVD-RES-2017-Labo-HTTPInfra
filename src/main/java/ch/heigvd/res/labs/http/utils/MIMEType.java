package ch.heigvd.res.labs.http.utils;

    public enum MIMEType {
        APPLICATION_JSON("application/json"),
        APPLICATION_XML("application/xml"),
        TEXT_ALL("text/*"),
        TEXT_HTML("text/html");

        private final String formatStr;

        MIMEType(String formatStr) {
            this.formatStr = formatStr;
        }

        public String getFormat() {
            return formatStr;
        }

        public static MIMEType getMimeType(String formatStr) {
            for(MIMEType m : MIMEType.values()) {
                if(m.getFormat().equals(formatStr))
                    return m;
            }
            return null;
        }
    }
