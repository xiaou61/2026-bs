import { useState } from 'react'
import { Outlet, useNavigate, useLocation } from 'react-router-dom'
import { Layout as AntLayout, Menu, Avatar, Dropdown } from 'antd'
import {
  DashboardOutlined,
  HeartOutlined,
  FileTextOutlined,
  MessageOutlined,
  UserOutlined,
  MedicineBoxOutlined,
  LogoutOutlined,
  BookOutlined,
  TeamOutlined
} from '@ant-design/icons'
import useUserStore from '../store/useUserStore'

const { Header, Sider, Content } = AntLayout

const Layout = () => {
  const navigate = useNavigate()
  const location = useLocation()
  const { user, logout } = useUserStore()
  const [collapsed, setCollapsed] = useState(false)

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  const userMenu = {
    items: [
      {
        key: 'logout',
        icon: <LogoutOutlined />,
        label: '退出登录',
        onClick: handleLogout
      }
    ]
  }

  const getMenuItems = () => {
    if (user?.role === 'PATIENT') {
      return [
        { key: '/patient/dashboard', icon: <DashboardOutlined />, label: '健康概览' },
        { key: '/patient/health-data', icon: <HeartOutlined />, label: '健康数据' },
        { key: '/patient/health-record', icon: <FileTextOutlined />, label: '健康档案' },
        { key: '/patient/consultation', icon: <MessageOutlined />, label: '在线咨询' },
        { key: '/knowledge', icon: <BookOutlined />, label: '健康知识' },
        { key: '/patient/profile', icon: <UserOutlined />, label: '个人信息' }
      ]
    } else if (user?.role === 'DOCTOR') {
      return [
        { key: '/doctor/dashboard', icon: <DashboardOutlined />, label: '工作台' },
        { key: '/doctor/consultation', icon: <MessageOutlined />, label: '咨询管理' },
        { key: '/knowledge', icon: <BookOutlined />, label: '健康知识' },
        { key: '/doctor/profile', icon: <UserOutlined />, label: '个人信息' }
      ]
    } else if (user?.role === 'ADMIN') {
      return [
        { key: '/admin/dashboard', icon: <DashboardOutlined />, label: '系统概览' },
        { key: '/admin/users', icon: <TeamOutlined />, label: '用户管理' },
        { key: '/admin/doctors', icon: <MedicineBoxOutlined />, label: '医生审核' }
      ]
    }
    return []
  }

  return (
    <AntLayout style={{ minHeight: '100vh' }}>
      <Sider collapsible collapsed={collapsed} onCollapse={setCollapsed}>
        <div style={{ 
          height: 64, 
          display: 'flex', 
          alignItems: 'center', 
          justifyContent: 'center',
          color: 'white',
          fontSize: collapsed ? 16 : 18,
          fontWeight: 'bold'
        }}>
          {collapsed ? '健康' : '健康管理平台'}
        </div>
        <Menu
          theme="dark"
          selectedKeys={[location.pathname]}
          mode="inline"
          items={getMenuItems()}
          onClick={({ key }) => navigate(key)}
        />
      </Sider>
      <AntLayout>
        <Header style={{ 
          padding: '0 24px', 
          background: '#fff',
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center'
        }}>
          <div style={{ fontSize: 18, fontWeight: 'bold' }}>
            居民智能健康管理平台
          </div>
          <Dropdown menu={userMenu}>
            <div style={{ cursor: 'pointer', display: 'flex', alignItems: 'center', gap: 8 }}>
              <Avatar icon={<UserOutlined />} />
              <span>{user?.username}</span>
            </div>
          </Dropdown>
        </Header>
        <Content style={{ margin: 16, padding: 24, background: '#fff', minHeight: 280 }}>
          <Outlet />
        </Content>
      </AntLayout>
    </AntLayout>
  )
}

export default Layout
