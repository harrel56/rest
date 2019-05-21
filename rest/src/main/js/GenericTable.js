import React from "react";
import Table from "semantic-ui-react/dist/es/collections/Table/Table";

const idField = "id";

class GenericTable extends React.Component {
  render() {
    return (
      <Table celled>
        <Table.Header>
          <Table.Row>
            {this.props.fields.map(header => (
              <Table.HeaderCell key={header}>{header}</Table.HeaderCell>
            ))}
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {this.props.data.map(row => (
            <Table.Row key={row[idField]}>
              {this.props.fields.map(field => (
                <Table.Cell key={field}>{row[field]}</Table.Cell>
              ))}
            </Table.Row>
          ))}
        </Table.Body>
      </Table>
    );
  }
}

export default GenericTable;
