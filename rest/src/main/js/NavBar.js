import React from 'react'

class NavBar extends React.Component {
  render () {
    return (
        <nav className='navbar navbar-expand-lg navbar-light bg-light'>
            <div className='container-fluid'>
                <div className="navbar-header">
                  <a className="navbar-brand" href="#">{this.props.name}</a>
                </div>

                <ul className='navbar-nav mr-auto'>
                    <li className='nav-item'><a className='nav-link' href="map">Map</a></li>
                    <li className='nav-item'><a className='nav-link' href="events">Events</a></li>
                    <li className='nav-item'><a className='nav-link' href="users">Users</a></li>
                </ul>

                <div className='navbar-right'>
                    {this.props.children}
                </div>
            </div>
        </nav>
    )
  }
}

export default NavBar