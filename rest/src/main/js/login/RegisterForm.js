import React from "react";
import Form from "semantic-ui-react/dist/es/collections/Form/Form";
import Header from "semantic-ui-react/dist/es/elements/Header/Header";
import Input from "semantic-ui-react/dist/es/elements/Input/Input";
import Button from "semantic-ui-react/dist/es/elements/Button/Button";
import Message from "semantic-ui-react/dist/es/collections/Message/Message";

const errorClass = "error-border";

const fields = {
  login: "login",
  email: "email",
  password: "password"
};

const loginValidation = {
  field: "login",
  message: "Login is required"
};

const emailValidation = {
  field: "email",
  message: "Email is required"
};

const passwordValidation = {
  field: "password",
  message: "Password is required"
};

const passwordCompareValidation = {
  field: "password",
  message: "Passwords are not the same"
};

class RegisterForm extends React.Component {
  constructor(props) {
    super(props);
    this.onSubmit = this.onSubmit.bind(this);

    this.state = {
      login: "",
      email: "",
      password1: "",
      password2: "",
      validation: this.props.validation
    };
  }

  render() {
    const field = this.state.validation.field || this.props.validation.field;
    const message =
      this.state.validation.message || this.props.validation.message;

    return (
      <Form onSubmit={this.onSubmit} error={field !== null}>
        <Header size="huge" textAlign="center">
          Register
        </Header>
        <Form.Field
          control={Input}
          name={fields.login}
          type="text"
          placeholder="Login"
          onChange={e => this.setState({ login: e.target.value })}
          className={fields.login === field ? errorClass : ""}
        />
        {fields.login === field && <Message error content={message} />}
        <Form.Field
          control={Input}
          name={fields.email}
          type="text"
          placeholder="Email"
          onChange={e => this.setState({ email: e.target.value })}
          className={fields.email === field ? errorClass : ""}
        />
        {fields.email === field && <Message error content={message} />}
        <Form.Field
          control={Input}
          name={fields.password}
          type="password"
          placeholder="Password"
          onChange={e => this.setState({ password1: e.target.value })}
          className={fields.password === field ? errorClass : ""}
        />
        <Form.Field
          control={Input}
          type="password"
          placeholder="Confirm Password"
          onChange={e => this.setState({ password2: e.target.value })}
          className={fields.password === field ? errorClass : ""}
        />
        {fields.password === field && <Message error content={message} />}
        <Form.Field control={Button} type="submit" secondary fluid>
          Create account
        </Form.Field>
        {!field && message && <Message error content={message} />}
      </Form>
    );
  }

  onSubmit(event) {
    const validationData = this.validate();
    if (!validationData) {
      this.setState({ validation: { field: "", message: "" } });
      this.props.onSubmit(event);
    } else {
      this.setState({ validation: validationData });
    }
  }

  validate() {
    if (!this.state.login) {
      return loginValidation;
    } else if (!this.state.email) {
      return emailValidation;
    } else if (!this.state.password1 || !this.state.password2) {
      return passwordValidation;
    } else if (this.state.password1 !== this.state.password2) {
      return passwordCompareValidation;
    } else {
      return null;
    }
  }
}

export default RegisterForm;
