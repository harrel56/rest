import React, { Fragment } from "react";
import TransitionablePortal from "semantic-ui-react/dist/es/addons/TransitionablePortal/TransitionablePortal";
import Modal from "semantic-ui-react/dist/es/modules/Modal/Modal";

class ModalWindow extends React.Component {
  constructor(props) {
    super(props);
    this.onOpen = this.onOpen.bind(this);
    this.onClose = this.onClose.bind(this);

    this.state = {
      open: false,
      triggerButton: React.cloneElement(this.props.trigger, {
        onClick: this.onOpen
      })
    };
  }

  render() {
    const { open } = this.state;
    const transition = {
      animation: "scale",
      duration: 300
    };

    return (
      <Fragment>
        {this.state.triggerButton}
        <TransitionablePortal {...{ open }} {...{ transition }}>
          <Modal
            open
            onOpen={this.onOpen}
            onClose={this.onClose}
            size={this.props.size}
            centered={this.props.centered}
            closeOnDimmerClick={false}
            closeIcon={true}
          >
            <Modal.Content className="rounded">
              {this.props.children}
            </Modal.Content>
          </Modal>
        </TransitionablePortal>
      </Fragment>
    );
  }

  onOpen() {
    setTimeout(() => document.body.classList.add("modal-fade-in"));
    this.setState({ open: true });
  }

  onClose() {
    document.body.classList.remove("modal-fade-in");
    this.setState({ open: false });
  }
}

export default ModalWindow;
