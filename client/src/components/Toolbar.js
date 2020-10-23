import React from 'react';
import '../css/Toolbar.css';
import UserDropdown from './UserDropdown';
import { withRouter } from "react-router-dom";

class Toolbar extends React.Component {

    state = {
        redirect: false,
        isLoggedIn: true
    }
    
    render() {
        return (
            <div className="toolbar">
                <p className="appName">URL Shortener</p>

                {
                    this.state.isLoggedIn 
                    ? (
                        <UserDropdown/>
                    ) 
                    : (
                        <div className="userActions">
                        <button className="signup" type="button" onClick={this.handleSignup}>Sign up</button>
                        <button className="login" type="button" onClick={this.handleLogin}>Login</button>
                        </div>
                    )
                }
                
            </div>
        );
    }

    componentDidMount = () => {
        const jwtToken = localStorage.getItem('jwtToken');

        if(jwtToken){
            this.setState({
                isLoggedIn: true
            }, () => console.log(this.state.isLoggedIn));
        }        
    }

    handleSignup = () => {
        this.props.history.push("/signup");    
    }

    handleLogin = () => {
        this.props.history.push("/login");    
    }
}

export default withRouter(Toolbar);