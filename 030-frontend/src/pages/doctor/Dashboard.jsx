import { useEffect, useState } from 'react'
import { Card, Row, Col, Statistic, List } from 'antd'
import { MessageOutlined, CheckCircleOutlined, ClockCircleOutlined } from '@ant-design/icons'
import { getDoctorConsultations } from '../../api/consultation'

const DoctorDashboard = () => {
  const [consultations, setConsultations] = useState([])

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const res = await getDoctorConsultations()
      setConsultations(res.data || [])
    } catch (error) {
      console.error(error)
    }
  }

  const pendingCount = consultations.filter(c => c.status === 'PENDING').length
  const answeredCount = consultations.filter(c => c.status === 'ANSWERED').length

  return (
    <div>
      <h2 style={{ marginBottom: 24 }}>工作台</h2>
      
      <Row gutter={16} style={{ marginBottom: 24 }}>
        <Col span={8}>
          <Card>
            <Statistic
              title="总咨询量"
              value={consultations.length}
              prefix={<MessageOutlined />}
              suffix="次"
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic
              title="待回复"
              value={pendingCount}
              prefix={<ClockCircleOutlined />}
              valueStyle={{ color: '#ff4d4f' }}
              suffix="个"
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic
              title="已回复"
              value={answeredCount}
              prefix={<CheckCircleOutlined />}
              valueStyle={{ color: '#52c41a' }}
              suffix="个"
            />
          </Card>
        </Col>
      </Row>

      <Card title="待处理咨询">
        <List
          dataSource={consultations.filter(c => c.status === 'PENDING').slice(0, 5)}
          renderItem={item => (
            <List.Item>
              <List.Item.Meta
                title={item.title}
                description={item.question.substring(0, 100) + '...'}
              />
            </List.Item>
          )}
        />
      </Card>
    </div>
  )
}

export default DoctorDashboard
