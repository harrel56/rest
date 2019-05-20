import React from "react";

const idField = "id";

class GenericTable extends React.Component {
  render() {
    return (
      <table className="table">
        <thead className="thead-dark">
          <tr>
            {this.props.fields.map(header => (
              <th scope="col" key={header}>
                {header}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {this.props.data.map(row => (
            <tr key={row[idField]}>
              {this.props.fields.map(field => (
                <td key={field}>{row[field]}</td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    );
  }
}

export default GenericTable;
