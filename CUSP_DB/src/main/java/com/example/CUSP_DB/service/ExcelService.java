package com.example.CUSP_DB.service;

import com.example.CUSP_DB.dao.BoreholeDao;
import com.example.CUSP_DB.model.Borehole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.CUSP_DB.excelParse.readExcel;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    private BoreholeDao boreholeDao;

    @Autowired
    public ExcelService(BoreholeDao boreholeDao) {
        this.boreholeDao = boreholeDao;
    }

    public void saveAll(MultipartFile file){
        try{
            List<Borehole> boreholes=readExcel.excelToBorehole(file.getInputStream());
            boreholeDao.saveAll(boreholes);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Borehole> getAllBoreholes(){
        return (List<Borehole>) boreholeDao.findAll();
    }
}
