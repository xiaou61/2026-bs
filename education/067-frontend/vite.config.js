import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    host: '0.0.0.0',
    port: 3067,
    proxy: {
      '/api': {
        target: 'http://localhost:8067',
        changeOrigin: true
      }
    }
  }
})
