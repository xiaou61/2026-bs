#!/bin/bash
set -e

DEPLOY_DIR="/opt/2026-bs"
NGINX_CONF="/etc/nginx/conf.d/2026-bs.conf"
REPO_URL="https://github.com/xiaou61/2026-bs.git"

# Clone or update repo
if [ -d "$DEPLOY_DIR/.git" ]; then
  cd "$DEPLOY_DIR"
  git pull
else
  rm -rf "$DEPLOY_DIR"
  git clone "$REPO_URL" "$DEPLOY_DIR"
  cd "$DEPLOY_DIR"
fi

# Install and build
npm install
npm run docs:build

# Configure Nginx
cat > "$NGINX_CONF" <<NGINXEOF
server {
    listen 80;
    server_name 36.140.150.167;

    root /opt/2026-bs/docs-site/.vitepress/dist;
    index index.html;

    location / {
        try_files \$uri \$uri/ /index.html;
    }

    error_page 404 /404.html;
}
NGINXEOF

# Test and reload Nginx
nginx -t
systemctl reload nginx

echo "Deployment complete: http://36.140.150.167"
