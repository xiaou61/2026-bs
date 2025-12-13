import { useEffect, useState } from 'react'
import { Card, Form, Input, Button, message } from 'antd'
import { getPatientInfo, updatePatientInfo } from '../../api/health'

const HealthRecord = () => {
  const [form] = Form.useForm()
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const res = await getPatientInfo()
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
      await updatePatientInfo(values)
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
      <h2 style={{ marginBottom: 24 }}>健康档案</h2>
      
      <Card>
        <Form form={form} layout="vertical">
          <Form.Item name="address" label="地址">
            <Input />
          </Form.Item>
          <Form.Item name="medicalHistory" label="既往病史">
            <Input.TextArea rows={3} />
          </Form.Item>
          <Form.Item name="familyHistory" label="家族病史">
            <Input.TextArea rows={3} />
          </Form.Item>
          <Form.Item name="allergyHistory" label="过敏史">
            <Input.TextArea rows={3} />
          </Form.Item>
          <Form.Item name="surgeryHistory" label="手术史">
            <Input.TextArea rows={3} />
          </Form.Item>
          <Form.Item name="lifestyle" label="生活习惯">
            <Input.TextArea rows={3} />
          </Form.Item>
          <Form.Item name="smokingStatus" label="吸烟状况">
            <Input />
          </Form.Item>
          <Form.Item name="drinkingStatus" label="饮酒状况">
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

export default HealthRecord
