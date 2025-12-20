import { Table, Button, Modal, Form, Input, message, Space } from 'antd'
import { useState } from 'react'
import { fetchList, postData, putData } from '../api'

function Photos() {
  const [data, setData] = useState<any[]>([])
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()
  const [orderId, setOrderId] = useState<string>('')

  const load = (oid?: string) => {
    if (!oid) return
    fetchList(`/photo/list/${oid}`).then(res => setData(res.data.data))
  }

  const onSubmit = async () => {
    const values = await form.validateFields()
    await postData('/photo', values)
    message.success('上传记录成功')
    setOpen(false)
    form.resetFields()
    load(orderId)
  }

  const onUpdate = async (record: any, isSelected: number, isRefined: number) => {
    await putData('/photo', { id: record.id, isSelected, isRefined })
    message.success('更新成功')
    load(orderId)
  }

  return (
    <div>
      <Input placeholder="输入订单ID" style={{ width: 240, marginBottom: 12 }} value={orderId} onChange={e => setOrderId(e.target.value)} onPressEnter={() => load(orderId)} />
      <Button type="primary" onClick={() => setOpen(true)} style={{ marginLeft: 8 }}>上传记录</Button>
      <Button onClick={() => load(orderId)} style={{ marginLeft: 8 }}>刷新</Button>
      <Table rowKey="id" dataSource={data} style={{ marginTop: 12 }} columns={[{title:'文件名',dataIndex:'fileName'},{title:'选片',dataIndex:'isSelected'},{title:'精修',dataIndex:'isRefined'},{title:'操作',render:(_:any,record:any)=>(<Space><a onClick={()=>onUpdate(record,1,record.isRefined)}>选中</a><a onClick={()=>onUpdate(record,record.isSelected,1)}>精修</a></Space>)}]} />
      <Modal open={open} title="上传记录" onCancel={()=>{setOpen(false);form.resetFields()}} onOk={onSubmit} destroyOnClose>
        <Form form={form} layout="vertical">
          <Form.Item name="orderId" label="订单ID" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="customerId" label="客户ID" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="fileName" label="文件名" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="filePath" label="文件路径" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="fileType" label="文件类型"><Input /></Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Photos
