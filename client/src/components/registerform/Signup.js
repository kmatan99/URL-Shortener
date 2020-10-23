import React from 'react';
import '../../css/Signup.css';
import axios from 'axios';
import {withRouter} from 'react-router-dom';

class Signup extends React.Component {

    state = {
        username: null,
        password: null,
        email: null
    };

    render() {
        return (
           <div className="main">
               <div className="registerForm">
                   <div className="header">

                   </div>
                   <div className="body">
                       <h2 className="title">Registration Info</h2>
                       <form onSubmit={this.handleSubmit}>
                        <div className="userName">
                            <input className="user" type="text" placeholder="Username" onChange={this.handleUsernameInput}></input>
                        </div> 
                        <div className="password">
                            <input className="pass" type="password" placeholder="Password" onChange={this.handlePasswordInput}></input>
                        </div>
                        <div className="email">
                            <input className="mail" type="text" placeholder="E-mail" onChange={this.handleEmailInput}></input>
                        </div>
                        <button className="submit" onClick={this.handleRegistration}>Sign Up</button>       
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

    handleEmailInput = (e) => {
        this.setState({
            email: e.target.value
        }, () => console.log(this.state.email));
    }

    handleSubmit = (e) => {
        e.preventDefault();
    }

    handleRegistration = () => {
        const userData = {
            username: this.state.username,
            password: this.state.password,
            email: this.state.email
        }

        axios.post('http://localhost:9090/createuser', userData)
        .then = () => {
            console.log("You have registered succesfully!");
        }

        this.props.history.push("/login");
    }
}

export default withRouter(Signup);