import React from "react";
import Container from "semantic-ui-react/dist/es/elements/Container/Container";

import NavBar from "./NavBar";
import LoginWidget from "./login/LoginWidget";

const appName = "RestApi";
const loginModalId = "loginModal";

class Root extends React.Component {
  constructor(props) {
    super(props);
    this.LoginWidget = React.createRef();
  }

  render() {
    return (
      <div>
        <NavBar />
        <Container>{this.props.children}</Container>
      </div>
    );
    //(
    //   <div>
    //     <NavBar name={appName}>
    //       <button
    //         type="button"
    //         className="btn btn-primary"
    //         data-toggle="modal"
    //         data-target={"#" + loginModalId}
    //         onClick={() => this.LoginWidget.current.resetState()}
    //       >
    //         Sign in
    //       </button>
    //     </NavBar>

    //     <div className="container">{this.props.children}</div>

    //     <ModalWindow id={loginModalId}>
    //       <LoginWidget ref={this.LoginWidget} />
    //     </ModalWindow>
    //   </div>
    // );
  }
}

export default Root;
