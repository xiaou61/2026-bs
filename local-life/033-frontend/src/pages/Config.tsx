import { Table, Button, Modal, Form, Input, message } from 'antd'
import { useEffect, useState } from 'react'
import { fetchList, postData } from '../api'

function Config() {
  const [data, setData] = useState<any[]>([])
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()

  const load = () => fetchList('/config/list').then(res => setData(res.data.data))
  useEffect(() => { load() }, [])

  const onSubmit = async () => {
    const values = await form.validateFields()
    await postData('/config', values)
    message.success('保存成功')
    setOpen(false)
    form.resetFields()
    load()
  }

  return (
    <div>
      <Button type="primary" onClick={() => setOpen(true)} style={{ marginBottom: 12 }}>新增配置</Button>
      <Table rowKey="id" dataSource={data} columns={[{title:'键',dataIndex:'configKey'},{title:'值',dataIndex:'configValue'},{title:'描述',dataIndex:'description'}]} />
      <Modal open={open} title="系统配置" onCancel={()=>{setOpen(false);form.resetFields()}} onOk={onSubmit} destroyOnClose>
        <Form form={form} layout="vertical">
          <Form.Item name="configKey" label="键" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="configValue" label="值" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="configType" label="类型"><Input /></Form.Item>
          <Form.Item name="description" label="描述"><Input /></Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Config
