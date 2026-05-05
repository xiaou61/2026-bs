import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3115, proxy: { '/api': { target: 'http://localhost:8115', changeOrigin: true } } }
})
