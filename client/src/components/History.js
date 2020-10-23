import React from 'react';
import '../css/History.css';
import axios from 'axios';
import HeartIcon from './HeartIcon';

class History extends React.Component {

  render() {
    return (
      <div className="rightHalf"> 
        <div className="historyIcon">
          <i id="searchHistory" className="fa fa-book"></i>      
        </div>
        <div className="urlList">{this.renderUrls()}</div>
      </div>
      );
    }

  renderUrls = () => {
    return this.props.urlList.map(url => {
      return (
        <div className="list" key={url.id}>
          <div className="heart" onClick={() => {this.addToFavourites(url)}}>
            <HeartIcon url={url}
            /></div>
          {url.fullUrl} | {url.shortUrl} | Visitcount : {url.visitcount}
          <i id="delete" onClick={() => this.handleDeleteClick(url.id)} className="fa fa-close"></i>
        </div>
      );
    })
  }

  handleDeleteClick = (id) => {
    axios.delete('http://localhost:9090/deleteurl/' + id)
      .then(response => {
        this.props.getUrls();
        this.props.getFavourites();
      })
      .catch(err => console.log(err));
    }

  addToFavourites = async (url) => {
    if(!url.favourite) {
      await this.addFavourite(url.id);
    }
    else {
      await this.removeFromFavourite(url.id);
    }

    this.props.getUrls();
    this.props.getFavourites();
  }

  addFavourite = async (id) => {
    return axios.get("http://localhost:9090/favourite/" + id);
  }

  removeFromFavourite = async (id) => {
    return axios.get("http://localhost:9090/removefavourite/" + id);
  }
  
}

export default History;