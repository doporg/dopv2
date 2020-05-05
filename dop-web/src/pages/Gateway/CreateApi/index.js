import React, { Component } from 'react';
import CreateActivityForm from './components/CreateActivityForm';

export default class CreateApi extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="create-api-page">
        <CreateActivityForm />
      </div>
    );
  }
}
