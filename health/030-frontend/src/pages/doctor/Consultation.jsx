import { useEffect, useState } from 'react'
import { Table, Button, Modal, Form, Input, message, Tag } from 'antd'
import dayjs from 'dayjs'
import { getDoctorConsultations, answerConsultation } from '../../api/consultation'

const DoctorConsultation = () => {
  const [data, setData] = useState([])
  const [loading, setLoading] = useState(false)
  const [modalVisible, setModalVisible] = useState(false)
  const [currentConsultation, setCurrentConsultation] = useState(null)
  const [form] = Form.useForm()

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    setLoading(true)
    try {
      const res = await getDoctorConsultations()
      setData(res.data || [])
    } catch (error) {
      message.error('加载数据失败')
    } finally {
      setLoading(false)
    }
  }

  const handleAnswer = (record) => {
    setCurrentConsultation(record)
    form.resetFields()
    setModalVisible(true)
  }

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields()
      await answerConsultation(currentConsultation.id, values.answer)
      message.success('回复成功')
      setModalVisible(false)
      loadData()
    } catch (error) {
      message.error('回复失败')
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
          {record.status === 'PENDING' && (
            <Button type="link" onClick={() => handleAnswer(record)}>回复</Button>
          )}
        </>
      )
    }
  ]

  return (
    <div>
      <h2 style={{ marginBottom: 16 }}>咨询管理</h2>

      <Table
        columns={columns}
        dataSource={data}
        rowKey="id"
        loading={loading}
      />

      <Modal
        title="回复咨询"
        open={modalVisible}
        onOk={handleSubmit}
        onCancel={() => setModalVisible(false)}
        width={600}
      >
        <div style={{ marginBottom: 16 }}>
          <p><strong>问题：</strong>{currentConsultation?.question}</p>
        </div>
        <Form form={form} layout="vertical">
          <Form.Item
            name="answer"
            label="回复内容"
            rules={[{ required: true, message: '请输入回复内容' }]}
          >
            <Input.TextArea rows={6} />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default DoctorConsultation
