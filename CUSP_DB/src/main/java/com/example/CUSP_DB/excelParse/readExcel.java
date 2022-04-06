package com.example.CUSP_DB.excelParse;

import com.example.CUSP_DB.model.Borehole;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class readExcel {

    //parse upload excel file
    //return value is a list, containing n list of Borehole items.
    //n is equal to the number of sheets in the excel file
    public static List<Borehole> excelToBorehole(InputStream is){
        try{
            //get content from upload excel file
            Workbook workbook=new XSSFWorkbook(is);
            //get number of sheets for the excel file
            int sheetNo=workbook.getNumberOfSheets();

            //store list of Borehole in result
            List<List<Borehole>> result=new ArrayList<List<Borehole>>();

            //parse each sheet in the excel file
            //build a new Borehole for each row
            //store all Boreholes in each sheet in a new list
            //store all Borehole lists in result
            //for(int i=0;i<sheetNo;i++){
                //row is used to store all Boreholes in each sheet
                List<Borehole> row= new ArrayList<Borehole>();

                //get sheet i
                Sheet sheet=workbook.getSheetAt(0);
                //get row in sheet i
                Iterator<Row> rows=sheet.iterator();
                //record row number
                int rowNo=0;
                //hashmap to store column name and its column number
                //name is lower case without '-'
                //{"api":0,...}
                HashMap<String,Integer> nameNo=new HashMap<String,Integer>();
                //record length of col
                int colLength=-1;

                //read each row in sheet i
                while (rows.hasNext()){
                    rowNo++;
                    //get current row i sheet i
                    Row currentRow=rows.next();
                    //read each column name and index
                    Iterator<Cell> cell=currentRow.iterator();
                    //cell index
                    Integer cellIndex=0;

                    //parse first row to get header
                    if (rowNo==1){
                        while (cell.hasNext()){
                            //record column name and index in nameNo
                            Cell currentCell=cell.next();
                            String currentName=currentCell.getStringCellValue();
                            currentName=currentName.replace("_","");
                            currentName=currentName.toLowerCase();
                            nameNo.put(currentName,cellIndex);
                            cellIndex++;
                        }
                        colLength=cellIndex;
                    }
                    else {
                        //get borehole from a row, and add this borehole to row
                        Borehole currentBoreHole=setBorehole(nameNo,currentRow,colLength);
                        row.add(currentBoreHole);
                    }
                //}

                //add this row to result
                //result.add(row);
            }

            return row;
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Fail to parse Excel file: "+e.getMessage());
        }
    }

    //use name-index hashmap, a specific row, and column lengths, to set up a new Borehole items
    private static Borehole setBorehole(HashMap<String,Integer> nameNo,Row row, int colLength) throws ParseException {
        //create a new Borehole item
        Borehole result=new Borehole();
        //create an array to store all information in this row
        String[] thisRow=new String[colLength+1];
        //record each cell's information and its index to thisRow
        for (int i=0;i<colLength;i++){
            Cell currentCell= row.getCell(i);
            DataFormatter formatter = new DataFormatter();
            thisRow[i]=formatter.formatCellValue(currentCell);
        }

        //TODO add empty string or NA string avoiding
        //set value to result
        //   this.API = API;
        result.setAPI(Long.valueOf(thisRow[nameNo.get("api")]));
        //    WellName = wellName;
        result.setWellName((thisRow[nameNo.get("wellname")]));
        //    Operator = operator;
        if(nameNo.get("operator")!=null) {
            result.setOperator((thisRow[nameNo.get("operator")]));
        }
        //    OperatorNo = operatorNo;
        if(nameNo.get("operatorno")!=null) {
            result.setOperatorNo(Long.valueOf(thisRow[nameNo.get("operatorno")]));
        }
        //    FieldName = fieldName;
        result.setFieldName((thisRow[nameNo.get("fieldname")]));
        //    GroundElev = groundElev;
        if(nameNo.get("groundelev")!=null) {
            result.setGroundElev(Long.valueOf(thisRow[nameNo.get("groundelev")]));
        }
        //    KellyElev = kellyElev;
        if(nameNo.get("kellyelev")!=null && !thisRow[nameNo.get("kellyelev")].equals("")) {
            result.setKellyElev(Long.valueOf(thisRow[nameNo.get("kellyelev")]));
        }
        //    DrkFloorElev = drkFloorElev;
        if(nameNo.get("drkfloorelev")!=null && !thisRow[nameNo.get("drkfloorelev")].equals("")) {
            result.setDrkFloorElev(Long.valueOf(thisRow[nameNo.get("drkfloorelev")]));
        }
         //   Latitude = latitude;
        if(nameNo.get("latitude")!=null) {
            result.setLatitude(Float.valueOf(thisRow[nameNo.get("latitude")]));
        }
         //   Longitude = longitude;
        if(nameNo.get("longitude")!=null) {
            result.setLongitude(Float.valueOf(thisRow[nameNo.get("longitude")]));
        }
         //   CoordsSurfN = coordsSurfN;
        if(nameNo.get("coordssurfn")!=null) {
            result.setCoordsSurfN(Long.valueOf(thisRow[nameNo.get("coordssurfn")]));
        }
        //    CoordsSurfE = coordsSurfE;
        if(nameNo.get("coordssurfe")!=null) {
            result.setCoordsSurfE(Long.valueOf(thisRow[nameNo.get("coordssurfe")]));
        }
         //   this.UTM = UTM;
        if(nameNo.get("utmzone")!=null) {
            result.setUTM(Integer.parseInt(thisRow[nameNo.get("utmzone")]));
        }
        //    FootageNS = footageNS;
        if(nameNo.get("footagens")!=null) {
            result.setFootageNS(Long.valueOf(thisRow[nameNo.get("footagens")]));
        }
         //   DirNS = dirNS;
        result.setDirNS((thisRow[nameNo.get("dirns")]));
         //   FootageEW = footageEW;
        if(nameNo.get("footageew")!=null) {
            result.setFootageEW(Long.valueOf(thisRow[nameNo.get("footageew")]));
        }
         //   DirEW = dirEW;
        result.setDirEW((thisRow[nameNo.get("direw")]));
         //   QtrQtr = qtrQtr;
        result.setQtrQtr((thisRow[nameNo.get("qtrqtr")]));
         //   Section = section;
        if(nameNo.get("section")!=null) {
            result.setSection(Integer.parseInt(thisRow[nameNo.get("section")]));
        }
        //    Township = township;
        if(nameNo.get("township")!=null) {
            result.setTownship(Integer.parseInt(thisRow[nameNo.get("township")]));
        }
         //   TownshipDir = townshipDir;
        result.setTownshipDir((thisRow[nameNo.get("townshipdir")]));
         //   Range = range;
        if(nameNo.get("range")!=null) {
            result.setRange(Integer.parseInt(thisRow[nameNo.get("range")]));
        }
         //   RangeDir = rangeDir;
        result.setRangeDir((thisRow[nameNo.get("rangedir")]));
         //   Meridian = meridian;
        result.setMeridian((thisRow[nameNo.get("meridian")]));
        //    County = county;
        result.setCounty((thisRow[nameNo.get("county")]));
         //   DirHoriz = dirHoriz;
        result.setDirHoriz((thisRow[nameNo.get("dirhoriz")]));
         //   DirVert = dirVert;
        result.setDirVert((thisRow[nameNo.get("dirvert")]));
         //   DirDirect = dirDirect;
        result.setDirDirect((thisRow[nameNo.get("dirdirect")]));
         //   SurfaceOwner = surfaceOwner;
        result.setSurfaceOwner((thisRow[nameNo.get("surfaceowner")]));
         //   IndianTribe = indianTribe;
        result.setIndianTribe((thisRow[nameNo.get("indiantribe")]));
         //   Confidential = confidential;
        result.setConfidential((thisRow[nameNo.get("confidential")]));
         //   ConfRelDate = confRelDate;
        if(nameNo.get("confreldate")!=null && !thisRow[nameNo.get("confreldate")].equals("")) {
            System.out.println(thisRow[nameNo.get("confreldate")]);
            System.out.println(new SimpleDateFormat("MM/dd/yy HH:mm").parse(thisRow[nameNo.get("confreldate")]));
            result.setConfRelDate(new SimpleDateFormat("MM/dd/yy HH:mm").parse(thisRow[nameNo.get("confreldate")]));
        }
        //    LeaseNumber = leaseNumber;
        result.setLeaseNumber((thisRow[nameNo.get("leasenumber")]));
         //   LeaseType = leaseType;
        result.setLeaseType((thisRow[nameNo.get("leasetype")]));
        //    AbandonDate = abandonDate;
        if(nameNo.get("abandondate")!=null && !thisRow[nameNo.get("abandondate")].equals("")) {
            result.setAbandonDate(new SimpleDateFormat("dd/MM/yyyy").parse(thisRow[nameNo.get("abandondate")]));
        }
        //    WellStatus = wellStatus;
        result.setWellStatus(thisRow[nameNo.get("wellstatus")]);
        //    WellType = wellType;
        result.setWellType(thisRow[nameNo.get("welltype")]);
        //    TotCumOil = totCumOil;
        if(nameNo.get("totcumoil")!=null && !thisRow[nameNo.get("totcumoil")].equals("")) {
            result.setTotCumOil(Long.valueOf(thisRow[nameNo.get("totcumoil")]));
        }
        //    TotCumGas = totCumGas;
        if(nameNo.get("totcumgas")!=null && !thisRow[nameNo.get("totcumgas")].equals("")) {
            result.setTotCumGas(Long.valueOf(thisRow[nameNo.get("totcumgas")]));
        }
         //   TotCumWater = totCumWater;
        if(nameNo.get("totcumwater")!=null && !thisRow[nameNo.get("totcumwater")].equals("")) {
            result.setTotCumWater(Long.valueOf(thisRow[nameNo.get("totcumwater")]));
        }
         //   MultiLats = multiLats;
        if(nameNo.get("multilats")!=null) {
            result.setMultiLats(Long.valueOf(thisRow[nameNo.get("multilats")]));
        }
         //   OrigianlField = origianlField;
        result.setOrigianlField((thisRow[nameNo.get("originalfieldtype")]));
         //   UnitName = unitName;
        result.setUnitName((thisRow[nameNo.get("unitname")]));
         //   this.GISStatusType = GISStatusType;
        result.setGISStatusType((thisRow[nameNo.get("gisstatustype")]));
         //   OrigComplDate = origComplDate;
        if(nameNo.get("origcompldate")!=null) {
            result.setOrigComplDate(new SimpleDateFormat("dd/MM/yyyy").parse(thisRow[nameNo.get("origcompldate")]));
        }

         //   Jurisdiction = jurisdiction;
        result.setJurisdiction((thisRow[nameNo.get("jurisdiction")]));
         //   this.TDSNavajo = TDSNavajo;
        if(nameNo.get("tdsmglnavajo")!=null) {
            result.setTDSNavajo(Long.valueOf(thisRow[nameNo.get("tdsmglnavajo")]));
        }
         //   this.TDSWingate = TDSWingate;
        if(nameNo.get("tdsmglwingate")!=null && !thisRow[nameNo.get("tdsmglwingate")].equals("NA")) {
            result.setTDSWingate(Long.valueOf(thisRow[nameNo.get("tdsmglwingate")]));
        }
        //TODO followings are not shown in excel file
         /*   ReportsID = reportsID;
        result.setReportsID(Long.valueOf(thisRow[nameNo.get("reportsid")]));
         //   FormationTopDepths = formationTopDepths;
        result.setFormationTopDepths(Long.valueOf(thisRow[nameNo.get("formationtopdepths")]));
         //   Thickness = thickness;
        result.setThickness(Long.valueOf(thisRow[nameNo.get("thickness")]));
          //  NetSand = netSand;
        result.setNetSand((thisRow[nameNo.get("netsand")]));
          //  NetPay = netPay;
        result.setNetPay((thisRow[nameNo.get("netpay")]));
          //  Extent = extent;
        result.setExtent((thisRow[nameNo.get("extent")]));*/

        return result;
        }
}

