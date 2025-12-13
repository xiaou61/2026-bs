import { useEffect, useState } from 'react'
import { Card, Form, Input, Button, message, InputNumber } from 'antd'
import { getDoctorInfo, updateDoctorInfo } from '../../api/doctor'

const DoctorProfile = () => {
  const [form] = Form.useForm()
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const res = await getDoctorInfo()
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
      await updateDoctorInfo(values)
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
          <Form.Item name="title" label="职称">
            <Input />
          </Form.Item>
          <Form.Item name="hospital" label="医院">
            <Input />
          </Form.Item>
          <Form.Item name="department" label="科室">
            <Input />
          </Form.Item>
          <Form.Item name="specialty" label="专长">
            <Input />
          </Form.Item>
          <Form.Item name="introduction" label="简介">
            <Input.TextArea rows={4} />
          </Form.Item>
          <Form.Item name="licenseNumber" label="执业证号">
            <Input />
          </Form.Item>
          <Form.Item name="yearsOfExperience" label="从业年限">
            <InputNumber style={{ width: '100%' }} min={0} />
          </Form.Item>
          <Form.Item name="consultationFee" label="咨询费用">
            <InputNumber style={{ width: '100%' }} min={0} />
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

export default DoctorProfile
