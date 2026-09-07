import { Card, Col, Row, Statistic, DatePicker } from 'antd'
import { useEffect, useState } from 'react'
import dayjs from 'dayjs'
import { fetchDashboard } from '../api'

function Dashboard() {
  const [data, setData] = useState<any>({})
  const [date, setDate] = useState<string>()

  useEffect(() => {
    fetchDashboard(date).then(res => setData(res.data.data))
  }, [date])

  return (
    <div>
      <DatePicker value={date ? dayjs(date) : undefined} onChange={d => setDate(d ? d.format('YYYY-MM-DD') : undefined)} style={{ marginBottom: 16 }} />
      <Row gutter={16}>
        <Col span={6}><Card><Statistic title="客户数" value={data.customerCount} /></Card></Col>
        <Col span={6}><Card><Statistic title="订单数" value={data.orderCount} /></Card></Col>
        <Col span={6}><Card><Statistic title="订单金额" value={data.orderAmount} prefix="¥" precision={2} /></Card></Col>
        <Col span={6}><Card><Statistic title="当日预约" value={data.appointmentCount} /></Card></Col>
      </Row>
    </div>
  )
}

export default Dashboard
