import React, { Fragment } from "react";
import { connect, dispatch } from "react-redux";
import axios from "axios";
import Form from "semantic-ui-react/dist/es/collections/Form/Form";
import Button from "semantic-ui-react/dist/es/elements/Button/Button";
import Dimmer from "semantic-ui-react/dist/es/modules/Dimmer/Dimmer";
import Loader from "semantic-ui-react/dist/es/elements/Loader/Loader";

import SignInForm from "./SignInForm";
import RegisterForm from "./RegisterForm";
import { formDataToJson } from "../utils/json.js";

const tabs = {
  signIn: "signIn",
  register: "register"
};

const emptyValidation = {
  field: null,
  message: null,
  state: null
};

class LoginWidget extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      validation: emptyValidation,
      isLoading: false,
      activeTab: tabs.signIn
    };

    this.setLoading = this.setLoading.bind(this);
    this.onRegister = this.onRegister.bind(this);
  }

  render() {
    return (
      <Fragment>
        <Dimmer className="rounded" inverted active={this.state.isLoading}>
          <Loader />
        </Dimmer>
        {this.renderTab()}
      </Fragment>
    );
  }

  renderTab() {
    switch (this.state.activeTab) {
      case tabs.signIn:
        return (
          <Fragment>
            <SignInForm
              onSubmit={() =>
                this.props.dispatch({
                  type: "USER_LOGGED",
                  payload: { currentUser: { login: "stubbie" } }
                })
              }
            />
            <Form.Field
              className="mt-1"
              control={Button}
              onClick={() => this.switchTab(tabs.register)}
              secondary
              fluid
            >
              Register
            </Form.Field>
          </Fragment>
        );
      case tabs.register:
        return (
          <Fragment>
            <RegisterForm
              onSubmit={this.onRegister}
              validation={this.state.validation}
            />
            <Form.Field
              className="mt-1"
              control={Button}
              onClick={() => this.switchTab(tabs.signIn)}
              primary
              fluid
            >
              Sign in instead
            </Form.Field>
          </Fragment>
        );
    }
  }

  switchTab(tab) {
    this.setState({ activeTab: tab });
  }

  setLoading(loading) {
    this.setState({ isLoading: loading });
  }

  onRegister(event) {
    this.setLoading(true);
    const data = new FormData(event.target);
    this.sendRegistrationData(formDataToJson(data));
  }

  sendRegistrationData(data) {
    axios
      .post("/api/user-management/register", data)
      .then(res => {
        console.log(res);
        this.setState({
          validation: emptyValidation,
          isLoading: false
        });
      })
      .catch(err => {
        console.log(err.response.data);
        this.setState({
          validation: err.response.data,
          isLoading: false
        });
      });
  }
}

function mapDispatchToProps(state) {
  return {
    onSignIn: () => {
      console.log("onSignIn");
      const action = {
        type: "USER_LOGGED",
        payload: { currentUser: { login: "stubbie" } }
      };
      dispatch(action);
    }
  };
}

export default connect()(LoginWidget);
