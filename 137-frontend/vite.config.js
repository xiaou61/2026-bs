import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3137, proxy: { '/api': { target: 'http://localhost:8137', changeOrigin: true } } }
})


