import React from 'react';
import ContentTable from './ContentTable';
import ContentSider from './ContentSider';
import CreateContentDropdown from './CreateContentDropdown';
import { Layout, Menu } from 'antd';

const { Header, Content, Footer } = Layout;

export default class Container extends React.Component {
    render() {
        return (
            <Layout>
            <Header className="header">
              <div className="logo" />
              <Menu
                theme="dark"
                mode="horizontal"
                defaultSelectedKeys={['2']}
                style={{ lineHeight: '64px' }}
              >
                <Menu.Item key="1">nav 1</Menu.Item>
                <Menu.Item key="2">nav 2</Menu.Item>
                <Menu.Item key="3">nav 3</Menu.Item>
              </Menu>
            </Header>
            <Content style={{ padding: '0 50px' }}>
              <Layout style={{ padding: '24px 0', background: '#fff' }}>
                <ContentSider/>
                <Content style={{ padding: '0 24px', minHeight: 280 }}>
                  <CreateContentDropdown/>
                  <ContentTable/>
                </Content>
              </Layout>
            </Content>
            <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
          </Layout>
        )
    }
}