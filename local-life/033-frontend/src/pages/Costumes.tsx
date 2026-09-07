import { Table, Button, Modal, Form, Input, Select, message, Space } from 'antd'
import { useEffect, useState } from 'react'
import { fetchList, postData, putData, deleteData } from '../api'

function Costumes() {
  const [data, setData] = useState<any[]>([])
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()

  const load = () => fetchList('/costume/list').then(res => setData(res.data.data))
  useEffect(() => { load() }, [])

  const onSubmit = async () => {
    const values = await form.validateFields()
    if (values.id) await putData('/costume', values)
    else await postData('/costume', values)
    message.success('保存成功')
    setOpen(false)
    form.resetFields()
    load()
  }

  const onEdit = (record: any) => { setOpen(true); form.setFieldsValue(record) }

  return (
    <div>
      <Button type="primary" onClick={() => setOpen(true)} style={{ marginBottom: 12 }}>新增服装</Button>
      <Table rowKey="id" dataSource={data} columns={[{title:'编号',dataIndex:'code'},{title:'名称',dataIndex:'name'},{title:'类别',dataIndex:'category'},{title:'状态',dataIndex:'status'},{title:'操作',render:(_:any,record:any)=>(<Space><a onClick={()=>onEdit(record)}>编辑</a><a onClick={async()=>{await deleteData(`/costume/${record.id}`);message.success('已删除');load()}}>删除</a></Space>)}]} />
      <Modal open={open} title="服装" onCancel={()=>{setOpen(false);form.resetFields()}} onOk={onSubmit} destroyOnClose>
        <Form form={form} layout="vertical">
          <Form.Item name="id" hidden><Input /></Form.Item>
          <Form.Item name="code" label="编号" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="name" label="名称" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="category" label="类别"><Input /></Form.Item>
          <Form.Item name="status" label="状态"><Select options={[{value:'AVAILABLE',label:'可用'},{value:'BORROWED',label:'借出'}]} /></Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Costumes
