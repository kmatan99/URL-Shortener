import React from 'react';
import '../css/History.css';

class HeartIcon extends React.Component {
    state = {
        clicked: false,
    }

    render() {
        return(
            <i id="favourite" className={this.props.url.favourite ? "fa fa-heart" : "fa fa-heart-o"}></i>
        )
    }
}

export default HeartIcon;