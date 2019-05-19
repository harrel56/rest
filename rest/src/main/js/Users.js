import React from 'react'
import GenericTable from './GenericTable'
import {flattenJsonArray} from './utils/json.js'

const visibleFields = ['id', 'login', 'userDetails.name', 'userDetails.surname', 'userDetails.location', 'userDetails.dateOfBirth']

class Users extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          items: []
        };
      }

    componentDidMount() {
        fetch("http://localhost:8080/api/users")
          .then(res => res.json())
          .then(
            (result) => {
              this.setState({
                isLoaded: true,
                items: result.results
              });
            },
            // Note: it's important to handle errors here
            // instead of a catch() block so that we don't swallow
            // exceptions from actual bugs in components.
            (error) => {
              this.setState({
                isLoaded: true,
                error
              });
            }
          )
    }

    render() {
        const { error, isLoaded, items } = this.state;
        if (error) {
          return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
          return <div>Loading...</div>;
        } else {
          return (
            <div className='pt-5'>
                <GenericTable fields={visibleFields} data={flattenJsonArray(items)}/>
            </div>
          );
        }
    }
}

export default Users