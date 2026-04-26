import { useEffect, useState } from 'react'
import { Card, Col, Empty, List, Row, Space, Spin, Statistic, Tag } from 'antd'
import { MessageOutlined, CheckCircleOutlined, ClockCircleOutlined, StarOutlined } from '@ant-design/icons'
import { CartesianGrid, Legend, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts'
import { getDoctorStatistics } from '../../api/statistics'

const DoctorDashboard = () => {
  const [stats, setStats] = useState(null)
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    setLoading(true)
    try {
      const res = await getDoctorStatistics()
      setStats(res.data || null)
    } catch (error) {
      console.error(error)
    } finally {
      setLoading(false)
    }
  }

  const cards = [
    {
      title: '总咨询量',
      value: stats?.totalConsultations ?? 0,
      prefix: <MessageOutlined />,
      suffix: '次'
    },
    {
      title: '待回复',
      value: stats?.pendingConsultations ?? 0,
      prefix: <ClockCircleOutlined />,
      suffix: '个',
      valueStyle: { color: '#ff4d4f' }
    },
    {
      title: '已回复',
      value: stats?.answeredConsultations ?? 0,
      prefix: <CheckCircleOutlined />,
      suffix: '个',
      valueStyle: { color: '#52c41a' }
    },
    {
      title: '平均评分',
      value: Number(stats?.averageRating ?? 0),
      precision: 2,
      prefix: <StarOutlined />,
      suffix: '分'
    }
  ]

  return (
    <div>
      <h2 style={{ marginBottom: 24 }}>工作台</h2>

      <Spin spinning={loading}>
        <Row gutter={[16, 16]} style={{ marginBottom: 24 }}>
          {cards.map(item => (
            <Col xs={24} md={12} xl={6} key={item.title}>
              <Card>
                <Statistic
                  title={item.title}
                  value={item.value}
                  prefix={item.prefix}
                  suffix={item.suffix}
                  valueStyle={item.valueStyle}
                />
              </Card>
            </Col>
          ))}
        </Row>

        <Row gutter={[16, 16]}>
          <Col xs={24} xl={14}>
            <Card
              title="近 7 天服务趋势"
              extra={stats?.doctorDepartment ? <Tag color="blue">{stats.doctorDepartment}</Tag> : null}
            >
              {stats?.consultationTrend?.length ? (
                <ResponsiveContainer width="100%" height={300}>
                  <LineChart
                    data={stats.consultationTrend.map(item => ({
                      date: item.date.slice(5),
                      count: item.count
                    }))}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Legend />
                    <Line type="monotone" dataKey="count" stroke="#1677ff" name="咨询量" />
                  </LineChart>
                </ResponsiveContainer>
              ) : (
                <Empty description="暂无趋势数据" />
              )}
            </Card>
          </Col>
          <Col xs={24} xl={10}>
            <Card title="医生信息摘要">
              {stats ? (
                <Space direction="vertical" size={16} style={{ width: '100%' }}>
                  <Statistic title="执业机构" value={stats.doctorHospital || '-'} />
                  <Statistic title="职称" value={stats.doctorTitle || '-'} />
                  <Statistic title="最近待处理" value={(stats.recentPendingConsultations || []).length} suffix="条" />
                </Space>
              ) : (
                <Empty description="暂无信息" />
              )}
            </Card>
          </Col>
        </Row>

        <Row gutter={[16, 16]} style={{ marginTop: 8 }}>
          <Col xs={24} xl={12}>
            <Card title="待处理咨询">
              <List
                dataSource={stats?.recentPendingConsultations || []}
                locale={{ emptyText: '暂无待处理咨询' }}
                renderItem={item => (
                  <List.Item>
                    <List.Item.Meta
                      title={item.title}
                      description={`${(item.question || '').slice(0, 80)}${(item.question || '').length > 80 ? '...' : ''}`}
                    />
                  </List.Item>
                )}
              />
            </Card>
          </Col>
          <Col xs={24} xl={12}>
            <Card title="最近已完成咨询">
              <List
                dataSource={stats?.recentAnsweredConsultations || []}
                locale={{ emptyText: '暂无已完成咨询' }}
                renderItem={item => (
                  <List.Item>
                    <List.Item.Meta
                      title={item.title}
                      description={
                        <Space split={<span>|</span>}>
                          <span>{item.answeredTime ? item.answeredTime.replace('T', ' ').slice(0, 16) : '未记录时间'}</span>
                          <span>评分 {item.rating ?? '-'}</span>
                        </Space>
                      }
                    />
                  </List.Item>
                )}
              />
            </Card>
          </Col>
        </Row>
      </Spin>
    </div>
  )
}

export default DoctorDashboard
