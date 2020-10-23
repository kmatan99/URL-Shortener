import React from 'react';
import '../css/MainBlock.css';
import InputUrl from './InputUrl';

class MainBlock extends React.Component {
    render() {
      return (
        <div className="mainBlock">
          <div className="column1">
              <InputUrl getUrls={this.props.getUrls}/>
          </div>
        </div>
      );
  }
}

export default MainBlock;