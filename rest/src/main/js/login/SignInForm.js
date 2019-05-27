import React from "react";
import Form from "semantic-ui-react/dist/es/collections/Form/Form";
import Header from "semantic-ui-react/dist/es/elements/Header/Header";
import Input from "semantic-ui-react/dist/es/elements/Input/Input";
import Button from "semantic-ui-react/dist/es/elements/Button/Button";

class SignInForm extends React.Component {
  constructor(props) {
    super(props);

    this.onSubmit = this.onSubmit.bind(this);
  }

  render() {
    return (
      <Form onSubmit={this.onSubmit}>
        <Header size="huge" textAlign="center">
          Sign in
        </Header>
        <Form.Field control={Input} type="text" placeholder="Email" />
        <Form.Field control={Input} type="password" placeholder="Password" />
        <Form.Field control={Button} type="submit" primary fluid>
          Sign in
        </Form.Field>
      </Form>
    );
  }

  onSubmit(event) {
    this.props.onSubmit(event);
  }
}

export default SignInForm;
