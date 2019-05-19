import React from 'react'

class ModalWindow extends React.Component {
  render () {
    return (
        <div id={this.props.id} className='modal fade' role='dialog' aria-hidden='true'>
            <div className='modal-dialog modal-sm' role='document'>
                <div className='modal-content'>
                    <div className='modal-body'>
                        {this.props.children}
                    </div>
                </div>
            </div>
        </div>
    )
  }
}

export default ModalWindow