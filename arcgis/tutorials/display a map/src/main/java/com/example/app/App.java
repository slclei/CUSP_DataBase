package com.example.app;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    //to be referenced from other parts
    private MapView mapView;

    //create an instance of this App class, and call start() method
    public static void main(String[] args){
        Application.launch(args);
    }

    //Configure the JavaFX Stage.
    @Override
    public void start(Stage stage) throws Exception {
        //set the title and size of the stage and show it
        stage.setTitle("Add a feature layer from a portal item");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.show();

        //create a JavaFX scene with a stack pane as the root node
        StackPane stackPane= new StackPane();
        Scene scene=new Scene(stackPane);
        stage.setScene(scene);

        //set API key
        String yourApiKey="AAPK6c1cb6ca72734247b2ebc1d71bb459dbeIx7Gxch0QqAwb4izp45FsrDf-fenkdSUj8khYxdINEoSxpqfVvua71BwlLcOrVP";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        //create a map view to display the map and add it to the stack pane
        //https://www.arcgis.com/home/item.html?id=2e4b3df6ba4b44969a3bc9827de746b3
        String portalItemId="537744ac23854acc879ccba58620490d";
        Portal portal=new Portal("https://slkqa5z4zxwzyvk2.maps.arcgis.com/");
        PortalItem portalItem= new PortalItem(portal,portalItemId);

        //a portal item may have several layers.
        long layerId=0;
        FeatureLayer layer=new FeatureLayer(portalItem,layerId);

        //create a map view to display the map and add it to stack pane
        mapView=new MapView();
        stackPane.getChildren().add(mapView);

        //create a new ArcGisMap with a topographic basemap style
        ArcGISMap map=new ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC);
        //add feature layer to the map
        map.getOperationalLayers().add(layer);

        //set the map on the map view for display
        mapView.setMap(map);

        //center the map view at a specific point and scale on the Earth
        //zoom levels and scale: https://developers.arcgis.com/documentation/mapping-apis-and-services/reference/zoom-levels-and-scale/
        mapView.setViewpoint(new Viewpoint(34.02700, -118.80543, 144447.638572));

        //add a done loading listener to the portal item.
        //display an alert if the portal item is not loaded
        portalItem.addDoneLoadingListener(() ->{
            if (portalItem.getLoadStatus()!=LoadStatus.LOADED){
                new Alert(Alert.AlertType.ERROR, "Portal item not loaded. "+ portalItem.getLoadError().getCause().getMessage()).show();
            }
        });

        /*
        //used for add feature layer
        //add new feature layers with urls into service feature table
        String parksUrl = "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Parks_and_Open_Space_Styled/FeatureServer/0";
        String trailsUrl = "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trails_Styled/FeatureServer/0";
        String trailHeadsUrl = "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trailheads_Styled/FeatureServer/0";

        ServiceFeatureTable parksServiceFeatureTable = new ServiceFeatureTable(parksUrl);
        ServiceFeatureTable trailsServiceFeatureTable = new ServiceFeatureTable(trailsUrl);
        ServiceFeatureTable trailHeadsServiceFeatureTable = new ServiceFeatureTable(trailHeadsUrl);

        map.getOperationalLayers().add(new FeatureLayer(parksServiceFeatureTable));
        map.getOperationalLayers().add(new FeatureLayer(trailsServiceFeatureTable));
        map.getOperationalLayers().add(new FeatureLayer(trailHeadsServiceFeatureTable));
*/
    }

    //stops and releases all resourcse
    @Override
    public void stop(){
        if (mapView!=null){
            mapView.dispose();
        }

    }
}
