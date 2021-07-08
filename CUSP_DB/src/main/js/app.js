'use strict';

// tag:: vars[]
const React=require('react');
const ReactDOM=require('react-dom');
const client=require('./client');
const follow=require('./follow');
var root='/api';
//end::vars[]

//tag::app[]
class App extends React.Component {
    constructor(props) {
        super(props);
        //add boreholes in a list
        this.state={boreholes:[],attributes:[],pageSize:2,links:{}};
        this.updatePageSize = this.updatePageSize.bind(this);
        this.onCreate = this.onCreate.bind(this);
        this.onDelete = this.onDelete.bind(this);
        this.onNavigate = this.onNavigate.bind(this);
    }

    //tag::Load data
    loadFromServer(pageSize){
        follow(client,root,[
            {rel:'boreholes',params:{size:pageSize}}
        ]).then(boreholeCollection => {
            return client({
                method:'GET',
                path:boreholeCollection.entity._links.profile.href,
                headers:{'Accept':'application/schema+json'}
            }).then(schema=>{
                this.schema=schema.entity;
                return boreholeCollection;
            });
        }).done(boreholeCollection => {
            this.setState({
                boreholes:boreholeCollection.entity._embedded.boreholes,
                attributes:Object.keys(this.schema.properties),
                pageSize:pageSize,
                links:boreholeCollection.entity._links
            });
        });
    }
    //end::Load data

    // tag::create[]
    onCreate(newBorehole) {
        follow(client, root, ['boreholes']).then(boreholeCollection => {
            return client({
                method: 'POST',
                path: boreholeCollection.entity._links.self.href,
                entity: newBorehole,
                headers: {'Content-Type': 'application/json'}
            })
        }).then(response => {
            return follow(client, root, [
                {rel: 'boreholes', params: {'size': this.state.pageSize}}]);
        }).done(response => {
            if (typeof response.entity._links.last !== "undefined") {
                this.onNavigate(response.entity._links.last.href);
            } else {
                this.onNavigate(response.entity._links.self.href);
            }
        });
    }
    // end::create[]

    // tag::delete[]
    onDelete(borehole) {
        client({method: 'DELETE', path: borehole._links.self.href}).done(response => {
            this.loadFromServer(this.state.pageSize);
        });
    }
    // end::delete[]

    // tag::navigate[]
    onNavigate(navUri) {
        client({method: 'GET', path: navUri}).done(boreholeCollection => {
            this.setState({
                boreholes: boreholeCollection.entity._embedded.boreholes,
                attributes: this.state.attributes,
                pageSize: this.state.pageSize,
                links: boreholeCollection.entity._links
            });
        });
    }
    // end::navigate[]

    // tag::update-page-size[]
    updatePageSize(pageSize) {
        if (pageSize !== this.state.pageSize) {
            this.loadFromServer(pageSize);
        }
    }
    // end::update-page-size[]

    componentDidMount() {
        this.loadFromServer(this.state.pageSize);
    }

    render() {
        return (
            <div>
                <CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
                <BoreholeList boreholes={this.state.boreholes}
                              links={this.state.links}
                              pageSize={this.state.pageSize}
                              onNavigate={this.onNavigate}
                              onDelete={this.onDelete}
                              updatePageSize={this.updatePageSize}/>
            </div>
        )
    }
}
//end::app[]

// tag::create-dialog[]
class CreateDialog extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        const newBorehole = {};
        this.props.attributes.forEach(attribute => {
            newBorehole[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
        });
        this.props.onCreate(newBorehole);

        // clear out the dialog's inputs
        this.props.attributes.forEach(attribute => {
            ReactDOM.findDOMNode(this.refs[attribute]).value = '';
        });

        // Navigate away from the dialog to hide it.
        window.location = "#";
    }

    render() {
        const inputs = this.props.attributes.map(attribute =>
            <p key={attribute}>
                <input type="text" placeholder={attribute} ref={attribute} className="field"/>
            </p>
        );

        return (
            <div>
                <a href="#createBorehole">Create</a>

                <div id="createBorehole" className="modalDialog">
                    <div>
                        <a href="#" title="Close" className="close">X</a>

                        <h2>Create new borehole</h2>

                        <form>
                            {inputs}
                            <button onClick={this.handleSubmit}>Create</button>
                        </form>
                    </div>
                </div>
            </div>
        )
    }

}
// end::create-dialog[]

//tag::borehole-list[]
class BoreholeList extends React.Component{
    constructor(props) {
        super(props);
        this.handleNavFirst = this.handleNavFirst.bind(this);
        this.handleNavPrev = this.handleNavPrev.bind(this);
        this.handleNavNext = this.handleNavNext.bind(this);
        this.handleNavLast = this.handleNavLast.bind(this);
        this.handleInput = this.handleInput.bind(this);
    }

    // tag::handle-page-size-updates[]
    handleInput(e) {
        e.preventDefault();
        const pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
        if (/^[0-9]+$/.test(pageSize)) {
            this.props.updatePageSize(pageSize);
        } else {
            ReactDOM.findDOMNode(this.refs.pageSize).value =
                pageSize.substring(0, pageSize.length - 1);
        }
    }
    // end::handle-page-size-updates[]

    // tag::handle-nav[]
    handleNavFirst(e){
        e.preventDefault();
        this.props.onNavigate(this.props.links.first.href);
    }

    handleNavPrev(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.links.prev.href);
    }

    handleNavNext(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.links.next.href);
    }

    handleNavLast(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.links.last.href);
    }
    // end::handle-nav[]
    render(){
        const boreholes=this.props.boreholes.map(borehole =>
            <Borehole key={borehole._links.self.href} borehole={borehole} onDelete={this.props.onDelete}/>
        );
        const navLinks = [];
        if ("first" in this.props.links) {
            navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
        }
        if ("prev" in this.props.links) {
            navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
        }
        if ("next" in this.props.links) {
            navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
        }
        if ("last" in this.props.links) {
            navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
        }

        return (
            <div>
                <input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
            <table>
                <tbody>
                    <tr>
                        <th>Delete</th>
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
                <div>
                    {navLinks}
                </div>
            </div>
        )
    }
}
//end::borehole-list[] J

//tag::borehole
class Borehole extends React.Component{
    constructor(props) {
        super(props);
        this.handleDelete = this.handleDelete.bind(this);
    }

    handleDelete() {
        this.props.onDelete(this.props.borehole);
    }

    render() {
        return (
            <tr>
                <td>
                    <button onClick={this.handleDelete}>Delete</button>
                </td>
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