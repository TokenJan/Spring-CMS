import React from 'react';
import axios from 'axios';
import { Modal, Form, Input } from 'antd';

const CollectionCreateForm = Form.create({ name: 'form_in_modal' })(
  // eslint-disable-next-line
  class extends React.Component {
    render() {
      const { visible, onCancel, onCreate, form } = this.props;
      const { getFieldDecorator } = form;
      return (
        <Modal
          visible={visible}
          title="Create a new content"
          okText="Create"
          onCancel={onCancel}
          onOk={onCreate}
        >
          <Form layout="vertical">
            <Form.Item label="Title">
              {getFieldDecorator('title', {
                rules: [{ required: true, message: 'Please input the title of content!' }],
              })(<Input />)}
            </Form.Item>
            <Form.Item label="Description">
              {getFieldDecorator('description')(<Input type="textarea" />)}
            </Form.Item>
          </Form>
        </Modal>
      );
    }
  },
);

export default class ContentModal extends React.Component {
  state = {
    visible: false,
  };

  showModal = () => {
    this.setState({ 
      visible: true,
    });
  };

  handleCancel = () => {
    this.setState({ visible: false });
  };

  handleCreate = () => {
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

      axios.post(`http://localhost:8080/api/contents`, content)
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
    const {contents, id} = this.props;
    return (
      <div 
        contents={contents}
        id={id}>
        <span onClick={this.showModal}>
          Brand
        </span>
        <CollectionCreateForm
          wrappedComponentRef={this.saveFormRef}
          visible={this.state.visible}
          onCancel={this.handleCancel}
          onCreate={this.handleCreate}
        />
      </div>
    );
  }
}