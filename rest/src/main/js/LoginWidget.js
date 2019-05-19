import React from 'react'

class LoginWidget extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoading: false
        };
      }

    render () {
        return (
            <div>
              <div className='form-group'>
                <h1 className='text-center p-3'>Sign in</h1>
              </div>
              <div className='form-group'>
                <input className='form-control' type='text' placeholder='Email' />
              </div>
              <div className='form-group'>
                <input className='form-control' type='password' placeholder='Password' />
              </div>
              <div className='form-group'>
                <button className='btn btn-block btn-md btn-primary' onClick={event => this.onSignIn(event)}>Sign in</button>
              </div>
              <div className='form-group'>
                <button className='btn btn-block btn-md btn-success'>Register</button>
              </div>
            </div>
        )
    }

    onSignIn (event) {
        this.props.setParentLoading(true);
        this.setState({isLoading: true})
    }
}

export default LoginWidget