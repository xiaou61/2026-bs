import { useEffect, useState } from 'react'
import { Table, Button, message, Tag, Popconfirm } from 'antd'
import { getPendingDoctors, verifyDoctor } from '../../api/doctor'

const AdminDoctors = () => {
  const [data, setData] = useState([])
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    setLoading(true)
    try {
      const res = await getPendingDoctors()
      setData(res.data || [])
    } catch (error) {
      message.error('加载数据失败')
    } finally {
      setLoading(false)
    }
  }

  const handleVerify = async (id, status) => {
    try {
      await verifyDoctor(id, status)
      message.success('操作成功')
      loadData()
    } catch (error) {
      message.error('操作失败')
    }
  }

  const columns = [
    { title: '职称', dataIndex: 'title', key: 'title' },
    { title: '医院', dataIndex: 'hospital', key: 'hospital' },
    { title: '科室', dataIndex: 'department', key: 'department' },
    { title: '专长', dataIndex: 'specialty', key: 'specialty' },
    { title: '执业证号', dataIndex: 'licenseNumber', key: 'licenseNumber' },
    {
      title: '状态',
      dataIndex: 'verifyStatus',
      key: 'verifyStatus',
      render: (status) => {
        const map = {
          0: { color: 'orange', text: '待审核' },
          1: { color: 'green', text: '已通过' },
          2: { color: 'red', text: '未通过' }
        }
        return <Tag color={map[status]?.color}>{map[status]?.text}</Tag>
      }
    },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => (
        <>
          <Popconfirm title="确认通过?" onConfirm={() => handleVerify(record.id, 1)}>
            <Button type="link">通过</Button>
          </Popconfirm>
          <Popconfirm title="确认拒绝?" onConfirm={() => handleVerify(record.id, 2)}>
            <Button type="link" danger>拒绝</Button>
          </Popconfirm>
        </>
      )
    }
  ]

  return (
    <div>
      <h2 style={{ marginBottom: 16 }}>医生审核</h2>
      <Table columns={columns} dataSource={data} rowKey="id" loading={loading} />
    </div>
  )
}

export default AdminDoctors
