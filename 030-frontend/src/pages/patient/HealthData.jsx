import { useEffect, useState } from 'react'
import { Table, Button, Modal, Form, Input, InputNumber, DatePicker, Select, message, Popconfirm } from 'antd'
import { PlusOutlined, DeleteOutlined } from '@ant-design/icons'
import dayjs from 'dayjs'
import { getHealthDataList, addHealthData, deleteHealthData } from '../../api/health'

const HealthData = () => {
  const [data, setData] = useState([])
  const [loading, setLoading] = useState(false)
  const [modalVisible, setModalVisible] = useState(false)
  const [form] = Form.useForm()

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    setLoading(true)
    try {
      const res = await getHealthDataList()
      setData(res.data || [])
    } catch (error) {
      message.error('加载数据失败')
    } finally {
      setLoading(false)
    }
  }

  const handleAdd = () => {
    form.resetFields()
    setModalVisible(true)
  }

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields()
      await addHealthData({
        ...values,
        measureTime: values.measureTime?.toISOString()
      })
      message.success('添加成功')
      setModalVisible(false)
      loadData()
    } catch (error) {
      message.error('添加失败')
    }
  }

  const handleDelete = async (id) => {
    try {
      await deleteHealthData(id)
      message.success('删除成功')
      loadData()
    } catch (error) {
      message.error('删除失败')
    }
  }

  const columns = [
    { title: '数据类型', dataIndex: 'dataType', key: 'dataType' },
    { title: '数值', dataIndex: 'value', key: 'value' },
    { title: '单位', dataIndex: 'unit', key: 'unit' },
    { 
      title: '测量时间', 
      dataIndex: 'measureTime', 
      key: 'measureTime',
      render: (text) => dayjs(text).format('YYYY-MM-DD HH:mm')
    },
    { title: '备注', dataIndex: 'remark', key: 'remark' },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => (
        <Popconfirm title="确认删除?" onConfirm={() => handleDelete(record.id)}>
          <Button type="link" danger icon={<DeleteOutlined />}>删除</Button>
        </Popconfirm>
      )
    }
  ]

  return (
    <div>
      <div style={{ marginBottom: 16, display: 'flex', justifyContent: 'space-between' }}>
        <h2>健康数据</h2>
        <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
          添加数据
        </Button>
      </div>
      
      <Table
        columns={columns}
        dataSource={data}
        rowKey="id"
        loading={loading}
      />

      <Modal
        title="添加健康数据"
        open={modalVisible}
        onOk={handleSubmit}
        onCancel={() => setModalVisible(false)}
      >
        <Form form={form} layout="vertical">
          <Form.Item
            name="dataType"
            label="数据类型"
            rules={[{ required: true, message: '请选择数据类型' }]}
          >
            <Select>
              <Select.Option value="血压">血压</Select.Option>
              <Select.Option value="血糖">血糖</Select.Option>
              <Select.Option value="体重">体重</Select.Option>
              <Select.Option value="心率">心率</Select.Option>
              <Select.Option value="体温">体温</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item
            name="value"
            label="数值"
            rules={[{ required: true, message: '请输入数值' }]}
          >
            <InputNumber style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item name="unit" label="单位">
            <Input placeholder="如: mmHg, kg, ℃" />
          </Form.Item>
          <Form.Item name="measureTime" label="测量时间">
            <DatePicker showTime style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item name="remark" label="备注">
            <Input.TextArea rows={3} />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default HealthData
