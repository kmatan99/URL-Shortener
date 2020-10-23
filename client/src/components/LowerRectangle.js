import React from 'react';
import FavouritesList from './FavouritesList';
import History from './History';
import '../css/LowerRectangle.css';

class LowerRectangle extends React.Component {
    render() {
        return (
            <div className="lowerRectangle">
                <FavouritesList favouriteList={this.props.favouriteList} getFavourites={this.props.getFavourites}/>
                <History urlList={this.props.urlList} getUrls={this.props.getUrls} getFavourites={this.props.getFavourites}/>
            </div>
        );       
    }
}

export default LowerRectangle;