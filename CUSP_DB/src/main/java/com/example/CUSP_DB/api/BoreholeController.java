package com.example.CUSP_DB.api;

import com.example.CUSP_DB.exceptions.BoreholeEmptyNameException;
import com.example.CUSP_DB.model.Borehole;
import com.example.CUSP_DB.service.BoreholeService;
import com.example.CUSP_DB.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.List;

@RestController
@RequestMapping("boreholes")
public class BoreholeController {

    private BoreholeService boreholeService;
    private ExcelService excelService;

    @Autowired
    public BoreholeController(BoreholeService boreholeService, ExcelService excelService) {
        this.boreholeService = boreholeService;
        this.excelService=excelService;
    }

    @GetMapping
    public List<Borehole> getAllBorehoels(){
        return boreholeService.getAllBoreholes();
    }

    @RequestMapping("/addBorehole")
    @PostMapping()
    public ResponseEntity<String> addBorehole(@RequestBody Borehole borehole){
        try{
            Borehole savedBorehole=boreholeService.addBorehole(borehole);
            return ResponseEntity.ok("Added Borehole: "+borehole.toString());
        } catch (BoreholeEmptyNameException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping("/upload")
    @PostMapping()
    public ResponseEntity<String> uploadBorehole(@RequestParam("file") MultipartFile file){
        try{
            excelService.saveAll(file);
            return ResponseEntity.status(HttpStatus.OK).body("upload file successfully: "+file.getOriginalFilename());
        }  catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("upload file failed: "+file.getOriginalFilename()+", "+e.getMessage());
        }
    }

    @RequestMapping("/insert")
    @PostMapping()
    public ResponseEntity<String> insertBorehole(@RequestParam long first, @RequestParam long end){
        try{
            System.out.println("before insert in controller");
            boreholeService.insert(first,end);
            System.out.println("after insert in controller");
            return ResponseEntity.status(HttpStatus.OK).body("Add boreholes successfully");
        }  catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add boreholes failed: "+e.getMessage());
        }
    }

}
