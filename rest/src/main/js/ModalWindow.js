import React from "react";

class ModalWindow extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoading: false
    };

    this.setLoading = this.setLoading.bind(this);
  }

  render() {
    const children = React.Children.map(this.props.children, child => {
      return React.cloneElement(child, { setParentLoading: this.setLoading });
    });

    return (
      <div
        id={this.props.id}
        className="modal fade"
        role="dialog"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-sm" role="document">
          <div className="modal-content">
            <div className="modal-body">{children}</div>
            {this.state.isLoading && (
              <div className="modal-content overlay">
                <div className="spinner-border m-auto" role="status">
                  <span className="sr-only">Loading...</span>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    );
  }

  setLoading(loading) {
    let $modal = $("#" + this.props.id);
    $modal.data("bs.modal")._config.keyboard = false;
    $modal.data("bs.modal")._config.backdrop = "static";
    $modal.off("keydown.dismiss.bs.modal");
    this.setState({ isLoading: loading });
  }
}

export default ModalWindow;
