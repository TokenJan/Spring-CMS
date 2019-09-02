import React from 'react';
import axios from 'axios';
import { Modal, Form, Input } from 'antd';

const CollectionEditForm = Form.create({ name: 'form_in_modal' })(
    // eslint-disable-next-line
    class extends React.Component {

      state = {
          content: {}
      }
      componentWillMount() {
        axios.get(`http://localhost:8080/api/contents/` + this.props.contentid['contentid'])
        .then(res => {
            this.setState({
                content: res.data['contentAttributeDtoList']
            })
          })
      }

      render() {
        const { visible, onCancel, onEdit, form, content } = this.props;
        const { getFieldDecorator } = form;

        return ( this.state.content.length 
            ? <Modal
            content={content}
            visible={visible}
            title="Edit the content"
            okText="Edit"
            onCancel={onCancel}
            onOk={onEdit}
          >
            <Form layout="vertical">
            <Form.Item label="Title">
              {getFieldDecorator('title', {
                initialValue: this.state.content[0]['value'],
                rules: [{ required: true, message: 'Please input the title of content!' }],
              })(<Input />)}
            </Form.Item>
            <Form.Item label="Description">
              {getFieldDecorator('description', {
                  initialValue: this.state.content[1]["value"],
              })(<Input type="textarea" />)}
            </Form.Item>
            </Form>
          </Modal>
          : null
        );
      }
    },
  );
  
  export default class EditContentModal extends React.Component {
    state = {
      visible: false,
    }
  
    showModal = () => {
      // console.log(this.props.contents.filter(content => {return content.id === this.props.id}));
      this.setState({ 
        visible: true,
      });
    };

    handleCancel = () => {
        this.setState({ visible: false });
      };
  
    handleEdit = () => {
      const { form } = this.formRef.props;
      form.validateFields((err, values) => {
        if (err) {
          return;
        }
  
        const content = { 
          "contentType": "Brand",
          "name": values["title"],
          "attributes": [
            {
              "key": "title",
              "value": values["title"],
              "type": "Text"
            },
            {
              "key": "description",
              "value": values["description"],
              "type": "Text"
            },
          ]
        }
  
        axios.post(`http://localhost:8080/api/contents/` + this.props.contentid + '/edit', content)
        .then(res => {
          console.log(res);
        })
  
        form.resetFields();
        this.setState({ visible: false });
      });
    };
  
    saveFormRef = formRef => {
      this.formRef = formRef;
    };
  
    render() {
      const contentid = this.props;
      return (
        <div contentid={contentid}>
          <a onClick={this.showModal}>
            Edit
          </a>
          <CollectionEditForm
            contentid={contentid}
            wrappedComponentRef={this.saveFormRef}
            visible={this.state.visible}
            onCancel={this.handleCancel}
            onEdit={this.handleEdit}
          />
        </div>
      );
    } 
  }