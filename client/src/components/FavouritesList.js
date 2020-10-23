import React from 'react';
import '../css/FavouritesList.css';

class FavouritesList extends React.Component {
  render() {
    return (
      <div className="leftHalf">
        <div className="favouritesIcon">
          <i id="favourites" className="fa fa-heart"></i>      
        </div>
        <div className="urlList">{this.renderUrls()}</div>
      </div>
    );    
  }

  renderUrls = () => {
    return this.props.favouriteList.map(url => {
      return (
        <div className="list" key={url.id}>
            {url.fullUrl} | localhost:9090/{url.shortUrl}
        </div>
      );
    })
  }
}

export default FavouritesList;