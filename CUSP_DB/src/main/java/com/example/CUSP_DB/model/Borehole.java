package com.example.CUSP_DB.model;

import javax.persistence.*;
import java.util.Date;
import java.util.PrimitiveIterator;

//This is entity for Borehole data information
//key is API
@Entity
@Table(name="wellsut")
public class Borehole {
    @Id
    @Column(name="API")
    private Long API;

    @Column(name="Wellname")
    private String WellName;

    @Column(name="Operator")
    private String Operator;

    @Column(name="Operatorno")
    private Long OperatorNo;

    @Column(name="Fieldname")
    private String FieldName;

    @Column(name="Ground_ele")
    private Long GroundElev;

    @Column(name="Kelly_elev")
    private Long KellyElev;

    @Column(name="Drkfloor_e")
    private Long DrkFloorElev;

    @Column(name="Latitude")
    private Float Latitude;

    @Column(name="Longitude")
    private Float Longitude;

    //TODO followings are belonged to map?
    @Column(name="Coordssurf")
    private Long CoordsSurfN;

    @Column(name="Coordssu_1")
    private Long CoordsSurfE;

    @Column(name="UTMZone")
    private Integer UTM;

    @Column(name="Footagens")
    private Long FootageNS;

    @Column(name="Dir_ns")
    private String DirNS;

    @Column(name="Footageew")
    private Long FootageEW;

    @Column(name="Dir_ew")
    private String DirEW;

    @Column(name="Qtrqtr")
    private String QtrQtr;

    @Column(name="Section")
    private Integer Section;

    @Column(name="Township")
    private Integer Township;

    @Column(name="Townshipdi")
    private String TownshipDir;

    @Column(name="Range")
    private Integer Range;

    @Column(name="Rangedir")
    private String RangeDir;

    @Column(name="Meridian")
    private String Meridian;

    @Column(name="County")
    private String County;

    @Column(name="Dir_horiz")
    private String DirHoriz;

    @Column(name="Dir_vert")
    private String DirVert;

    @Column(name="Dir_direct")
    private String DirDirect;

    @Column(name="Surfaceown")
    private String SurfaceOwner;

    @Column(name="Indiantrib")
    private String IndianTribe;
    //TODO Above are location information

    @Column(name="Confidenti")
    private String Confidential;

    @Column(name="Confreldat")
    private Date ConfRelDate;

    @Column(name="Leasenumbe")
    private String LeaseNumber;

    @Column(name="Leasetype")
    private String LeaseType;

    @Column(name="Abandondat")
    private Date AbandonDate;

    @Column(name="Wellstatus")
    private String WellStatus;

    @Column(name="Welltype")
    private String WellType;

    //TODO below are Production and injection volumes?
    @Column(name="Totcum_oil")
    private Long TotCumOil;

    @Column(name="Totcum_gas")
    private Long TotCumGas;

    @Column(name="Totcum_wat")
    private Long TotCumWater;
    //TODO above are Production and injection volumes?

    @Column(name="Multi_lats")
    private Long MultiLats;

    @Column(name="Originalfi")
    private String OrigianlField;

    @Column(name="Unitname")
    private String UnitName;

    @Column(name="GISStatust")
    private String GISStatusType;

    @Column(name="Origcompld")
    private Date OrigComplDate;

    @Column(name="Jurisdicti")
    private String Jurisdiction;

    //TODO TDS belongs to Groundwater? One well has one groundwater data?
    @Column(name="TDSNavajo")
    private Long TDSNavajo;

    @Column(name="TDSWingate")
    private Long TDSWingate;

    //TODO below are listed in CUSP data workflow and types, but not in ConocoPhilips
    //Foreign Key linked to well reports
    @Column(name="Reportsid")
    private Long ReportsID;

    @Column(name="Formationtopdepths")
    private Long FormationTopDepths;

    @Column(name="Thickness")
    private Long Thickness;

    @Column(name="Netsand")
    private String NetSand;

    @Column(name="Netpay")
    private String NetPay;

    @Column(name="Extent")
    private String Extent;

    @Override
    public String toString(){
        String str="";
        str += "WellName: "+getWellName()+" API:" + getAPI();
        return str;
    }

    public Borehole() {
    }

    public Borehole(Long API,String wellName) {
        this.WellName=wellName;
        this.API = API;
    }

    public Borehole(String wellName) {
        WellName = wellName;
    }

    public Borehole(Long API, String wellname, String operator, Long operatorNo, String fieldName, Long groundElev, Long kellyElev, Long drkFloorElev, Float latitude, Float longitude, Long coordsSurfN, Long coordsSurfE, Integer UTM, Long footageNS, String dirNS, Long footageEW, String dirEW, String qtrQtr, Integer section, Integer township, String townshipDir, Integer range, String rangeDir, String meridian, String county, String dirHoriz, String dirVert, String dirDirect, String surfaceOwner, String indianTribe, String confidential, Date confRelDate, String leaseNumber, String leaseType, Date abandonDate, String wellStatus, String wellType, Long totCumOil, Long totCumGas, Long totCumWater, Long multiLats, String origianlField, String unitName, String GISStatusType, Date origComplDate, String jurisdiction, Long TDSNavajo, Long TDSWingate, Long reportsID, Long formationTopDepths, Long thickness, String netSand, String netPay, String extent) {
        this.API = API;
        WellName = wellname;
        Operator = operator;
        OperatorNo = operatorNo;
        FieldName = fieldName;
        GroundElev = groundElev;
        KellyElev = kellyElev;
        DrkFloorElev = drkFloorElev;
        Latitude = latitude;
        Longitude = longitude;
        CoordsSurfN = coordsSurfN;
        CoordsSurfE = coordsSurfE;
        this.UTM = UTM;
        FootageNS = footageNS;
        DirNS = dirNS;
        FootageEW = footageEW;
        DirEW = dirEW;
        QtrQtr = qtrQtr;
        Section = section;
        Township = township;
        TownshipDir = townshipDir;
        Range = range;
        RangeDir = rangeDir;
        Meridian = meridian;
        County = county;
        DirHoriz = dirHoriz;
        DirVert = dirVert;
        DirDirect = dirDirect;
        SurfaceOwner = surfaceOwner;
        IndianTribe = indianTribe;
        Confidential = confidential;
        ConfRelDate = confRelDate;
        LeaseNumber = leaseNumber;
        LeaseType = leaseType;
        AbandonDate = abandonDate;
        WellStatus = wellStatus;
        WellType = wellType;
        TotCumOil = totCumOil;
        TotCumGas = totCumGas;
        TotCumWater = totCumWater;
        MultiLats = multiLats;
        OrigianlField = origianlField;
        UnitName = unitName;
        this.GISStatusType = GISStatusType;
        OrigComplDate = origComplDate;
        Jurisdiction = jurisdiction;
        this.TDSNavajo = TDSNavajo;
        this.TDSWingate = TDSWingate;
        ReportsID = reportsID;
        FormationTopDepths = formationTopDepths;
        Thickness = thickness;
        NetSand = netSand;
        NetPay = netPay;
        Extent = extent;
    }

    public Long getAPI() {
        return this.API;
    }

    public String getWellName() {
        return WellName;
    }

    public void setAPI(Long API) {
        this.API = API;
    }

    public void setWellName(String wellName) {
        WellName = wellName;
    }

    public String getOperator() {
        return Operator;
    }

    public Long getOperatorNo() {
        return OperatorNo;
    }

    public String getFieldName() {
        return FieldName;
    }

    public Long getGroundElev() {
        return GroundElev;
    }

    public Long getKellyElev() {
        return KellyElev;
    }

    public Long getDrkFloorElev() {
        return DrkFloorElev;
    }

    public Float getLatitude() {
        return Latitude;
    }

    public Float getLongitude() {
        return Longitude;
    }

    public Long getCoordsSurfN() {
        return CoordsSurfN;
    }

    public Long getCoordsSurfE() {
        return CoordsSurfE;
    }

    public Integer getUTM() {
        return UTM;
    }

    public Long getFootageNS() {
        return FootageNS;
    }

    public String getDirNS() {
        return DirNS;
    }

    public Long getFootageEW() {
        return FootageEW;
    }

    public String getDirEW() {
        return DirEW;
    }

    public String getQtrQtr() {
        return QtrQtr;
    }

    public Integer getSection() {
        return Section;
    }

    public Integer getTownship() {
        return Township;
    }

    public String getTownshipDir() {
        return TownshipDir;
    }

    public Integer getRange() {
        return Range;
    }

    public String getRangeDir() {
        return RangeDir;
    }

    public String getMeridian() {
        return Meridian;
    }

    public String getCounty() {
        return County;
    }

    public String getDirHoriz() {
        return DirHoriz;
    }

    public String getDirVert() {
        return DirVert;
    }

    public String getDirDirect() {
        return DirDirect;
    }

    public String getSurfaceOwner() {
        return SurfaceOwner;
    }

    public String getIndianTribe() {
        return IndianTribe;
    }

    public String getConfidential() {
        return Confidential;
    }

    public Date getConfRelDate() {
        return ConfRelDate;
    }

    public String getLeaseNumber() {
        return LeaseNumber;
    }

    public String getLeaseType() {
        return LeaseType;
    }

    public Date getAbandonDate() {
        return AbandonDate;
    }

    public String getWellStatus() {
        return WellStatus;
    }

    public String getWellType() {
        return WellType;
    }

    public Long getTotCumOil() {
        return TotCumOil;
    }

    public Long getTotCumGas() {
        return TotCumGas;
    }

    public Long getTotCumWater() {
        return TotCumWater;
    }

    public Long getMultiLats() {
        return MultiLats;
    }

    public String getOrigianlField() {
        return OrigianlField;
    }

    public String getUnitName() {
        return UnitName;
    }

    public String getGISStatusType() {
        return GISStatusType;
    }

    public Date getOrigComplDate() {
        return OrigComplDate;
    }

    public String getJurisdiction() {
        return Jurisdiction;
    }

    public Long getTDSNavajo() {
        return TDSNavajo;
    }

    public Long getTDSWingate() {
        return TDSWingate;
    }

    public Long getReportsID() {
        return ReportsID;
    }

    public Long getFormationTopDepths() {
        return FormationTopDepths;
    }

    public Long getThickness() {
        return Thickness;
    }

    public String getNetSand() {
        return NetSand;
    }

    public String getNetPay() {
        return NetPay;
    }

    public String getExtent() {
        return Extent;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public void setOperatorNo(Long operatorNo) {
        OperatorNo = operatorNo;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public void setGroundElev(Long groundElev) {
        GroundElev = groundElev;
    }

    public void setKellyElev(Long kellyElev) {
        KellyElev = kellyElev;
    }

    public void setDrkFloorElev(Long drkFloorElev) {
        DrkFloorElev = drkFloorElev;
    }

    public void setLatitude(Float latitude) {
        Latitude = latitude;
    }

    public void setLongitude(Float longitude) {
        Longitude = longitude;
    }

    public void setCoordsSurfN(Long coordsSurfN) {
        CoordsSurfN = coordsSurfN;
    }

    public void setCoordsSurfE(Long coordsSurfE) {
        CoordsSurfE = coordsSurfE;
    }

    public void setUTM(Integer UTM) {
        this.UTM = UTM;
    }

    public void setFootageNS(Long footageNS) {
        FootageNS = footageNS;
    }

    public void setDirNS(String dirNS) {
        DirNS = dirNS;
    }

    public void setFootageEW(Long footageEW) {
        FootageEW = footageEW;
    }

    public void setDirEW(String dirEW) {
        DirEW = dirEW;
    }

    public void setQtrQtr(String qtrQtr) {
        QtrQtr = qtrQtr;
    }

    public void setSection(Integer section) {
        Section = section;
    }

    public void setTownship(Integer township) {
        Township = township;
    }

    public void setTownshipDir(String townshipDir) {
        TownshipDir = townshipDir;
    }

    public void setRange(Integer range) {
        Range = range;
    }

    public void setRangeDir(String rangeDir) {
        RangeDir = rangeDir;
    }

    public void setMeridian(String meridian) {
        Meridian = Character.toString(meridian.charAt(0));
    }

    public void setCounty(String county) {
        County = county;
    }

    public void setDirHoriz(String dirHoriz) {
        DirHoriz = dirHoriz;
    }

    public void setDirVert(String dirVert) {
        DirVert = dirVert;
    }

    public void setDirDirect(String dirDirect) {
        DirDirect = dirDirect;
    }

    public void setSurfaceOwner(String surfaceOwner) {
        SurfaceOwner = surfaceOwner;
    }

    public void setIndianTribe(String indianTribe) {
        IndianTribe = indianTribe;
    }

    public void setConfidential(String confidential) {
        Confidential = confidential;
    }

    public void setConfRelDate(Date confRelDate) {
        ConfRelDate = confRelDate;
    }

    public void setLeaseNumber(String leaseNumber) {
        LeaseNumber = leaseNumber;
    }

    public void setLeaseType(String leaseType) {
        LeaseType = leaseType;
    }

    public void setAbandonDate(Date abandonDate) {
        AbandonDate = abandonDate;
    }

    public void setWellStatus(String wellStatus) {
        WellStatus = wellStatus;
    }

    public void setWellType(String wellType) {
        WellType = wellType;
    }

    public void setTotCumOil(Long totCumOil) {
        TotCumOil = totCumOil;
    }

    public void setTotCumGas(Long totCumGas) {
        TotCumGas = totCumGas;
    }

    public void setTotCumWater(Long totCumWater) {
        TotCumWater = totCumWater;
    }

    public void setMultiLats(Long multiLats) {
        MultiLats = multiLats;
    }

    public void setOrigianlField(String origianlField) {
        OrigianlField = origianlField;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public void setGISStatusType(String GISStatusType) {
        this.GISStatusType = GISStatusType;
    }

    public void setOrigComplDate(Date origComplDate) {
        OrigComplDate = origComplDate;
    }

    public void setJurisdiction(String jurisdiction) {
        Jurisdiction = jurisdiction;
    }

    public void setTDSNavajo(Long TDSNavajo) {
        this.TDSNavajo = TDSNavajo;
    }

    public void setTDSWingate(Long TDSWingate) {
        this.TDSWingate = TDSWingate;
    }

    public void setReportsID(Long reportsID) {
        ReportsID = reportsID;
    }

    public void setFormationTopDepths(Long formationTopDepths) {
        FormationTopDepths = formationTopDepths;
    }

    public void setThickness(Long thickness) {
        Thickness = thickness;
    }

    public void setNetSand(String netSand) {
        NetSand = netSand;
    }

    public void setNetPay(String netPay) {
        NetPay = netPay;
    }

    public void setExtent(String extent) {
        Extent = extent;
    }
}
