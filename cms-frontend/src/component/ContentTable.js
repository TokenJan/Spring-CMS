import React from 'react';
import axios from 'axios';
import { Table } from 'antd';
import EditContentModal from './EditContentModal'

export default class ContentTable extends React.Component {
    state = {
        dataSource: [],
        columns: [
            {
              title: 'Name',
              dataIndex: 'name',
              key: 'name',
            },
            {
              title: 'Updated',
              dataIndex: 'updatedTime',
              key: 'updatedTime',
            },
            {
              title: 'Status',
              dataIndex: 'status',
              key: 'status',
            },
            {
              title: 'Action',
              dataIndex: '',
              key: 'action',
            //   
            render: (record) => <div><EditContentModal contentid={record.key}>Edit</EditContentModal><a onClick={this.handlePublish.bind(this, record.key)}>Publish</a></div>,
            },
          ],
    }

    handlePublish(key) {
        axios.post(`http://localhost:8080/api/contents/` + key + `/publish`)
        .then(res => {
          const content = this.state.dataSource
          content[key-1]["name"] = res.data["name"];
          content[key-1]["updatedTime"] = res.data["updatedTime"];
          content[key-1]["status"] = res.data["status"];
          console.log(content);
          this.setState({
            dataSource: content
          });
        })
    }

    componentWillMount() {
        axios.get(`http://localhost:8080/api/contents`)
        .then(res => {
          const contents = res.data.simpleContentDtoList.map(content => {
              {
                content.key = content.id;
              }
              return content;
          })
          this.setState({ dataSource: contents });
        })
    }

    render() {
        return (
            <div>
                <Table columns={this.state.columns} dataSource={this.state.dataSource} size="large" />
            </div>
        )
    }
}