package ch.heigvd.res.labs.http;

import ch.heigvd.res.labs.http.net.client.HttpClient;
import ch.heigvd.res.labs.http.utils.MIMEType;

import java.io.IOException;

public class HttpTests {
    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient();
        client.connect("www.heig-vd.ch");
        client.requestPage("www.heig-vd.ch", MIMEType.TEXT_ALL);
        client.disconnect();
    }
}
