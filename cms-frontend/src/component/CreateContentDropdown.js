import React from 'react';
import { Menu, Dropdown, Icon } from 'antd';
import ContentModal from './ContentModal'

const menu = (
  <Menu>
    <Menu.Item>
        <ContentModal/>
    </Menu.Item>
  </Menu>
);

export default class ContentDropDown extends React.Component {
 render() {
    return (
    <Dropdown overlay={menu}>
        <a className="ant-dropdown-link" href="#">
          Add Content <Icon type="down" />
        </a>
    </Dropdown>)
 }
}


