import React, { Fragment } from "react";
import Axios from "axios";
import { injectIntl } from "react-intl";
import { Card, Collapse, Descriptions } from "antd";
import "./SonarqubeCodeStyle.scss";
import { Loading } from "@icedesign/base";
import {
  API_GET_ISSUES,
  API_GET_SOURCES,
  API_GET_GENERALINFO,
  extractSourcePath,
} from "../utility";
const { Panel } = Collapse;

class ShowScanResult extends React.Component {
  state = {
    projectKey: this.props.match.params.projectKey,
    issues: [],
    issuesTotal: 0,
    issuesEffortTotal: 0,
    measures: [],
    allSources: new Map(),
  };

  componentDidMount() {
    this.fetchData();
  }

  fetchData = () => {
    const { projectKey } = this.state;

    Axios.get(API_GET_ISSUES, {
      params: {
        projectKey: projectKey,
      },
    }).then((response) => {
      const issues = response.data.issues || [];
      this.setState({
        issues: response.data.issues || [],
        issuesTotal: response.data.total,
        issuesEffortTotal: response.data.effortTotal,
      });
      const allSources = new Map();
      for (const { component } of issues) {
        allSources.set(component, []);
      }

      Promise.all(
        [...allSources.keys()].map((eachComponent) =>
          Axios.get(API_GET_SOURCES, {
            params: { key: eachComponent },
          }).then((sourceResponse) => {
            allSources.set(eachComponent, sourceResponse.data.sources || []);
          })
        )
      ).then(() => {
        console.log(allSources),
          this.setState({
            allSources: allSources,
          });
      });
    });

    Axios.get(API_GET_GENERALINFO, {
      params: { projectKey: projectKey },
    }).then((response) => {
      this.setState({
        measures: response.data.measures || [],
      });
    });
  };

  render() {
    const {
      projectKey,
      issues,
      issuesTotal,
      issuesEffortTotal,
      measures = [],
      allSources = new Map(),
    } = this.state;
    const { messages } = this.props.intl;
    const pageIntl = "scan.result";
    return (
      <div>
        <Card>
          <Descriptions title={messages[`${pageIntl}.measures.title`]} bordered>
            {measures.map(({ metric, value }) => (
              <Descriptions.Item
                key={metric}
                label={messages[`${pageIntl}.measures.${metric}`]}
              >
                {value==null?'N/A':value}
              </Descriptions.Item>
            ))}
          </Descriptions>
        </Card>

        <Card style={{ marginTop: "24px" }}>
          <Descriptions title={messages[`${pageIntl}.issues.title`]}>
            <Descriptions.Item label={messages[`${pageIntl}.issues.total`]}>
              {issuesTotal}
            </Descriptions.Item>
          </Descriptions>
          <Collapse>
            {[...allSources.keys()].map((key) => {
              const sources = allSources.get(key);
              const relatedIssue = issues.filter(
                (item) => item.component === key
              );

              return (
                <Panel header={extractSourcePath(key)} key={key}>
                  <pre className="sonarqube-code">
                    {sources.map(({ code, line }) => {
                      const issue = relatedIssue.find(
                        (issue) => issue.line === line
                      );
                      const addon = issue ? (
                        <Card size="small" className="sonarqube-code__issue">
                          <Descriptions size="small">
                            <Descriptions.Item
                              label={messages[`${pageIntl}.issues.message`]}
                              span={3}
                            >
                              {issue.message}
                            </Descriptions.Item>
                            <Descriptions.Item
                              label={messages[`${pageIntl}.issues.severity`]}
                            >
                              {
                                messages[
                                  `${pageIntl}.issues.severity.${issue.severity}`
                                ]
                              }
                            </Descriptions.Item>
                            <Descriptions.Item
                              label={messages[`${pageIntl}.issues.type`]}
                            >
                              {
                                messages[
                                  `${pageIntl}.issues.type.${issue.type}`
                                ]
                              }
                            </Descriptions.Item>
                            <Descriptions.Item
                              label={messages[`${pageIntl}.issues.effort`]}
                            >
                              {
                                issue.effort
                              }
                            </Descriptions.Item>
                          </Descriptions>
                        </Card>
                      ) : undefined;
                      const issueClassName = issue ? " --issue" : "";

                      return (
                        <Fragment key={line}>
                          <div
                            className={`sonarqube-code__line${issueClassName}`}
                          >
                            {line}
                          </div>
                          <div
                            className={`sonarqube-code__code${issueClassName}`}
                            dangerouslySetInnerHTML={{ __html: code }}
                          />
                          {addon}
                        </Fragment>
                      );
                    })}
                  </pre>
                </Panel>
              );
            })}
          </Collapse>
        </Card>
      </div>
    );
  }
}

export default injectIntl(ShowScanResult);
