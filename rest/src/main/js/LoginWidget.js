import React from 'react'

class LoginWidget extends React.Component {
  render () {
    return (
        <form>
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
            <button className='btn btn-block btn-md btn-primary'>Sign in</button>
          </div>
          <div className='form-group'>
            <button className='btn btn-block btn-md btn-success'>Register</button>
          </div>
        </form>
    )
  }
}

export default LoginWidget