package com.example.CUSP_DB.util;

import com.google.common.collect.Lists;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private HttpClient httpClient;

    //get Http entity
    public HttpEntity getEntityByGet(String url){
        //init http client notice
        httpClient=HttpClients.custom().build();

        //use get method
        HttpGet httpGet=new HttpGet(url);

        //header setting
        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding","gzip,deflate");
        httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        httpGet.setHeader("Cache-Control","max-age=0");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.3");

        //get response
        HttpResponse response=null;

        try{
            response=httpClient.execute(httpGet);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get website entities
        HttpEntity httpEntity=response.getEntity();
        return httpEntity;
    }

    //get HTML content
    public String getHTMLByGet(String url, String code){
        try{
            return EntityUtils.toString(getEntityByGet(url),code);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //request HTML file
    public static String getRawHtml(String keyword) throws URISyntaxException, ClientProtocolException, IOException {
        //set basic url
        String url="https://dataexplorer.ogm.utah.gov/ED.aspx";

        //set name and value pari in this list
        List<NameValuePair> nameAndValueList = new ArrayList<NameValuePair>();
        nameAndValueList.add(new BasicNameValuePair("keyName", "PKey"));
        nameAndValueList.add(new BasicNameValuePair("keyValue", keyword));
        nameAndValueList.add(new BasicNameValuePair("keyType", "Integer"));
        nameAndValueList.add(new BasicNameValuePair("detailXML", "WellDetails.xml"));
        nameAndValueList.add(new BasicNameValuePair("DETAILSONLY", "True"));
        /*nameAndValueList.add(new BasicNameValuePair("vt", "2"));
        nameAndValueList.add(new BasicNameValuePair("wq", keyword));
        nameAndValueList.add(new BasicNameValuePair("ev", "exprice_" + firstprice + "-" + endprice + "^"));
        nameAndValueList.add(new BasicNameValuePair("uc", "0"));
        nameAndValueList.add(new BasicNameValuePair("page", page + ""));*/

        //set final uri
        URI uri=new URIBuilder(url).addParameters(nameAndValueList).build();
        System.out.println(uri);

        HttpClientContext httpClientContext=HttpClientContext.create();

        //add header list
        List<Header> headerList= Lists.newArrayList();
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9," +
                "image/webp,image/apng,*/*;q=0.8"));
        headerList.add(new BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"));
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate"));
        headerList.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "max-age=0"));
        headerList.add(new BasicHeader(HttpHeaders.CONNECTION, "keep-alive"));
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,ja;q=0.2," +
                "de;q=0.2"));
        //"https://dataexplorer.ogm.utah.gov/"
        headerList.add(new BasicHeader(HttpHeaders.HOST, "dataexplorer.ogm.utah.gov"));
        headerList.add(new BasicHeader(HttpHeaders.REFERER, "https://dataexplorer.ogm.utah.gov/"));

        //init http client
        HttpClient httpClient=HttpClients.custom().setDefaultHeaders(headerList).build();

        //get response content
        HttpUriRequest httpUriRequest=RequestBuilder.get().setUri(uri).build();

        //execute client
        httpClient.execute(httpUriRequest,httpClientContext);

        HttpResponse httpResponse=httpClient.execute(httpUriRequest,httpClientContext);

        StatusLine statusLine = httpResponse.getStatusLine();
        if(statusLine.getStatusCode() != HttpStatus.SC_OK){
            httpResponse.getEntity().getContent().close();
            System.out.println("no such well");
            return null;
        }
        //get entity from result
        HttpEntity httpEntity=httpResponse.getEntity();
        //String html= "<html>"+EntityUtils.toString(httpEntity)+"</html>";
        String html= "<html>"+EntityUtils.toString(httpEntity).substring(3)+"</html>";

        return html;
    }

}
