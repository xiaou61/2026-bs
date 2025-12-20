import { Table, Button, Modal, Form, Input, Select, DatePicker, InputNumber, message } from 'antd'
import { useEffect, useState } from 'react'
import { fetchList, postData } from '../api'

function Finance() {
  const [data, setData] = useState<any[]>([])
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()

  const load = () => fetchList('/financial/list').then(res => setData(res.data.data))
  useEffect(() => { load() }, [])

  const onSubmit = async () => {
    const values = await form.validateFields()
    values.recordDate = values.recordDate?.format('YYYY-MM-DD')
    await postData('/financial', values)
    message.success('保存成功')
    setOpen(false)
    form.resetFields()
    load()
  }

  return (
    <div>
      <Button type="primary" onClick={() => setOpen(true)} style={{ marginBottom: 12 }}>新增收支</Button>
      <Table rowKey="id" dataSource={data} columns={[{title:'类型',dataIndex:'recordType'},{title:'金额',dataIndex:'amount'},{title:'支付方式',dataIndex:'paymentMethod'},{title:'日期',dataIndex:'recordDate'}]} />
      <Modal open={open} title="收支记录" onCancel={()=>{setOpen(false);form.resetFields()}} onOk={onSubmit} destroyOnClose>
        <Form form={form} layout="vertical">
          <Form.Item name="recordType" label="类型" rules={[{required:true}]}><Select options={[{value:'INCOME',label:'收入'},{value:'EXPENSE',label:'支出'}]} /></Form.Item>
          <Form.Item name="amount" label="金额" rules={[{required:true}]}><InputNumber style={{width:'100%'}} min={0} /></Form.Item>
          <Form.Item name="paymentMethod" label="支付方式"><Input /></Form.Item>
          <Form.Item name="category" label="分类"><Input /></Form.Item>
          <Form.Item name="description" label="描述"><Input.TextArea rows={3} /></Form.Item>
          <Form.Item name="recordDate" label="日期" rules={[{required:true}]}><DatePicker style={{width:'100%'}} /></Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Finance
