package ch.heigvd.res.ex.http.net;

import ch.heigvd.res.ex.http.net.client.HttpClient;

import java.io.IOException;

public class HttpTests {
    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient();
        client.connect("www.heig-vd.ch");
        client.requestPage("www.heig-vd.ch");
        client.disconnect();
    }
}
