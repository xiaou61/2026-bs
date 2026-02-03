<script>
export default {
  onLaunch() {
    console.log('App Launch')
  },
  onShow() {
    console.log('App Show')
  },
  onHide() {
    console.log('App Hide')
  }
}
</script>

<style>
page {
  background-color: #f5f5f5;
}
.container {
  padding: 20rpx;
}
.card {
  background: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.05);
}
.btn-primary {
  background: #409EFF;
  color: #fff;
  border-radius: 8rpx;
  text-align: center;
  padding: 20rpx 0;
}
.btn-success {
  background: #67C23A;
  color: #fff;
  border-radius: 8rpx;
  text-align: center;
  padding: 20rpx 0;
}
.text-primary { color: #409EFF; }
.text-success { color: #67C23A; }
.text-warning { color: #E6A23C; }
.text-danger { color: #F56C6C; }
.text-gray { color: #999; }
.text-sm { font-size: 24rpx; }
.text-lg { font-size: 32rpx; }
.mt-10 { margin-top: 10rpx; }
.mt-20 { margin-top: 20rpx; }
.mb-10 { margin-bottom: 10rpx; }
.mb-20 { margin-bottom: 20rpx; }
</style>
