import { Card, Row, Col, Statistic } from 'antd'
import { UserOutlined, TeamOutlined, MedicineBoxOutlined } from '@ant-design/icons'

const AdminDashboard = () => {
  return (
    <div>
      <h2 style={{ marginBottom: 24 }}>系统概览</h2>
      
      <Row gutter={16}>
        <Col span={8}>
          <Card>
            <Statistic
              title="用户总数"
              value={0}
              prefix={<UserOutlined />}
              suffix="人"
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic
              title="医生数量"
              value={0}
              prefix={<MedicineBoxOutlined />}
              suffix="人"
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic
              title="患者数量"
              value={0}
              prefix={<TeamOutlined />}
              suffix="人"
            />
          </Card>
        </Col>
      </Row>
    </div>
  )
}

export default AdminDashboard
