import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3191, proxy: { '/api': { target: 'http://localhost:8191', changeOrigin: true } } }
})
