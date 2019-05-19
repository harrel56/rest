import React from 'react'
import ModalWindow from './ModalWindow';
import NavBar from './NavBar';
import LoginWidget from './LoginWidget';

const appName = 'RestApi'
const loginModalId = 'loginModal'

class Root extends React.Component {
  render () {
    return (
        <div>
            <NavBar name={appName}>
                <button type='button' className='btn btn-primary' data-toggle='modal' data-target={'#' + loginModalId}>
                  Sign in
                </button>
            </NavBar>


            <div className='w-screen mt-4'>
                {this.props.children}
            </div>

            <ModalWindow id={loginModalId}>
                <LoginWidget />
            </ModalWindow>
        </div>
    )
  }
}

export default Root