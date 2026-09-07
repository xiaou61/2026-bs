import { useEffect, useState } from 'react'
import {
  Button,
  Form,
  Input,
  Modal,
  Popconfirm,
  Select,
  Space,
  Table,
  Tag,
  message
} from 'antd'
import {
  createKnowledge,
  deleteKnowledge,
  getAdminKnowledgeList,
  updateKnowledge,
  updateKnowledgeStatus
} from '../../api/knowledge'

const { TextArea } = Input

const presetCategories = ['慢病管理', '运动康复', '营养膳食', '心理健康', '老年照护']

const AdminKnowledge = () => {
  const [data, setData] = useState([])
  const [loading, setLoading] = useState(false)
  const [open, setOpen] = useState(false)
  const [submitting, setSubmitting] = useState(false)
  const [editingItem, setEditingItem] = useState(null)
  const [form] = Form.useForm()

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    setLoading(true)
    try {
      const res = await getAdminKnowledgeList()
      setData(res.data || [])
    } catch (error) {
      message.error('加载知识库数据失败')
    } finally {
      setLoading(false)
    }
  }

  const categoryOptions = Array.from(
    new Set([...presetCategories, ...data.map(item => item.category).filter(Boolean)])
  ).map(item => ({
    label: item,
    value: item
  }))

  const openCreateModal = () => {
    setEditingItem(null)
    form.resetFields()
    form.setFieldsValue({ status: 1, category: presetCategories[0] })
    setOpen(true)
  }

  const openEditModal = (record) => {
    setEditingItem(record)
    form.setFieldsValue({
      category: record.category,
      title: record.title,
      summary: record.summary,
      content: record.content,
      coverImage: record.coverImage,
      videoUrl: record.videoUrl,
      status: record.status
    })
    setOpen(true)
  }

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields()
      setSubmitting(true)
      if (editingItem) {
        await updateKnowledge(editingItem.id, values)
        message.success('健康知识更新成功')
      } else {
        await createKnowledge(values)
        message.success('健康知识创建成功')
      }
      setOpen(false)
      form.resetFields()
      setEditingItem(null)
      loadData()
    } catch (error) {
      if (error?.errorFields) {
        return
      }
      message.error(error.message || '保存失败')
    } finally {
      setSubmitting(false)
    }
  }

  const handleToggleStatus = async (record) => {
    const nextStatus = record.status === 1 ? 0 : 1
    try {
      await updateKnowledgeStatus(record.id, nextStatus)
      message.success(nextStatus === 1 ? '已发布' : '已下线')
      loadData()
    } catch (error) {
      message.error('状态更新失败')
    }
  }

  const handleDelete = async (id) => {
    try {
      await deleteKnowledge(id)
      message.success('删除成功')
      loadData()
    } catch (error) {
      message.error('删除失败')
    }
  }

  const columns = [
    {
      title: '标题',
      dataIndex: 'title',
      key: 'title',
      width: 240
    },
    {
      title: '分类',
      dataIndex: 'category',
      key: 'category',
      width: 120,
      render: (value) => <Tag color="blue">{value}</Tag>
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      width: 100,
      render: (status) => (
        <Tag color={status === 1 ? 'green' : 'orange'}>
          {status === 1 ? '已发布' : '已下线'}
        </Tag>
      )
    },
    {
      title: '浏览量',
      dataIndex: 'viewCount',
      key: 'viewCount',
      width: 100
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
      key: 'updateTime',
      width: 180,
      render: (value) => value ? value.replace('T', ' ').slice(0, 16) : '-'
    },
    {
      title: '操作',
      key: 'action',
      width: 240,
      render: (_, record) => (
        <Space wrap>
          <Button type="link" onClick={() => openEditModal(record)}>
            编辑
          </Button>
          <Button type="link" onClick={() => handleToggleStatus(record)}>
            {record.status === 1 ? '下线' : '发布'}
          </Button>
          <Popconfirm
            title="确认删除这篇知识内容吗？"
            onConfirm={() => handleDelete(record.id)}
          >
            <Button type="link" danger>
              删除
            </Button>
          </Popconfirm>
        </Space>
      )
    }
  ]

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: 16 }}>
        <h2 style={{ margin: 0 }}>知识库管理</h2>
        <Space>
          <Button onClick={loadData}>刷新</Button>
          <Button type="primary" onClick={openCreateModal}>
            新增知识
          </Button>
        </Space>
      </div>
      <Table
        columns={columns}
        dataSource={data}
        rowKey="id"
        loading={loading}
        scroll={{ x: 980 }}
      />

      <Modal
        title={editingItem ? '编辑健康知识' : '新增健康知识'}
        open={open}
        onCancel={() => {
          setOpen(false)
          setEditingItem(null)
          form.resetFields()
        }}
        onOk={handleSubmit}
        confirmLoading={submitting}
        destroyOnHidden
        width={760}
      >
        <Form form={form} layout="vertical">
          <Form.Item
            label="分类"
            name="category"
            rules={[{ required: true, message: '请选择分类' }]}
          >
            <Select
              options={categoryOptions}
              placeholder="请选择分类"
              showSearch
              optionFilterProp="label"
            />
          </Form.Item>
          <Form.Item
            label="标题"
            name="title"
            rules={[{ required: true, message: '请输入标题' }]}
          >
            <Input maxLength={200} placeholder="请输入知识标题" />
          </Form.Item>
          <Form.Item label="摘要" name="summary">
            <TextArea rows={3} maxLength={500} placeholder="请输入摘要" />
          </Form.Item>
          <Form.Item
            label="正文"
            name="content"
            rules={[{ required: true, message: '请输入正文内容' }]}
          >
            <TextArea rows={8} placeholder="请输入正文内容，支持换行展示" />
          </Form.Item>
          <Form.Item label="封面图片 URL" name="coverImage">
            <Input placeholder="可选：封面图片地址" />
          </Form.Item>
          <Form.Item label="视频链接" name="videoUrl">
            <Input placeholder="可选：视频链接地址" />
          </Form.Item>
          <Form.Item label="状态" name="status">
            <Select
              options={[
                { label: '已发布', value: 1 },
                { label: '已下线', value: 0 }
              ]}
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default AdminKnowledge
