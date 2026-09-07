<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="cart-header">
          <el-checkbox v-model="allChecked" @change="handleCheckAll">全选</el-checkbox>
          <span>共{{ cartList.length }}件商品</span>
        </div>
      </template>
      <div v-for="item in cartList" :key="item.id" class="cart-item">
        <el-checkbox v-model="item.checked" @change="handleItemCheck" />
        <el-image :src="item.productCover" style="width: 80px; height: 80px" fit="cover" />
        <div class="item-info">
          <div class="item-name">{{ item.productName }}</div>
          <div class="item-price">¥{{ item.price }}</div>
        </div>
        <el-input-number v-model="item.quantity" :min="1" :max="99" @change="handleQuantityChange(item)" />
        <div class="item-subtotal">¥{{ (item.price * item.quantity).toFixed(2) }}</div>
        <el-button type="danger" link @click="handleDelete(item.id)">删除</el-button>
      </div>
      <el-empty v-if="!cartList.length" description="购物车是空的" />
      <div class="cart-footer" v-if="cartList.length">
        <div class="total">
          <span>已选{{ checkedCount }}件</span>
          <span>合计: <strong>¥{{ totalPrice.toFixed(2) }}</strong></span>
        </div>
        <el-button type="danger" size="large" :disabled="!checkedCount" @click="handleCheckout">去结算</el-button>
      </div>
    </el-card>
    <el-dialog v-model="showAddress" title="选择收货地址">
      <el-radio-group v-model="selectedAddress" style="display: block">
        <div v-for="addr in addressList" :key="addr.id" class="address-item">
          <el-radio :label="addr.id">{{ addr.name }} {{ addr.phone }} {{ addr.province }}{{ addr.city }}{{ addr.district }}{{ addr.detail }}</el-radio>
        </div>
      </el-radio-group>
      <template #footer>
        <el-button @click="showAddress = false">取消</el-button>
        <el-button type="primary" @click="confirmOrder">确认下单</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getCartList, updateCartQuantity, deleteCart, getAddressList, createOrder } from '../../api'

const router = useRouter()
const cartList = ref([])
const addressList = ref([])
const showAddress = ref(false)
const selectedAddress = ref(null)

const allChecked = computed({
  get: () => cartList.value.length > 0 && cartList.value.every(item => item.checked),
  set: () => {}
})

const checkedCount = computed(() => cartList.value.filter(item => item.checked).length)
const totalPrice = computed(() => cartList.value.filter(item => item.checked).reduce((sum, item) => sum + item.price * item.quantity, 0))

const loadData = async () => {
  const res = await getCartList()
  cartList.value = res.data.map(item => ({ ...item, checked: false }))
}

const loadAddress = async () => {
  const res = await getAddressList()
  addressList.value = res.data
  if (res.data.length) selectedAddress.value = res.data[0].id
}

const handleCheckAll = (checked) => {
  cartList.value.forEach(item => item.checked = checked)
}

const handleItemCheck = () => {}

const handleQuantityChange = async (item) => {
  await updateCartQuantity(item.id, { quantity: item.quantity })
}

const handleDelete = async (id) => {
  await deleteCart(id)
  ElMessage.success('已删除')
  loadData()
}

const handleCheckout = () => {
  if (!checkedCount.value) {
    ElMessage.warning('请选择商品')
    return
  }
  loadAddress()
  showAddress.value = true
}

const confirmOrder = async () => {
  if (!selectedAddress.value) {
    ElMessage.warning('请选择收货地址')
    return
  }
  const items = cartList.value.filter(item => item.checked).map(item => ({ cartId: item.id, productId: item.productId, quantity: item.quantity }))
  await createOrder({ addressId: selectedAddress.value, items })
  ElMessage.success('下单成功')
  showAddress.value = false
  router.push('/order')
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 20px; }
.cart-header { display: flex; align-items: center; gap: 20px; }
.cart-item { display: flex; align-items: center; gap: 20px; padding: 15px 0; border-bottom: 1px solid #eee; }
.item-info { flex: 1; }
.item-name { font-size: 14px; margin-bottom: 5px; }
.item-price { color: #f56c6c; }
.item-subtotal { width: 100px; text-align: right; font-weight: bold; color: #f56c6c; }
.cart-footer { display: flex; justify-content: flex-end; align-items: center; gap: 30px; margin-top: 20px; padding-top: 20px; border-top: 1px solid #eee; }
.total { display: flex; gap: 20px; }
.total strong { font-size: 20px; color: #f56c6c; }
.address-item { padding: 10px 0; border-bottom: 1px solid #eee; }
</style>
