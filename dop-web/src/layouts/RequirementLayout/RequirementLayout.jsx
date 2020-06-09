import React, { Component } from 'react';
import cx from 'classnames';
import Layout from '@icedesign/layout';
import Header from '../../components/Header';
import Aside from '../../components/RequirementAside';
import Footer from '../../components/Footer';

import './RequirementLayout.scss';

const theme = 'dark';

export default class RequirementLayout extends Component {
    render() {
        return (
            <Layout
                fixable
                style={{ minHeight: '100vh' }}
                className={cx(`basic-layout-${theme}-code ice-design-layout`)}
            >
                <Header theme={theme} />

                <Layout.Section>
                    <Layout.Aside width={240}>
                        <Aside />
                    </Layout.Aside>

                    <Layout.Main scrollable>
                        {this.props.children}
                        <Footer />
                    </Layout.Main>
                </Layout.Section>
            </Layout>
        );
    }
}
