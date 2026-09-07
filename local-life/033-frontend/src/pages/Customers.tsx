import { Table, Button, Space, Modal, Form, Input, message } from 'antd'
import { useEffect, useState } from 'react'
import { fetchList, postData, putData, deleteData } from '../api'

function Customers() {
  const [data, setData] = useState<any[]>([])
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()

  const load = () => fetchList('/customer/list').then(res => setData(res.data.data))
  useEffect(() => { load() }, [])

  const onSubmit = async () => {
    const values = await form.validateFields()
    if (values.id) await putData('/customer', values)
    else await postData('/customer', values)
    message.success('保存成功')
    setOpen(false)
    form.resetFields()
    load()
  }

  const onEdit = (record: any) => { setOpen(true); form.setFieldsValue(record) }

  return (
    <div>
      <Button type="primary" onClick={() => setOpen(true)} style={{ marginBottom: 12 }}>新增客户</Button>
      <Table rowKey="id" dataSource={data} columns={[{title:'姓名',dataIndex:'name'},{title:'电话',dataIndex:'phone'},{title:'类型',dataIndex:'customerType'},{title:'来源',dataIndex:'source'},{title:'操作',render:(_:any,record:any)=>(<Space><a onClick={()=>onEdit(record)}>编辑</a><a onClick={async()=>{await deleteData(`/customer/${record.id}`);message.success('已删除');load()}}>删除</a></Space>)}]} />
      <Modal open={open} title="客户" onCancel={()=>{setOpen(false);form.resetFields()}} onOk={onSubmit} destroyOnClose>
        <Form form={form} layout="vertical">
          <Form.Item name="id" hidden><Input /></Form.Item>
          <Form.Item name="name" label="姓名" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="phone" label="电话" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="customerType" label="类型"><Input /></Form.Item>
          <Form.Item name="source" label="来源"><Input /></Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Customers
