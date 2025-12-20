import { Table, Button, Modal, Form, Input, Select, message, Space } from 'antd'
import { useEffect, useState } from 'react'
import { fetchList, postData, putData, deleteData } from '../api'

function Photographers() {
  const [data, setData] = useState<any[]>([])
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()

  const load = () => fetchList('/photographer/list').then(res => setData(res.data.data))
  useEffect(() => { load() }, [])

  const onSubmit = async () => {
    const values = await form.validateFields()
    if (values.id) await putData('/photographer', values)
    else await postData('/photographer', values)
    message.success('保存成功')
    setOpen(false)
    form.resetFields()
    load()
  }

  const onEdit = (record: any) => { setOpen(true); form.setFieldsValue(record) }

  return (
    <div>
      <Button type="primary" onClick={() => setOpen(true)} style={{ marginBottom: 12 }}>新增摄影师</Button>
      <Table rowKey="id" dataSource={data} columns={[{title:'姓名',dataIndex:'name'},{title:'等级',dataIndex:'level'},{title:'电话',dataIndex:'phone'},{title:'状态',dataIndex:'status'},{title:'操作',render:(_:any,record:any)=>(<Space><a onClick={()=>onEdit(record)}>编辑</a><a onClick={async()=>{await deleteData(`/photographer/${record.id}`);message.success('已删除');load()}}>删除</a></Space>)}]} />
      <Modal open={open} title="摄影师" onCancel={()=>{setOpen(false);form.resetFields()}} onOk={onSubmit} destroyOnClose>
        <Form form={form} layout="vertical">
          <Form.Item name="id" hidden><Input /></Form.Item>
          <Form.Item name="name" label="姓名" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="phone" label="电话"><Input /></Form.Item>
          <Form.Item name="level" label="等级"><Select options={[{value:'首席',label:'首席'},{value:'高级',label:'高级'},{value:'资深',label:'资深'}]} /></Form.Item>
          <Form.Item name="status" label="状态"><Select options={[{value:1,label:'启用'},{value:0,label:'禁用'}]} /></Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Photographers
