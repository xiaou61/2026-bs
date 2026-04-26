import { useEffect, useState } from 'react'
import {
  Alert,
  Button,
  Card,
  Col,
  Descriptions,
  Empty,
  Row,
  Select,
  Space,
  Statistic,
  Table,
  Tag,
  Typography,
  message
} from 'antd'
import { AlertOutlined, ReloadOutlined } from '@ant-design/icons'
import {
  generateAssessment,
  getAlerts,
  getAssessmentHistory,
  getLatestAssessment,
  getUnreadAlertCount,
  markAlertAsRead
} from '../../api/assessment'

const { Paragraph } = Typography

const levelColorMap = {
  NORMAL: 'green',
  LOW: 'orange',
  ELEVATED: 'orange',
  HIGH: 'red'
}

const alertLevelColorMap = {
  LOW: 'blue',
  MEDIUM: 'orange',
  HIGH: 'red'
}

const Assessment = () => {
  const [loading, setLoading] = useState(false)
  const [generating, setGenerating] = useState(false)
  const [latestAssessment, setLatestAssessment] = useState(null)
  const [history, setHistory] = useState([])
  const [alerts, setAlerts] = useState([])
  const [unreadCount, setUnreadCount] = useState(0)
  const [alertFilter, setAlertFilter] = useState('ALL')

  useEffect(() => {
    loadData()
  }, [alertFilter])

  const loadData = async () => {
    setLoading(true)
    try {
      const alertParams = alertFilter === 'ALL' ? undefined : { isRead: alertFilter === 'UNREAD' ? 0 : 1 }
      const [latestRes, historyRes, alertsRes, unreadRes] = await Promise.allSettled([
        getLatestAssessment(),
        getAssessmentHistory(),
        getAlerts(alertParams),
        getUnreadAlertCount()
      ])

      if (latestRes.status === 'fulfilled') {
        setLatestAssessment(latestRes.value.data)
      } else {
        setLatestAssessment(null)
      }

      if (historyRes.status === 'fulfilled') {
        setHistory(historyRes.value.data || [])
      }

      if (alertsRes.status === 'fulfilled') {
        setAlerts(alertsRes.value.data || [])
      }

      if (unreadRes.status === 'fulfilled') {
        setUnreadCount(unreadRes.value.data || 0)
      }
    } catch (error) {
      message.error('加载评估预警信息失败')
    } finally {
      setLoading(false)
    }
  }

  const handleGenerate = async () => {
    setGenerating(true)
    try {
      await generateAssessment()
      message.success('健康评估已更新')
      loadData()
    } catch (error) {
      message.error(error.message || '生成评估失败')
    } finally {
      setGenerating(false)
    }
  }

  const handleMarkRead = async (id) => {
    try {
      await markAlertAsRead(id)
      message.success('已标记为已读')
      loadData()
    } catch (error) {
      message.error('操作失败')
    }
  }

  const historyColumns = [
    {
      title: '评估时间',
      dataIndex: 'assessmentDate',
      key: 'assessmentDate',
      render: (value) => value ? value.replace('T', ' ').slice(0, 16) : '-'
    },
    {
      title: '健康评分',
      dataIndex: 'healthScore',
      key: 'healthScore'
    },
    {
      title: '血压等级',
      dataIndex: 'bloodPressureLevel',
      key: 'bloodPressureLevel',
      render: (value) => value ? <Tag color={levelColorMap[value] || 'default'}>{value}</Tag> : '-'
    },
    {
      title: '血糖等级',
      dataIndex: 'bloodSugarLevel',
      key: 'bloodSugarLevel',
      render: (value) => value ? <Tag color={levelColorMap[value] || 'default'}>{value}</Tag> : '-'
    }
  ]

  const alertColumns = [
    {
      title: '预警级别',
      dataIndex: 'level',
      key: 'level',
      width: 120,
      render: (value) => <Tag color={alertLevelColorMap[value] || 'default'}>{value}</Tag>
    },
    {
      title: '标题',
      dataIndex: 'title',
      key: 'title',
      width: 180
    },
    {
      title: '内容',
      dataIndex: 'content',
      key: 'content'
    },
    {
      title: '状态',
      dataIndex: 'isRead',
      key: 'isRead',
      width: 100,
      render: (value) => (
        <Tag color={value === 1 ? 'default' : 'orange'}>
          {value === 1 ? '已读' : '未读'}
        </Tag>
      )
    },
    {
      title: '时间',
      dataIndex: 'createTime',
      key: 'createTime',
      width: 180,
      render: (value) => value ? value.replace('T', ' ').slice(0, 16) : '-'
    },
    {
      title: '操作',
      key: 'action',
      width: 120,
      render: (_, record) => (
        record.isRead === 1 ? '-' : (
          <Button type="link" onClick={() => handleMarkRead(record.id)}>
            标记已读
          </Button>
        )
      )
    }
  ]

  return (
    <Space direction="vertical" size={16} style={{ width: '100%' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h2 style={{ margin: 0 }}>评估预警</h2>
        <Space>
          <Button icon={<ReloadOutlined />} onClick={loadData} loading={loading}>
            刷新
          </Button>
          <Button type="primary" icon={<AlertOutlined />} onClick={handleGenerate} loading={generating}>
            重新评估
          </Button>
        </Space>
      </div>

      <Alert
        type="info"
        showIcon
        message="系统会在您新增健康数据后自动更新评估结果；也可以手动触发一次综合评估。"
      />

      <Row gutter={16}>
        <Col xs={24} md={8}>
          <Card loading={loading}>
            <Statistic title="最新健康评分" value={latestAssessment?.healthScore ?? '--'} suffix="分" />
          </Card>
        </Col>
        <Col xs={24} md={8}>
          <Card loading={loading}>
            <Statistic title="未读预警" value={unreadCount} suffix="条" />
          </Card>
        </Col>
        <Col xs={24} md={8}>
          <Card loading={loading}>
            <Statistic title="累计评估记录" value={history.length} suffix="次" />
          </Card>
        </Col>
      </Row>

      {latestAssessment ? (
        <Card title="最新评估结果" loading={loading}>
          <Descriptions bordered column={2}>
            <Descriptions.Item label="健康评分">{latestAssessment.healthScore ?? '-'}</Descriptions.Item>
            <Descriptions.Item label="评估时间">
              {latestAssessment.assessmentDate ? latestAssessment.assessmentDate.replace('T', ' ').slice(0, 16) : '-'}
            </Descriptions.Item>
            <Descriptions.Item label="BMI 状态">{latestAssessment.bmiLevel || '-'}</Descriptions.Item>
            <Descriptions.Item label="血压等级">
              {latestAssessment.bloodPressureLevel ? (
                <Tag color={levelColorMap[latestAssessment.bloodPressureLevel] || 'default'}>
                  {latestAssessment.bloodPressureLevel}
                </Tag>
              ) : '-'}
            </Descriptions.Item>
            <Descriptions.Item label="血糖等级">
              {latestAssessment.bloodSugarLevel ? (
                <Tag color={levelColorMap[latestAssessment.bloodSugarLevel] || 'default'}>
                  {latestAssessment.bloodSugarLevel}
                </Tag>
              ) : '-'}
            </Descriptions.Item>
            <Descriptions.Item label="风险因素" span={2}>
              <Paragraph style={{ marginBottom: 0, whiteSpace: 'pre-line' }}>
                {latestAssessment.riskFactors || '当前没有明显风险因素。'}
              </Paragraph>
            </Descriptions.Item>
            <Descriptions.Item label="健康建议" span={2}>
              <Paragraph style={{ marginBottom: 0, whiteSpace: 'pre-line' }}>
                {latestAssessment.suggestions || '暂无建议。'}
              </Paragraph>
            </Descriptions.Item>
          </Descriptions>
        </Card>
      ) : (
        <Card>
          <Empty description="暂无健康评估记录，录入健康数据后会自动生成，也可点击“重新评估”立即生成。" />
        </Card>
      )}

      <Card
        title="预警消息"
        extra={
          <Select
            value={alertFilter}
            onChange={setAlertFilter}
            style={{ width: 140 }}
            options={[
              { label: '全部消息', value: 'ALL' },
              { label: '仅看未读', value: 'UNREAD' },
              { label: '仅看已读', value: 'READ' }
            ]}
          />
        }
      >
        <Table
          columns={alertColumns}
          dataSource={alerts}
          rowKey="id"
          loading={loading}
          pagination={{ pageSize: 5 }}
          locale={{ emptyText: '暂无预警消息' }}
          scroll={{ x: 920 }}
        />
      </Card>

      <Card title="历史评估">
        <Table
          columns={historyColumns}
          dataSource={history}
          rowKey="id"
          loading={loading}
          pagination={{ pageSize: 5 }}
          locale={{ emptyText: '暂无历史评估记录' }}
        />
      </Card>
    </Space>
  )
}

export default Assessment
