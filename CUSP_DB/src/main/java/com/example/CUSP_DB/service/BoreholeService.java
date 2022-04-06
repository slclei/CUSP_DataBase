package com.example.CUSP_DB.service;

import com.example.CUSP_DB.dao.BoreholeDao;
import com.example.CUSP_DB.exceptions.BoreholeEmptyNameException;
import com.example.CUSP_DB.exceptions.BoreholeNonExistException;
import com.example.CUSP_DB.model.Borehole;
import com.example.CUSP_DB.util.HttpParse;
import com.example.CUSP_DB.util.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
public class BoreholeService {
    private BoreholeDao boreholeDao;

    @Autowired
    public BoreholeService(BoreholeDao boreholeDao) {
        this.boreholeDao = boreholeDao;
    }

    public Borehole addBorehole(Borehole borehole){
        if (borehole.getWellName() ==null){
            throw new BoreholeEmptyNameException("Empty WellName.");
        }

        return boreholeDao.save(borehole);
    }

    public Borehole updateBore(Borehole borehole){
        if (borehole.getAPI()==null){
            throw new BoreholeNonExistException("Cannot find well API.");
        }

        return boreholeDao.save(borehole);
    }

    public List<Borehole> getAllBoreholes(){
        return (List<Borehole>) boreholeDao.findAll();
    }

    public Optional<Borehole> getBoreholeById(Long API){
        return boreholeDao.findById(API);
    }

    public int insert(long first, long end) throws IOException {
        for (long i=first; i<=end;i++){
            String key=String.valueOf(i);

            String html=null;

            try{
                html= HttpRequest.getRawHtml(key);
            } catch (IOException e) {
                System.out.println("IO in service: "+e);
            } catch (URISyntaxException e) {
                System.out.println("URI exception: "+e);
            }

            if (html==null){
                continue;
            }

            System.out.println("after get html in service");

            addBorehole(HttpParse.getData(html));
        }

        return 0;
    }
}
