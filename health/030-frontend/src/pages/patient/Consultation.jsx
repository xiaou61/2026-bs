import { useEffect, useState } from 'react'
import { Table, Button, Modal, Form, Input, Select, message, Tag, Rate } from 'antd'
import { PlusOutlined } from '@ant-design/icons'
import dayjs from 'dayjs'
import { getPatientConsultations, createConsultation, rateConsultation } from '../../api/consultation'
import { getVerifiedDoctors } from '../../api/doctor'

const Consultation = () => {
  const [data, setData] = useState([])
  const [doctors, setDoctors] = useState([])
  const [loading, setLoading] = useState(false)
  const [modalVisible, setModalVisible] = useState(false)
  const [rateModalVisible, setRateModalVisible] = useState(false)
  const [currentConsultation, setCurrentConsultation] = useState(null)
  const [form] = Form.useForm()
  const [rateForm] = Form.useForm()

  useEffect(() => {
    loadData()
    loadDoctors()
  }, [])

  const loadData = async () => {
    setLoading(true)
    try {
      const res = await getPatientConsultations()
      setData(res.data || [])
    } catch (error) {
      message.error('加载数据失败')
    } finally {
      setLoading(false)
    }
  }

  const loadDoctors = async () => {
    try {
      const res = await getVerifiedDoctors()
      setDoctors(res.data || [])
    } catch (error) {
      console.error(error)
    }
  }

  const handleAdd = () => {
    form.resetFields()
    setModalVisible(true)
  }

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields()
      await createConsultation(values)
      message.success('提交成功')
      setModalVisible(false)
      loadData()
    } catch (error) {
      message.error('提交失败')
    }
  }

  const handleRate = (record) => {
    setCurrentConsultation(record)
    rateForm.resetFields()
    setRateModalVisible(true)
  }

  const handleRateSubmit = async () => {
    try {
      const values = await rateForm.validateFields()
      await rateConsultation(currentConsultation.id, values.rating, values.feedback)
      message.success('评价成功')
      setRateModalVisible(false)
      loadData()
    } catch (error) {
      message.error('评价失败')
    }
  }

  const columns = [
    { title: '标题', dataIndex: 'title', key: 'title' },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status) => {
        const map = {
          PENDING: { color: 'orange', text: '待回复' },
          ANSWERED: { color: 'green', text: '已回复' }
        }
        return <Tag color={map[status]?.color}>{map[status]?.text}</Tag>
      }
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      key: 'createTime',
      render: (text) => dayjs(text).format('YYYY-MM-DD HH:mm')
    },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => (
        <>
          <Button type="link" onClick={() => Modal.info({
            title: '咨询详情',
            width: 600,
            content: (
              <div>
                <p><strong>问题：</strong>{record.question}</p>
                {record.answer && <p><strong>回复：</strong>{record.answer}</p>}
              </div>
            )
          })}>
            查看
          </Button>
          {record.status === 'ANSWERED' && !record.rating && (
            <Button type="link" onClick={() => handleRate(record)}>评价</Button>
          )}
        </>
      )
    }
  ]

  return (
    <div>
      <div style={{ marginBottom: 16, display: 'flex', justifyContent: 'space-between' }}>
        <h2>在线咨询</h2>
        <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
          发起咨询
        </Button>
      </div>

      <Table
        columns={columns}
        dataSource={data}
        rowKey="id"
        loading={loading}
      />

      <Modal
        title="发起咨询"
        open={modalVisible}
        onOk={handleSubmit}
        onCancel={() => setModalVisible(false)}
      >
        <Form form={form} layout="vertical">
          <Form.Item
            name="doctorId"
            label="选择医生"
            rules={[{ required: true, message: '请选择医生' }]}
          >
            <Select placeholder="请选择">
              {doctors.map(doc => (
                <Select.Option key={doc.userId} value={doc.userId}>
                  {doc.title} - {doc.hospital}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            name="title"
            label="标题"
            rules={[{ required: true, message: '请输入标题' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="question"
            label="问题描述"
            rules={[{ required: true, message: '请输入问题描述' }]}
          >
            <Input.TextArea rows={5} />
          </Form.Item>
        </Form>
      </Modal>

      <Modal
        title="评价咨询"
        open={rateModalVisible}
        onOk={handleRateSubmit}
        onCancel={() => setRateModalVisible(false)}
      >
        <Form form={rateForm} layout="vertical">
          <Form.Item
            name="rating"
            label="评分"
            rules={[{ required: true, message: '请评分' }]}
          >
            <Rate />
          </Form.Item>
          <Form.Item name="feedback" label="评价">
            <Input.TextArea rows={4} />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default Consultation
