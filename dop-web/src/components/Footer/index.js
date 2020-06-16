import React, { PureComponent } from 'react';
import Layout from '@icedesign/layout';
import cx from 'classnames';
import Logo from '../Logo/index';

import './index.scss';

export default class Footer extends PureComponent {
  render() {
    const { className, style } = this.props;
    return (
      <Layout.Footer
        className={cx('ice-design-layout-footer', className)}
        style={{
          ...style,
          lineHeight: '36px',
        }}
      >
        <div className="ice-design-layout-footer-body">
          <div style={{ filter: 'grayscale(100%)', opacity: 0.3 }}>
            <Logo style={{ color: '#666' }} />
          </div>
          <div className="copyright">
            © 2020 powered by NJU
          </div>
        </div>
      </Layout.Footer>
    );
  }
}
