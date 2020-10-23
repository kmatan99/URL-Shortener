import React from 'react';
import '../../css/Login.css';
import axios from 'axios';
import {withRouter} from 'react-router-dom';

class Login extends React.Component {

    state = {
        username: null,
        password: null,
    }

    render() {
        return(
            <div className="main">
               <div className="registerForm">
                   <div className="header">

                   </div>
                   <div className="body">
                       <h2 className="title">Login</h2>
                       <form onSubmit={this.handleSubmit}>
                        <div className="userName">
                            <input className="user" type="text" placeholder="Username" onChange={this.handleUsernameInput}></input>
                        </div> 
                        <div className="password">
                            <input className="pass" type="password" placeholder="Password" onChange={this.handlePasswordInput}></input>
                        </div>
                        <button className="submit" onClick={this.handleLogIn}>Log In</button> 
                        <div className="linkDiv">
                            <a id="forgot" href="¸http://localhost:3000/signup">Forgot your password?</a>    
                        </div>
                        <div>
                            <a target="_self" id="register" href="http://localhost:3000/signup">Don't have an account? Sign up here!</a>
                        </div>                              
                       </form>
                   </div>
               </div>
           </div>
        );
    }

    handleUsernameInput = (e) => {
        this.setState({
            username: e.target.value
        }, () => console.log(this.state.username));
    }

    handlePasswordInput = (e) => {
        this.setState({
            password: e.target.value
        }, () => console.log(this.state.password));
    }

    handleSubmit = (e) => {
        e.preventDefault();
    }

    handleLogIn = (e) => {
        const userData = {
            username: this.state.username,
            password: this.state.password
        }

        axios.post('http://localhost:9090/authenticate', userData)
        .then((response) => {
            localStorage.setItem("jwtToken", response.data.jwtToken);
            this.props.history.push("/");
        })
        .catch(err => console.log(err));
    }
}

export default withRouter(Login);