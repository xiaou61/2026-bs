import { Table, Button, Modal, Form, Input, InputNumber, message, Space } from 'antd'
import { useEffect, useState } from 'react'
import { fetchList, postData, putData, deleteData } from '../api'

function Studios() {
  const [data, setData] = useState<any[]>([])
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()

  const load = () => fetchList('/studio/list').then(res => setData(res.data.data))
  useEffect(() => { load() }, [])

  const onSubmit = async () => {
    const values = await form.validateFields()
    if (values.id) await putData('/studio', values)
    else await postData('/studio', values)
    message.success('保存成功')
    setOpen(false)
    form.resetFields()
    load()
  }

  const onEdit = (record: any) => { setOpen(true); form.setFieldsValue(record) }

  return (
    <div>
      <Button type="primary" onClick={() => setOpen(true)} style={{ marginBottom: 12 }}>新增影棚</Button>
      <Table rowKey="id" dataSource={data} columns={[{title:'名称',dataIndex:'name'},{title:'面积',dataIndex:'area'},{title:'风格',dataIndex:'style'},{title:'状态',dataIndex:'status'},{title:'操作',render:(_:any,record:any)=>(<Space><a onClick={()=>onEdit(record)}>编辑</a><a onClick={async()=>{await deleteData(`/studio/${record.id}`);message.success('已删除');load()}}>删除</a></Space>)}]} />
      <Modal open={open} title="影棚" onCancel={()=>{setOpen(false);form.resetFields()}} onOk={onSubmit} destroyOnClose>
        <Form form={form} layout="vertical">
          <Form.Item name="id" hidden><Input /></Form.Item>
          <Form.Item name="name" label="名称" rules={[{required:true}]}><Input /></Form.Item>
          <Form.Item name="area" label="面积"><InputNumber style={{width:'100%'}} min={0} /></Form.Item>
          <Form.Item name="style" label="风格"><Input /></Form.Item>
          <Form.Item name="status" label="状态"><Input /></Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Studios
