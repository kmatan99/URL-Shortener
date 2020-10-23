import React from 'react';
import '../css/UserDropdown.css';
import {withRouter} from 'react-router-dom';

class UserDropdown extends React.Component {
    state = {
        showMenu: false,
    }
    
    render() {
        return (
            <div>
                <button className="userButton" onClick={this.showMenu}>Your account</button> 

                {
                    this.state.showMenu
                       ? (
                            <div className="dropMenu">
                                <button className="dropButton" onClick={this.handleSignOut}>Sign out</button>
                                <button className="dropButton">Manage your account</button>
                            </div>
                        )
                        : (
                            null
                        )
                }     
            </div>
        );
    }

    
    showMenu = (event) => {
        event.preventDefault();

        this.setState({showMenu: true}, () => {
            document.addEventListener('click', this.closeMenu);
        });
    }

    closeMenu = () => {
        this.setState({showMenu: false}, () => {
            document.removeEventListener('click', this.closeMenu);
        });
    }

    handleSignOut = () => {
        localStorage.clear();
        this.props.history.push("/login");
    }
}

export default withRouter(UserDropdown);