import React from 'react';
import { Layout, Menu, Icon } from 'antd';

const { SubMenu } = Menu;
const { Sider } = Layout;

export default class Container extends React.Component {
    render() {
        return (                
        <Sider width={200} style={{ background: '#fff' }}>
        <Menu
          mode="inline"
          defaultSelectedKeys={['1']}
          defaultOpenKeys={['sub1']}
          style={{ height: '100%' }}
        >
          <SubMenu
            key="sub1"
            title={
              <span>
                <Icon type="user" />
                Content Type
              </span>
            }
          >
            <Menu.Item key="1">Brand</Menu.Item>
            <Menu.Item key="2">News</Menu.Item>
          </SubMenu>
          <SubMenu
            key="sub2"
            title={
              <span>
                <Icon type="laptop" />
                Status
              </span>
            }
          >
            <Menu.Item key="5">Published</Menu.Item>
            <Menu.Item key="6">Draft</Menu.Item>
            <Menu.Item key="7">Archived</Menu.Item>
          </SubMenu>
        </Menu>
      </Sider>)
    }
}