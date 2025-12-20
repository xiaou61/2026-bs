import { Table, Button, Modal, Form, Input, InputNumber, Select, message, Space } from 'antd'
import { useEffect, useState } from 'react'
import { fetchList, postData, putData } from '../api'

function Orders() {
  const [data, setData] = useState<any[]>([])
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()

  const load = () => fetchList('/order/list').then(res => setData(res.data.data))
  useEffect(() => { load() }, [])

  const onSubmit = async () => {
    const values = await form.validateFields()
    if (values.id) await putData(`/order/${values.id}/status?status=${values.status}&paidAmount=${values.paidAmount || ''}`, {})
    else await postData('/order', values)
    message.success('保存成功')
    setOpen(false)
    form.resetFields()
    load()
  }

  const onEdit = (record: any) => { setOpen(true); form.setFieldsValue(record) }

  return (
    <div>
      <Button type="primary" onClick={() => setOpen(true)} style={{ marginBottom: 12 }}>新建订单/更新状态</Button>
      <Table rowKey="id" dataSource={data} columns={[{title:'订单号',dataIndex:'orderNo'},{title:'客户',dataIndex:'customerId'},{title:'套餐',dataIndex:'packageId'},{title:'状态',dataIndex:'status'},{title:'已付',dataIndex:'paidAmount'},{title:'操作',render:(_:any,record:any)=>(<Space><a onClick={()=>onEdit(record)}>编辑/状态</a></Space>)}]} />
      <Modal open={open} title="订单" onCancel={()=>{setOpen(false);form.resetFields()}} onOk={onSubmit} destroyOnClose>
        <Form form={form} layout="vertical">
          <Form.Item name="id" hidden><Input /></Form.Item>
          <Form.Item name="customerId" label="客户ID" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="packageId" label="套餐ID" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="totalAmount" label="总金额" rules={[{required:true}]}><InputNumber style={{width:'100%'}} min={0} /></Form.Item>
          <Form.Item name="status" label="状态"><Select options={[{value:'PENDING',label:'待支付'},{value:'PAID',label:'已支付'},{value:'IN_PROGRESS',label:'进行中'},{value:'DONE',label:'完成'}]} /></Form.Item>
          <Form.Item name="paidAmount" label="已付金额"><InputNumber style={{width:'100%'}} min={0} /></Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Orders
