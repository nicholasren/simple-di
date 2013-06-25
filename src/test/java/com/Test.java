package com;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String uri = "http://127.0.0.1:3000";

        DefaultHttpClient client = new DefaultHttpClient();

        HttpGet get = new HttpGet(uri);

        StatusLine s = client.execute(get).getStatusLine();
        System.out.println(s);
    }
}
