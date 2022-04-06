package com.example.CUSP_DB.util;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.CUSP_DB.dao.BoreholeDao;
import com.example.CUSP_DB.exceptions.BoreholeNonExistException;
import com.example.CUSP_DB.model.Borehole;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpParse {

    public static Borehole getData(String html) throws IOException {
                //parse html content with Jsoup
        System.out.println(html.substring(0,20));
        Document doc =Jsoup.parse(html);

        //get content from html tag
        //get all texts from class "ColunmValue"
        //document.querySelector("#ED > table > tbody > tr > td:nth-child(3)")
        Elements elements=doc.select("td[class=ColumnValue]");
        System.out.println("after getting api in parse");
        //System.out.println(elements);

        /*String iframeSrc=iframe.attr("src");
        System.out.println(iframes.get(0).data().substring(0,1000));
        Document iframeDoc=Jsoup.parse(iframes.get(0).data());

        //get element from iframe
        Element element=iframeDoc.select("#form1").first();
        System.out.println("form1 in iframe is "+element.attr("id"));

        Element element1=element.select("div").first();
        System.out.println("form1 is "+element1.attr("class"));
        Element elements=element;*/
        Borehole borehole=new Borehole();
        int count=0;
        //api: 1-0
        String api=elements.get(0).text().replaceAll("-","");

        System.out.println(api);
        borehole.setAPI(Long.valueOf(api));

        if (borehole.getAPI()==null){
            throw new BoreholeNonExistException("Cannot find well API.");
        }
        //WellName: 4-3
        String wellName=elements.get(3).text();
        if (wellName!=null) {
            borehole.setWellName(wellName);
        }
        //System.out.println(wellName);
        //Operator: 5-4
        String operator=elements.get(4).text();
        if(operator!=null) {
            borehole.setOperator(operator);
        }
        //Operator number
        String operatorNumber=elements.get(4).select("a").attr("href");
        if(operatorNumber!=null) {
            borehole.setOperatorNo(Long.valueOf(operatorNumber.substring(32, 38)));
        }
        //WellType: gas well: GW; Water Disposal well: WD:6-5
        String wellType=elements.get(5).text();
        if(wellType!=null) {
            borehole.setWellType(wellType + "W");
        }

        //well status
        String wellStatuses=elements.get(6).text();
        if(wellStatuses!=null) {
            String wellStatus=wellStatuses;
            borehole.setWellStatus(wellStatus);
        }

        //Surface Owner: 9-8
        String surfaceOwner=elements.get(8).text();
        if (surfaceOwner!=null) {
            borehole.setSurfaceOwner(surfaceOwner);
        }
        //System.out.println("surfaceOwner");
        //Confidential: 10-9
        String confidential=elements.get(9).text();
        if(confidential!=null) {
            borehole.setConfidential(confidential);
        }
        //Tribe: 11-10
        String tribe=elements.get(10).text();
        if(tribe!=null) {
            borehole.setIndianTribe(tribe);
        }
        //UnitName: 12-11
        String unitName=elements.get(11).text();
        if(unitName!=null) {
            borehole.setUnitName(unitName);
            borehole.setFieldName(unitName);
        }
        //Cumulative oil production: 15-14
        String cumOil= elements.get(14).text();
        if(cumOil!=null) {
            borehole.setTotCumOil(Long.valueOf(cumOil));
        }
        //Cumulative gas production: 16-15
        String cumGas=elements.get(15).text();
        if(cumGas!=null) {
            borehole.setTotCumGas(Long.valueOf(cumGas));
        }
        //Cumulative water production: 17-16
        String cumWater=elements.get(16).text();
        if(cumWater!=null) {
            borehole.setTotCumWater(Long.valueOf(cumWater));
        }
        //original field type: 21-20
        String originalType=elements.get(20).text();
        if (originalType!=null) {
            borehole.setOrigianlField(originalType);
        }
        //section: 24-23
        String section=elements.get(23).text();
        if(section!=null) {
            borehole.setSection(Integer.valueOf(section));
        }
        //township and direction: 25-24
        //08s: township: 08, direction: s
        String twp=elements.get(24).text();
        if(twp!=null) {
            borehole.setTownship(Integer.valueOf(twp.substring(0, twp.length() - 1)));
            borehole.setTownshipDir(twp);
        }
        //range and direction: 26-25
        String rng=elements.get(25).text();
        if (rng!=null) {
            borehole.setRange(Integer.valueOf(rng.substring(0, rng.length() - 1)));
            borehole.setRangeDir(rng);
        }
        //qtrqtr:27-26
        //Southeast of Northwest=>SENW
        String qq=elements.get(26).text();
        if(qq!=null) {
            String qtr = ("" + qq + qq.charAt(5) + qq.charAt(13) + qq.charAt(18)).toUpperCase(Locale.ROOT);
            borehole.setQtrQtr(qtr);
        }
        //System.out.println(qq);
        //NS feet: 28-27
        String nsFeet=elements.get(27).text();
        if(nsFeet!=null) {
            borehole.setFootageNS(Long.valueOf(nsFeet));
        }
        //Dir N/s:29-28
        String ns=elements.get(28).text();
        if(ns!=null) {
            borehole.setDirNS("F" + ns + "L");
        }
        //EW feet: 30-29
        String ewFeet=elements.get(29).text();
        if(ewFeet!=null) {
            borehole.setFootageEW(Long.valueOf(ewFeet));
        }
        //Dir e/w:31-30
        String ew=elements.get(30).text();
        if(ew!=null) {
            borehole.setDirEW("F" + ew + "L");
        }
        //Latitude: 32-31
        String latitude=elements.get(31).text();
        if(latitude!=null) {
            borehole.setLatitude(Float.valueOf(latitude));
        }
        //Longitude:33-32
        String longitude=elements.get(32).text();
        if(longitude!=null) {
            borehole.setLongitude(Float.valueOf(longitude));
        }
        //UTM Easting
        String toEast=elements.get(33).text();
        if(toEast!=null) {
            borehole.setCoordsSurfE(Long.valueOf(toEast));
        }
        //UTM Northing
        String toNorth=elements.get(34).text();
        if(toNorth!=null) {
            borehole.setCoordsSurfN(Long.valueOf(toNorth));
        }
        //set UTM: 36-35
        String utm=elements.get(35).text().substring(5);
        if(utm!=null) {
            borehole.setUTM(Integer.valueOf(utm));
        }
        //set Meridian: 38-37
        String meridian=elements.get(37).text();
        if(meridian!=null) {
            borehole.setMeridian(meridian);
        }
        //set county:39-38
        String county=elements.get(38).text();
        if(county!=null) {
            borehole.setCounty(county);
        }

        System.out.println("Borehole built successfully!");

        return borehole;

        /*There are 11 tables in class DetailsBody
        for (Element ele: elements){
            //get number of table id="EDI0" - "EDI10"
            int index=Integer.parseInt(ele.attr("id").substring(3));

            switch (index){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
            }



            //get item attributions with class name
            String itemId = ele.attr("data-sku");
            System.out.println(itemId);
            String itemPrice = ele.select("div[class=p-price]").select("strong").select("i").text();
            //get books
            String itemName = ele.select("div[class~=p-name?]").select("em").text();
            String commentNumber = ele.select("div[class=p-commit]").select("strong").text();
            //plan to choose images' uri; use href in tag a instead.
            String imgurl =ele.select("div[class=p-img]").select("a").attr("href");
            boolean b=imgurl.startsWith("https:");
            if (b==false){
                imgurl="https:"+imgurl;
            }

            String itemUrl="https://item.jd.com/"+itemId+".html";
            String type=keyword;
            String shopName="";
            String shopUrl="";

            //get shop name and url if possible; or default value
            if (ele.select("div[class=p-shop]").select("a[class=curr-shop]").text().length() != 0) {
                shopName = ele.select("div[class=p-shop]").select("a[class=curr-shop]").text();
                shopUrl = "https:" + ele.select("div[class=p-shop]").select("a[class=curr-shop]").attr("href");
            } else {
                shopName = "京东自营";
                shopUrl = "https://mall.jd.com/index-" + ele.select("div[class=p-shop]").attr("data-shopid") + ".html";
            }

            String crawlTime = TimeUtils.GetNowDate("yyyy-MM-dd HH:mm:ss");
            //create a Jd model
            Borehole borehole=new Borehole();
            //set Jdmodel value
            borehole.setItemId(itemId);
            borehole.setItemName(itemName);
            borehole.setItemPrice(itemPrice);
            borehole.setCommentNumber(commentNumber);
            borehole.setItemImgUrl(imgurl);
            borehole.setItemUrl(itemUrl);
            borehole.setShopName(shopName);
            borehole.setShopUrl(shopUrl);
            borehole.setCrawlerTime(crawlTime);
            borehole.setType(type);
            //store each model in data list
            data.add(borehole);
        }*/

    }
}
