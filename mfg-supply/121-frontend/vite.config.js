import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3121, proxy: { '/api': { target: 'http://localhost:8121', changeOrigin: true } } }
})
