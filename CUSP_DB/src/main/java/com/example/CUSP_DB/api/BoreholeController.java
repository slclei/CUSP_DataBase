package com.example.CUSP_DB.api;

import com.example.CUSP_DB.exceptions.BoreholeEmptyNameException;
import com.example.CUSP_DB.model.Borehole;
import com.example.CUSP_DB.service.BoreholeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Borehole")
public class BoreholeController {

    private BoreholeService boreholeService;

    @Autowired
    public BoreholeController(BoreholeService boreholeService) {
        this.boreholeService = boreholeService;
    }

    @GetMapping
    public List<Borehole> getAllBorehoels(){
        return boreholeService.getAllBoreholes();
    }

    @RequestMapping("/addBorehole")
    @PostMapping
    public ResponseEntity<String> addBorehole(@RequestBody Borehole borehole){
        try{
            Borehole savedBorehole=boreholeService.addBorehole(borehole);
            return ResponseEntity.ok("Added Borehole: "+borehole.toString());
        } catch (BoreholeEmptyNameException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
