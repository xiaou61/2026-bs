import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3069,
    host: '0.0.0.0',
    proxy: {
      '/api': {
        target: 'http://localhost:8069',
        changeOrigin: true
      }
    }
  }
})
