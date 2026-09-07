import { useEffect, useState } from 'react'
import {
  Alert,
  Button,
  Card,
  Col,
  Drawer,
  Empty,
  Input,
  Row,
  Select,
  Space,
  Spin,
  Tag,
  Typography,
  message
} from 'antd'
import { getKnowledgeDetail, getKnowledgeList } from '../api/knowledge'

const { Paragraph, Text, Title } = Typography

const Knowledge = () => {
  const [list, setList] = useState([])
  const [loading, setLoading] = useState(false)
  const [detailLoading, setDetailLoading] = useState(false)
  const [selectedCategory, setSelectedCategory] = useState('ALL')
  const [keyword, setKeyword] = useState('')
  const [drawerOpen, setDrawerOpen] = useState(false)
  const [selectedKnowledge, setSelectedKnowledge] = useState(null)

  useEffect(() => {
    loadList()
  }, [])

  const loadList = async () => {
    setLoading(true)
    try {
      const res = await getKnowledgeList()
      setList(res.data || [])
    } catch (error) {
      message.error('加载健康知识失败')
    } finally {
      setLoading(false)
    }
  }

  const categoryOptions = [
    { label: '全部分类', value: 'ALL' },
    ...Array.from(new Set(list.map(item => item.category).filter(Boolean))).map(item => ({
      label: item,
      value: item
    }))
  ]

  const filteredList = list.filter(item => {
    const matchCategory = selectedCategory === 'ALL' || item.category === selectedCategory
    const text = `${item.title || ''} ${item.summary || ''} ${item.content || ''}`.toLowerCase()
    const matchKeyword = !keyword || text.includes(keyword.toLowerCase())
    return matchCategory && matchKeyword
  })

  const openDetail = async (record) => {
    setDrawerOpen(true)
    setDetailLoading(true)
    try {
      const res = await getKnowledgeDetail(record.id)
      setSelectedKnowledge(res.data)
      setList(current =>
        current.map(item => (item.id === res.data.id ? { ...item, viewCount: res.data.viewCount } : item))
      )
    } catch (error) {
      message.error('加载知识详情失败')
    } finally {
      setDetailLoading(false)
    }
  }

  return (
    <div>
      <Space direction="vertical" size={16} style={{ width: '100%' }}>
        <div>
          <h2 style={{ marginBottom: 8 }}>健康知识</h2>
          <Alert
            type="info"
            showIcon
            message="当前内容由平台管理员维护，可用于患者宣教、日常健康提醒和医生随访指导。"
          />
        </div>

        <Card>
          <Space wrap style={{ width: '100%', justifyContent: 'space-between' }}>
            <Select
              value={selectedCategory}
              onChange={setSelectedCategory}
              options={categoryOptions}
              style={{ width: 200 }}
            />
            <Input
              value={keyword}
              onChange={(event) => setKeyword(event.target.value)}
              placeholder="搜索标题、摘要或正文关键词"
              allowClear
              style={{ width: 280 }}
            />
          </Space>
        </Card>

        <Spin spinning={loading}>
          {filteredList.length === 0 ? (
            <Card>
              <Empty description="暂无匹配的健康知识内容" />
            </Card>
          ) : (
            <Row gutter={[16, 16]}>
              {filteredList.map(item => (
                <Col xs={24} md={12} xl={8} key={item.id}>
                  <Card
                    title={item.title}
                    extra={<Tag color="blue">{item.category}</Tag>}
                    actions={[
                      <Button type="link" onClick={() => openDetail(item)} key={`detail-${item.id}`}>
                        查看详情
                      </Button>
                    ]}
                  >
                    <Paragraph ellipsis={{ rows: 3 }}>
                      {item.summary || item.content || '暂无摘要'}
                    </Paragraph>
                    <Space size={16}>
                      <Text type="secondary">浏览 {item.viewCount || 0}</Text>
                      <Text type="secondary">
                        更新于 {item.updateTime ? item.updateTime.replace('T', ' ').slice(0, 16) : '-'}
                      </Text>
                    </Space>
                  </Card>
                </Col>
              ))}
            </Row>
          )}
        </Spin>
      </Space>

      <Drawer
        title={selectedKnowledge?.title || '健康知识详情'}
        open={drawerOpen}
        onClose={() => {
          setDrawerOpen(false)
          setSelectedKnowledge(null)
        }}
        width={720}
      >
        <Spin spinning={detailLoading}>
          {selectedKnowledge ? (
            <Space direction="vertical" size={16} style={{ width: '100%' }}>
              <Space wrap>
                <Tag color="blue">{selectedKnowledge.category}</Tag>
                <Text type="secondary">浏览 {selectedKnowledge.viewCount || 0}</Text>
                <Text type="secondary">
                  发布时间 {selectedKnowledge.createTime ? selectedKnowledge.createTime.replace('T', ' ').slice(0, 16) : '-'}
                </Text>
              </Space>
              {selectedKnowledge.summary ? (
                <Card size="small" title="摘要">
                  <Paragraph style={{ marginBottom: 0, whiteSpace: 'pre-line' }}>
                    {selectedKnowledge.summary}
                  </Paragraph>
                </Card>
              ) : null}
              <Card size="small" title="正文内容">
                <Title level={5}>内容说明</Title>
                <Paragraph style={{ whiteSpace: 'pre-line', marginBottom: 0 }}>
                  {selectedKnowledge.content}
                </Paragraph>
              </Card>
              {selectedKnowledge.videoUrl ? (
                <Card size="small" title="扩展资料">
                  <a href={selectedKnowledge.videoUrl} target="_blank" rel="noreferrer">
                    查看关联视频或外部资料
                  </a>
                </Card>
              ) : null}
            </Space>
          ) : (
            <Empty description="请选择一篇健康知识查看详情" />
          )}
        </Spin>
      </Drawer>
    </div>
  )
}

export default Knowledge
