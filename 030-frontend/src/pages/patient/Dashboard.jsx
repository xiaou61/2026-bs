import { useEffect, useState } from 'react'
import { Card, Row, Col, Statistic, Empty } from 'antd'
import { HeartOutlined, FileTextOutlined, MessageOutlined } from '@ant-design/icons'
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts'
import { getHealthDataList } from '../../api/health'
import { getPatientConsultations } from '../../api/consultation'

const PatientDashboard = () => {
  const [healthData, setHealthData] = useState([])
  const [consultations, setConsultations] = useState([])

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const [healthRes, consultRes] = await Promise.all([
        getHealthDataList(),
        getPatientConsultations()
      ])
      setHealthData(healthRes.data || [])
      setConsultations(consultRes.data || [])
    } catch (error) {
      console.error(error)
    }
  }

  const chartData = healthData.slice(0, 10).reverse().map(item => ({
    date: new Date(item.measureTime).toLocaleDateString(),
    value: parseFloat(item.value)
  }))

  return (
    <div>
      <h2 style={{ marginBottom: 24 }}>健康概览</h2>
      
      <Row gutter={16} style={{ marginBottom: 24 }}>
        <Col span={8}>
          <Card>
            <Statistic
              title="健康数据记录"
              value={healthData.length}
              prefix={<HeartOutlined />}
              suffix="条"
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic
              title="咨询记录"
              value={consultations.length}
              prefix={<MessageOutlined />}
              suffix="次"
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic
              title="待回复咨询"
              value={consultations.filter(c => c.status === 'PENDING').length}
              prefix={<FileTextOutlined />}
              suffix="个"
            />
          </Card>
        </Col>
      </Row>

      <Card title="健康数据趋势">
        {chartData.length > 0 ? (
          <ResponsiveContainer width="100%" height={300}>
            <LineChart data={chartData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="date" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="value" stroke="#8884d8" name="数值" />
            </LineChart>
          </ResponsiveContainer>
        ) : (
          <Empty description="暂无数据" />
        )}
      </Card>
    </div>
  )
}

export default PatientDashboard
