import { Layout, Menu } from 'antd'
import { Outlet, useLocation, useNavigate } from 'react-router-dom'
import { DashboardOutlined, TeamOutlined, GiftOutlined, CalendarOutlined, ShoppingCartOutlined, CameraOutlined, HomeOutlined, SkinOutlined, PictureOutlined, DollarOutlined, SettingOutlined } from '@ant-design/icons'
import { useEffect, useMemo } from 'react'

const { Header, Sider, Content } = Layout

const items = [
  { key: '/dashboard', icon: <DashboardOutlined />, label: '概览' },
  { key: '/customers', icon: <TeamOutlined />, label: '客户' },
  { key: '/packages', icon: <GiftOutlined />, label: '套餐' },
  { key: '/appointments', icon: <CalendarOutlined />, label: '预约' },
  { key: '/orders', icon: <ShoppingCartOutlined />, label: '订单' },
  { key: '/photographers', icon: <CameraOutlined />, label: '摄影师' },
  { key: '/studios', icon: <HomeOutlined />, label: '影棚' },
  { key: '/costumes', icon: <SkinOutlined />, label: '服装道具' },
  { key: '/photos', icon: <PictureOutlined />, label: '相册' },
  { key: '/finance', icon: <DollarOutlined />, label: '财务' },
  { key: '/config', icon: <SettingOutlined />, label: '系统配置' }
]

function AppLayout() {
  const location = useLocation()
  const navigate = useNavigate()
  const selectedKeys = useMemo(() => {
    const found = items.find(i => location.pathname.startsWith(i.key))
    return [found ? found.key : '/dashboard']
  }, [location.pathname])

  useEffect(() => {
    if (location.pathname === '/') navigate('/dashboard', { replace: true })
  }, [location.pathname, navigate])

  return (
    <Layout>
      <Sider theme="light">
        <div style={{ height: 56, display: 'flex', alignItems: 'center', justifyContent: 'center', fontWeight: 600 }}>婚纱写真馆</div>
        <Menu mode="inline" selectedKeys={selectedKeys} onClick={e => navigate(e.key)} items={items} />
      </Sider>
      <Layout>
        <Header style={{ background: '#fff', padding: '0 16px', fontSize: 16 }}>管理后台</Header>
        <Content style={{ margin: 16, padding: 16, background: '#fff', minHeight: 360 }}>
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  )
}

export default AppLayout
