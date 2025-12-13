import { Table, Tag } from 'antd'

const AdminUsers = () => {
  const columns = [
    { title: '用户名', dataIndex: 'username', key: 'username' },
    { title: '真实姓名', dataIndex: 'realName', key: 'realName' },
    { title: '邮箱', dataIndex: 'email', key: 'email' },
    { title: '手机号', dataIndex: 'phone', key: 'phone' },
    {
      title: '角色',
      dataIndex: 'role',
      key: 'role',
      render: (role) => {
        const map = {
          PATIENT: '患者',
          DOCTOR: '医生',
          ADMIN: '管理员'
        }
        return <Tag>{map[role]}</Tag>
      }
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status) => (
        <Tag color={status === 1 ? 'green' : 'red'}>
          {status === 1 ? '正常' : '禁用'}
        </Tag>
      )
    }
  ]

  return (
    <div>
      <h2 style={{ marginBottom: 16 }}>用户管理</h2>
      <Table columns={columns} dataSource={[]} rowKey="id" />
    </div>
  )
}

export default AdminUsers
