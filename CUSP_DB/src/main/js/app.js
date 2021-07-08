'use strict';

// tag:: vars[]
const React=require('react');
const ReactDOM=require('react-dom');
const client=require('./client');
//end::vars[]

//tag::app[]
class App extends React.Component {
    constructor(props) {
        super(props);
        //add boreholes in a list
        this.state={boreholes:[]};
    }

    componentDidMount() {
        client({method:"GET",path:'/api/boreholes'}).done(response => {
            this.setState({
                boreholes:response.entity._embedded.boreholes
            });
        });
    }

    render() {
        return (
            <BoreholeList boreholes={this.state.boreholes}/>
        )
    }
}
//end::app[]

//tag::borehole-list[]
class BoreholeList extends React.Component{
    render(){
        const boreholes=this.props.boreholes.map(borehole =>
            <Borehole key={borehole._links.self.href} borehole={borehole}/>
        );
        return (
            <table>
                <tbody>
                    <tr>
                        <th>API</th>
                        <th>WellName</th>
                        <th>Operator</th>
                        <th>OperatorNo</th>
                        <th>FieldName</th>
                        <th>Ground_Elev</th>
                        <th>Kelly_Elev</th>
                        <th>DrkFloor_Elev</th>
                        <th>CoordsSurf_N</th>
                        <th>CoordsSurf_E</th>
                        <th>UTMZone</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>FootageNS</th>
                        <th>Dir_NS</th>
                        <th>FootageEW</th>
                        <th>Dir_EW</th>
                        <th>QtrQtr</th>
                        <th>Section</th>
                        <th>Township</th>
                        <th>TownshipDir</th>
                        <th>Range</th>
                        <th>RangeDir</th>
                        <th>Merdian</th>
                        <th>County</th>
                        <th>Dir_Horiz</th>
                        <th>Dir_Vert</th>
                        <th>Dir_Direct</th>
                        <th>Confidential</th>
                        <th>ConfRelDate</th>
                        <th>LeaseNumber</th>
                        <th>LeaseType</th>
                        <th>SurfaceOwner</th>
                        <th>AbandonDate</th>
                        <th>WellStatus</th>
                        <th>TotCum_Oil</th>
                        <th>TotCum_Gas</th>
                        <th>TotCum_Water</th>
                        <th>IndianTribe</th>
                        <th>Multi_lats</th>
                        <th>OriginalFieldType</th>
                        <th>UniteName</th>
                        <th>GISStatusType</th>
                        <th>OrigComplDate</th>
                        <th>JURISDICTION</th>
                        <th>TDS_mg_L_Navajo</th>
                        <th>TDS_mg_L_Wingate</th>
                    </tr>
                    {boreholes}
                </tbody>
            </table>
        )
    }
}
//end::borehole-list[] J

//tag::borehole
class Borehole extends React.Component{
    render() {
        return (
            <tr>
                <td>{this.props.borehole.api}</td>
                <td>{this.props.borehole.wellName}</td>
                <td>{this.props.borehole.operator}</td>
                <td>{this.props.borehole.operatorNo}</td>
                <td>{this.props.borehole.fieldName}</td>
                <td>{this.props.borehole.groundElev}</td>
                <td>{this.props.borehole.kellyElev}</td>
                <td>{this.props.borehole.drkFloorElev}</td>
                <td>{this.props.borehole.coordsSurfN}</td>
                <td>{this.props.borehole.coordsSurfE}</td>
                <td>{this.props.borehole.utm}</td>
                <td>{this.props.borehole.latitude}</td>
                <td>{this.props.borehole.longitude}</td>
                <td>{this.props.borehole.footageNS}</td>
                <td>{this.props.borehole.dirNS}</td>
                <td>{this.props.borehole.footageEW}</td>
                <td>{this.props.borehole.dirEW}</td>
                <td>{this.props.borehole.qtrQtr}</td>
                <td>{this.props.borehole.section}</td>
                <td>{this.props.borehole.township}</td>
                <td>{this.props.borehole.townshipDir}</td>
                <td>{this.props.borehole.range}</td>
                <td>{this.props.borehole.rangeDir}</td>
                <td>{this.props.borehole.meridian}</td>
                <td>{this.props.borehole.county}</td>
                <td>{this.props.borehole.dirHoriz}</td>
                <td>{this.props.borehole.dirVert}</td>
                <td>{this.props.borehole.dirDirect}</td>
                <td>{this.props.borehole.confidential}</td>
                <td>{this.props.borehole.confRelDate}</td>
                <td>{this.props.borehole.leaseNumber}</td>
                <td>{this.props.borehole.leaseType}</td>
                <td>{this.props.borehole.surfaceOwner}</td>
                <td>{this.props.borehole.abandonDate}</td>
                <td>{this.props.borehole.wellStatus}</td>
                <td>{this.props.borehole.totCumOil}</td>
                <td>{this.props.borehole.totCumGas}</td>
                <td>{this.props.borehole.totCumWater}</td>
                <td>{this.props.borehole.indianTribe}</td>
                <td>{this.props.borehole.multiLats}</td>
                <td>{this.props.borehole.origianlField}</td>
                <td>{this.props.borehole.unitName}</td>
                <td>{this.props.borehole.gisstatusType}</td>
                <td>{this.props.borehole.origComplDate}</td>
                <td>{this.props.borehole.jurisdiction}</td>
                <td>{this.props.borehole.tdsnavajo}</td>
                <td>{this.props.borehole.tdswingate}</td>
            </tr>
        )
    }
}
//end::borehole

//tag::render[]
ReactDOM.render(
    <App />,
    document.getElementById('react')
)
//end::render[]