import React from 'react';
import '../css/App.css';
import UpperRectangle from './UpperRectangle';
import LowerRectangle from './LowerRectangle';
import axios from 'axios';
import {withRouter} from 'react-router-dom'

class App extends React.Component {

  state = {
    urlList: [],
    favouriteList: []
  }

  render() {
    return (
      <div className="App">
        <UpperRectangle getUrls={this.getUrls}/>
        <LowerRectangle urlList={this.state.urlList} getUrls={this.getUrls} favouriteList={this.state.favouriteList}
            getFavourites={this.getFavourites}/>    
      </div>
    ); 
  }

  componentDidMount = () => {
    const jwtToken = localStorage.getItem('jwtToken');

    if(jwtToken) {
      this.props.history.push("/");
      this.getUrls();
      this.getFavourites();
    }
    else {
      this.props.history.push("/login");
    }
    
  }

  getUrls = () => {
    const jwtToken = localStorage.getItem('jwtToken')
    axios.defaults.headers.common['Authorization'] = 'Bearer ' + jwtToken;
    axios.get('http://localhost:9090/userurlList')
    .then((response) => {
        const urls = response.data.urlList;
        this.setState({
          urlList: urls
        })
    }); 
  }

  getFavourites = () => {
    const jwtToken = localStorage.getItem('jwtToken')
    axios.defaults.headers.common['Authorization'] = 'Bearer ' + jwtToken;
    axios.get('http://localhost:9090/getfavourites')
    .then((response) => {
        const urls = response.data;
        this.setState({
          favouriteList: urls
        })
        console.log(this.state.favouriteList);
    }); 
  }
}

export default withRouter(App);
