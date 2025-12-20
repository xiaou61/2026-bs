import { Table, Button, Space, Modal, Form, Input, InputNumber, message } from 'antd'
import { useEffect, useState } from 'react'
import { fetchList, postData, putData, deleteData } from '../api'

function Packages() {
  const [data, setData] = useState<any[]>([])
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()

  const load = () => fetchList('/package/list').then(res => setData(res.data.data))
  useEffect(() => { load() }, [])

  const onSubmit = async () => {
    const values = await form.validateFields()
    if (values.id) await putData('/package', values)
    else await postData('/package', values)
    message.success('保存成功')
    setOpen(false)
    form.resetFields()
    load()
  }

  const onEdit = (record: any) => { setOpen(true); form.setFieldsValue(record) }

  return (
    <div>
      <Button type="primary" onClick={() => setOpen(true)} style={{ marginBottom: 12 }}>新增套餐</Button>
      <Table rowKey="id" dataSource={data} columns={[{title:'名称',dataIndex:'name'},{title:'分类',dataIndex:'category'},{title:'价格',dataIndex:'price'},{title:'销量',dataIndex:'salesCount'},{title:'操作',render:(_:any,record:any)=>(<Space><a onClick={()=>onEdit(record)}>编辑</a><a onClick={async()=>{await deleteData(`/package/${record.id}`);message.success('已删除');load()}}>删除</a></Space>)}]} />
      <Modal open={open} title="套餐" onCancel={()=>{setOpen(false);form.resetFields()}} onOk={onSubmit} destroyOnClose>
        <Form form={form} layout="vertical">
          <Form.Item name="id" hidden><Input /></Form.Item>
          <Form.Item name="name" label="名称" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="category" label="分类"><Input /></Form.Item>
          <Form.Item name="price" label="价格" rules={[{required:true}]}><InputNumber style={{width:'100%'}} min={0} /></Form.Item>
          <Form.Item name="description" label="描述"><Input.TextArea rows={3} /></Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Packages
