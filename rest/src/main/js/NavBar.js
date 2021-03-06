import React from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import Button from "semantic-ui-react/dist/es/elements/Button/Button";
import Menu from "semantic-ui-react/dist/es/collections/Menu/Menu";

import ModalWindow from "./ModalWindow";
import LoginWidget from "./login/LoginWidget";

class NavBar extends React.Component {
  render() {
    return (
      <Menu stackable>
        <Menu.Item>
          <img src="https://react.semantic-ui.com/logo.png" />
        </Menu.Item>
        <Menu.Item as={Link} to="/map">
          Map
        </Menu.Item>
        <Menu.Item as={Link} to="/events">
          Events
        </Menu.Item>
        <Menu.Item as={Link} to="/users">
          Users
        </Menu.Item>
        <Menu.Item position="right">
          {this.props.currentUserName ? (
            "Hello, " + this.props.currentUserName
          ) : (
            <ModalWindow
              size="mini"
              centered={false}
              trigger={<Button primary>Sign in</Button>}
            >
              <LoginWidget />
            </ModalWindow>
          )}
        </Menu.Item>
      </Menu>
    );
  }
}

function mapStateToProps(state) {
  return {
    currentUserName: state.currentUser.login
  };
}

export default connect(mapStateToProps)(NavBar);
