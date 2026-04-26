import { useEffect, useState } from 'react'
import { Card, Col, Empty, Row, Space, Spin, Statistic, Table, Tag } from 'antd'
import { AlertOutlined, FileTextOutlined, MedicineBoxOutlined, TeamOutlined, UserOutlined } from '@ant-design/icons'
import { Bar, BarChart, CartesianGrid, Legend, ResponsiveContainer, Tooltip, XAxis, YAxis, Line, LineChart } from 'recharts'
import { getAdminStatistics } from '../../api/statistics'

const AdminDashboard = () => {
  const [loading, setLoading] = useState(false)
  const [stats, setStats] = useState(null)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    setLoading(true)
    try {
      const res = await getAdminStatistics()
      setStats(res.data || null)
    } catch (error) {
      console.error(error)
    } finally {
      setLoading(false)
    }
  }

  const cards = [
    {
      title: '用户总数',
      value: stats?.totalUsers ?? 0,
      prefix: <UserOutlined />,
      suffix: '人'
    },
    {
      title: '医生数量',
      value: stats?.totalDoctors ?? 0,
      prefix: <MedicineBoxOutlined />,
      suffix: '人'
    },
    {
      title: '患者数量',
      value: stats?.totalPatients ?? 0,
      prefix: <TeamOutlined />,
      suffix: '人'
    },
    {
      title: '待审核医生',
      value: stats?.pendingDoctors ?? 0,
      prefix: <MedicineBoxOutlined />,
      suffix: '位'
    },
    {
      title: '咨询总量',
      value: stats?.totalConsultations ?? 0,
      prefix: <FileTextOutlined />,
      suffix: '次'
    },
    {
      title: '未读预警',
      value: stats?.unreadAlerts ?? 0,
      prefix: <AlertOutlined />,
      suffix: '条'
    }
  ]

  const rankingColumns = [
    {
      title: '医生',
      dataIndex: 'doctorName',
      key: 'doctorName'
    },
    {
      title: '科室',
      dataIndex: 'department',
      key: 'department',
      render: (value) => value || '-'
    },
    {
      title: '医院',
      dataIndex: 'hospital',
      key: 'hospital',
      render: (value) => value || '-'
    },
    {
      title: '服务量',
      dataIndex: 'totalConsultations',
      key: 'totalConsultations'
    },
    {
      title: '已回复',
      dataIndex: 'answeredConsultations',
      key: 'answeredConsultations'
    },
    {
      title: '平均评分',
      dataIndex: 'averageRating',
      key: 'averageRating',
      render: (value) => <Tag color="blue">{value}</Tag>
    }
  ]

  return (
    <div>
      <h2 style={{ marginBottom: 24 }}>系统概览</h2>

      <Spin spinning={loading}>
        <Row gutter={[16, 16]} style={{ marginBottom: 24 }}>
          {cards.map(item => (
            <Col xs={24} md={12} xl={8} key={item.title}>
              <Card>
                <Statistic
                  title={item.title}
                  value={item.value}
                  prefix={item.prefix}
                  suffix={item.suffix}
                />
              </Card>
            </Col>
          ))}
        </Row>

        <Row gutter={[16, 16]}>
          <Col xs={24} xl={14}>
            <Card title="近 7 天趋势">
              {stats?.consultationTrend?.length ? (
                <ResponsiveContainer width="100%" height={320}>
                  <LineChart data={stats.consultationTrend.map((item, index) => ({
                    date: item.date.slice(5),
                    consultationCount: item.count,
                    healthDataCount: stats.healthDataTrend?.[index]?.count ?? 0,
                    alertCount: stats.alertTrend?.[index]?.count ?? 0
                  }))}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Line type="monotone" dataKey="consultationCount" stroke="#1677ff" name="咨询量" />
                    <Line type="monotone" dataKey="healthDataCount" stroke="#52c41a" name="健康数据录入" />
                    <Line type="monotone" dataKey="alertCount" stroke="#fa8c16" name="预警数" />
                  </LineChart>
                </ResponsiveContainer>
              ) : (
                <Empty description="暂无趋势数据" />
              )}
            </Card>
          </Col>
          <Col xs={24} xl={10}>
            <Card title="平台关键指标">
              {stats ? (
                <Space direction="vertical" size={16} style={{ width: '100%' }}>
                  <Statistic title="已发布健康知识" value={stats.publishedKnowledgeCount ?? 0} suffix="篇" />
                  <Statistic title="健康评估记录" value={stats.totalAssessments ?? 0} suffix="次" />
                  <Statistic title="平均咨询评分" value={stats.averageConsultationRating ?? 0} precision={2} suffix="分" />
                  <Statistic title="已审核医生" value={stats.verifiedDoctors ?? 0} suffix="位" />
                </Space>
              ) : (
                <Empty description="暂无统计数据" />
              )}
            </Card>
          </Col>
        </Row>

        <Row gutter={[16, 16]} style={{ marginTop: 8 }}>
          <Col xs={24} xl={10}>
            <Card title="用户角色分布">
              {stats ? (
                <ResponsiveContainer width="100%" height={300}>
                  <BarChart
                    data={[
                      { name: '管理员', value: stats.totalAdmins ?? 0 },
                      { name: '医生', value: stats.totalDoctors ?? 0 },
                      { name: '患者', value: stats.totalPatients ?? 0 }
                    ]}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="value" fill="#722ed1" name="人数" radius={[6, 6, 0, 0]} />
                  </BarChart>
                </ResponsiveContainer>
              ) : (
                <Empty description="暂无角色统计" />
              )}
            </Card>
          </Col>
          <Col xs={24} xl={14}>
            <Card title="医生服务量排行">
              <Table
                columns={rankingColumns}
                dataSource={stats?.doctorServiceRanking || []}
                rowKey="doctorId"
                pagination={false}
                locale={{ emptyText: '暂无医生服务数据' }}
                scroll={{ x: 760 }}
              />
            </Card>
          </Col>
        </Row>
      </Spin>
    </div>
  )
}

export default AdminDashboard
