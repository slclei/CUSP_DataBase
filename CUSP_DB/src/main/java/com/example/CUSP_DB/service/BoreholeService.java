package com.example.CUSP_DB.service;

import com.example.CUSP_DB.dao.BoreholeDao;
import com.example.CUSP_DB.exceptions.BoreholeEmptyNameException;
import com.example.CUSP_DB.exceptions.BoreholeNonExistException;
import com.example.CUSP_DB.model.Borehole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new BoreholeEmptyNameException("Empty API.");
        }

        return boreholeDao.save(borehole);
    }

    public Borehole updateBore(Borehole borehole){
        if (borehole.getAPI()==null || boreholeDao.existsById(borehole.getAPI())){
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
}
