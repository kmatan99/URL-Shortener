import React from 'react';
import ReactDOM from 'react-dom';

import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import Signup from './components/registerform/Signup.js';
import Login from './components/registerform/Login.js';


import './index.css';
import App from './components/App';
import * as serviceWorker from './serviceWorker';

ReactDOM.render(
  <React.StrictMode>
    <Router>

      <Switch>
        <Route path="/signup">
          <Signup />
        </Route>

        <Route path="/login">
          <Login />
        </Route>

        <Route path="/">
          <App />
        </Route>
      </Switch>

    </Router>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
