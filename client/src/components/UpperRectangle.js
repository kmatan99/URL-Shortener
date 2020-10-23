import React from 'react';
import ToolBar from './Toolbar';
import MainBlock from './MainBlock';
import '../css/UpperRectangle.css';

class UpperRectangle extends React.Component {
    render() {
        return (
            <div className="upperRectangle">
                <ToolBar/>
                <hr></hr>
                <MainBlock getUrls={this.props.getUrls}/>
            </div>
        );     
    }       
}

export default UpperRectangle;