import React from "react";
import { Tabs, Tab } from "./Tabs";
import "bootstrap/dist/css/bootstrap.min.css";
import ScanTaskList from "./ScanTaskList/ScanTaskList";
import CreateForm from "./CreateForm/CreateForm";
import {injectIntl, FormattedMessage} from "react-intl";

class Scan extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tabIndex: 0,
    };
  }

  changeView = (index) => {
    this.setState({
      tabIndex: index,
    });
  };
  render() {
    const tabIndex = this.state.tabIndex;
    return (
      <React.Fragment>
        <Tabs activeIndex={tabIndex} onTabChange={this.changeView}>
    <Tab>{this.props.intl.messages['scan.quick']}</Tab>
    <Tab>{this.props.intl.messages['scan.tasklist']}</Tab>
        </Tabs>
        {tabIndex === 0 && <CreateForm />}
        {tabIndex === 1 && <ScanTaskList />}
      </React.Fragment>
    );
  }
}

export default injectIntl(Scan);