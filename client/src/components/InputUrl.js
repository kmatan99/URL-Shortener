import React from 'react';
import axios from 'axios';
import '../css/InputUrl.css';

class InputUrl extends React.Component {
    state = {
        fullUrl: 0,
        shortUrl: "exampleUrl",
    };

    render() {
        return(
            <div> 
                <div className="column1">
                    <input type="text" className="inputUrl" placeholder=" Insert your URL here!" onChange={this.handleUrlInput}>
                        </input>
                    <button className="Shorten" type="button" onClick={this.generateShortUrl}>Shorten</button>
                </div>
                <div className="column2">
                    <p className="shortUrlPointer">Your short URL :</p>
                    <p className="shortUrl">{this.state.shortUrl}</p>
                </div>
            </div> 
        );
    }

    handleUrlInput = (e) => {
        this.setState({
            fullUrl: e.target.value
        }, () => console.log(this.state.fullUrl));
    }

    generateShortUrl = () => {
        const jwtToken = localStorage.getItem('jwtToken');
        axios.defaults.headers.common['Authorization'] = 'Bearer ' + jwtToken;
        axios.get('http://localhost:9090/createurl', {
            params: {
                fullUrl: this.state.fullUrl
            }
        })
        .then((response) => {
            console.log(response.data);

            if(response.data !== "Invalid url!"){
                this.setState({
                    shortUrl: "localhost:9090/" + response.data
                });
            }
            else {
                this.setState({
                    shortUrl: response.data
                });
            }
            
            this.props.getUrls();
        });
    }
}

export default InputUrl;