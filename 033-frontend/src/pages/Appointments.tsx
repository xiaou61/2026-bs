import { Table, Button, Modal, Form, Input, DatePicker, Select, message, Space } from 'antd'
import { useEffect, useState } from 'react'
import dayjs from 'dayjs'
import { fetchList, postData, putData } from '../api'

function Appointments() {
  const [data, setData] = useState<any[]>([])
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()

  const load = () => fetchList('/appointment/list').then(res => setData(res.data.data))
  useEffect(() => { load() }, [])

  const onSubmit = async () => {
    const values = await form.validateFields()
    values.appointmentDate = values.appointmentDate?.format('YYYY-MM-DD')
    if (values.id) await putData('/appointment', values)
    else await postData('/appointment', values)
    message.success('保存成功')
    setOpen(false)
    form.resetFields()
    load()
  }

  const onEdit = (record: any) => { setOpen(true); form.setFieldsValue({ ...record, appointmentDate: record.appointmentDate ? dayjs(record.appointmentDate) : null }) }

  return (
    <div>
      <Button type="primary" onClick={() => setOpen(true)} style={{ marginBottom: 12 }}>新增预约</Button>
      <Table rowKey="id" dataSource={data} columns={[{title:'客户',dataIndex:'customerId'},{title:'日期',dataIndex:'appointmentDate'},{title:'时段',dataIndex:'timeSlot'},{title:'状态',dataIndex:'status'},{title:'操作',render:(_:any,record:any)=>(<Space><a onClick={()=>onEdit(record)}>编辑</a></Space>)}]} />
      <Modal open={open} title="预约" onCancel={()=>{setOpen(false);form.resetFields()}} onOk={onSubmit} destroyOnClose>
        <Form form={form} layout="vertical">
          <Form.Item name="id" hidden><Input /></Form.Item>
          <Form.Item name="customerId" label="客户ID" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="appointmentDate" label="日期" rules={[{required:true}]}><DatePicker style={{width:'100%'}} /></Form.Item>
          <Form.Item name="timeSlot" label="时段" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="status" label="状态"><Select options={[{value:'PENDING',label:'待确认'},{value:'CONFIRMED',label:'已确认'},{value:'CANCELLED',label:'已取消'}]} /></Form.Item>
          <Form.Item name="remark" label="备注"><Input.TextArea rows={3} /></Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Appointments
