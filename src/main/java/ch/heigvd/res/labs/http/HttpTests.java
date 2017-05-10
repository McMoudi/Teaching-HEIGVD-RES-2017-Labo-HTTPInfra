package ch.heigvd.res.labs.http;

import ch.heigvd.res.labs.http.net.client.HttpClient;

import java.io.IOException;

public class HttpTests {
    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient();
        client.connect("www.heig-vd.ch");
        client.requestPage("www.heig-vd.ch");
        client.disconnect();
    }
}
