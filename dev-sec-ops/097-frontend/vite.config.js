import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3097,
    proxy: {
      '/api': {
        target: 'http://localhost:8097',
        changeOrigin: true
      }
    }
  }
})
