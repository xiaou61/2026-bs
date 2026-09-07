import { useEffect, useState } from 'react'
import { Card, Form, Input, Button, message, InputNumber, Select } from 'antd'
import { getUserInfo, updateUserInfo } from '../../api/user'

const Profile = () => {
  const [form] = Form.useForm()
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const res = await getUserInfo()
      if (res.data) {
        form.setFieldsValue(res.data)
      }
    } catch (error) {
      console.error(error)
    }
  }

  const handleSubmit = async () => {
    setLoading(true)
    try {
      const values = await form.validateFields()
      await updateUserInfo(values)
      message.success('保存成功')
      loadData()
    } catch (error) {
      message.error('保存失败')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div>
      <h2 style={{ marginBottom: 24 }}>个人信息</h2>
      
      <Card>
        <Form form={form} layout="vertical">
          <Form.Item name="realName" label="真实姓名">
            <Input />
          </Form.Item>
          <Form.Item name="gender" label="性别">
            <Select>
              <Select.Option value="男">男</Select.Option>
              <Select.Option value="女">女</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="age" label="年龄">
            <InputNumber style={{ width: '100%' }} min={0} max={150} />
          </Form.Item>
          <Form.Item name="idCard" label="身份证号">
            <Input />
          </Form.Item>
          <Form.Item>
            <Button type="primary" onClick={handleSubmit} loading={loading}>
              保存
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  )
}

export default Profile
