import React from "react";
import { Link } from "react-router-dom";

class NavBar extends React.Component {
  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          <div className="navbar-header">
            <a className="navbar-brand" href="/">
              {this.props.name}
            </a>
          </div>

          <ul className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/map">
                Map
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/events">
                Events
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/users">
                Users
              </Link>
            </li>
          </ul>

          <div className="navbar-right">{this.props.children}</div>
        </div>
      </nav>
    );
  }
}

export default NavBar;
