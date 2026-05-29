#!/usr/bin/env python3
"""Coverage audit for VitePress project pages."""
import os
import re
import sys

PROJECTS_DIR = os.path.join(os.path.dirname(os.path.dirname(__file__)), 'docs-site', 'projects')
TOTAL = 199  # projects 001-199

def check_accounts(content):
    """Check if page has real accounts (backtick-wrapped username/password in table or inline)."""
    # Check for backtick-wrapped username/password in table rows
    bt = '`'
    for line in content.split('\n'):
        if '|' in line and bt in line:
            cells = [c.strip() for c in line.split('|')]
            bt_cells = [c for c in cells if re.match(bt + r'+\s*\w+\s*' + bt + r'+', c)]
            if len(bt_cells) >= 2:
                return True
    # Check for inline account mentions with backtick-wrapped credentials
    if re.search(r'账号[：:]\s*`\w+`', content) and re.search(r'密码[：:]\s*`\w+`', content):
        return True
    # Check for account table headers
    if re.search(r'\|\s*账号\s*\|\s*密码\s*\|', content): return True
    if re.search(r'\|\s*用户名\s*\|\s*密码\s*\|', content): return True
    return False

def check_roles(content):
    """Check if page has role permissions section."""
    return bool(re.search(r'## 角色权限\s*\n', content))

def check_description(content):
    """Check if page has meaningful description in 项目简介/概览 section."""
    # Try 项目简介 first (usually has actual text), then 项目概览, then others
    for section_name in ['项目简介', '项目概览', '项目说明', '简介', '概览', '说明']:
        desc_match = re.search(r'## ' + section_name + r'\s*\n([\s\S]*?)(?=\n## )', content)
        if desc_match:
            text = desc_match.group(1).strip()
            # Remove just table headers and dividers
            text = re.sub(r'^\|.*\|$', '', text, flags=re.MULTILINE).strip()
            if len(text) > 10:
                return True
    # Also check if 项目概览 section has meaningful table data (项目全称, 项目类型 etc.)
    overview_match = re.search(r'## 项目概览\s*\n([\s\S]*?)(?=\n## )', content)
    if overview_match:
        overview_text = overview_match.group(1)
        # A table with 项目全称 and 项目类型 is considered valid
        if '项目全称' in overview_text and '项目类型' in overview_text:
            return True
    return False

def check_dev_notes(content):
    """Check if page has dev notes section (h2 or h3)."""
    return bool(re.search(r'#{2,3}\s*(?:开发笔记|技术要点|开发说明|开发注意事项|注意事项)\s*\n', content))

def main():
    metrics = {
        'accounts': {'found': 0, 'missing': []},
        'roles': {'found': 0, 'missing': []},
        'descriptions': {'found': 0, 'missing': []},
        'dev_notes': {'found': 0, 'missing': []},
    }

    for i in range(1, TOTAL + 1):
        num = f'{i:03d}'
        filepath = os.path.join(PROJECTS_DIR, f'{num}.md')
        if not os.path.exists(filepath):
            for key in metrics:
                metrics[key]['missing'].append(num)
            continue

        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()

        if check_accounts(content):
            metrics['accounts']['found'] += 1
        else:
            metrics['accounts']['missing'].append(num)

        if check_roles(content):
            metrics['roles']['found'] += 1
        else:
            metrics['roles']['missing'].append(num)

        if check_description(content):
            metrics['descriptions']['found'] += 1
        else:
            metrics['descriptions']['missing'].append(num)

        if check_dev_notes(content):
            metrics['dev_notes']['found'] += 1
        else:
            metrics['dev_notes']['missing'].append(num)

    print(f'=== VitePress Project Page Coverage ===')
    print(f'Total projects: {TOTAL}')
    print()
    for key, label in [('accounts', 'Real accounts'), ('roles', 'Role permissions'),
                        ('descriptions', 'Descriptions'), ('dev_notes', 'Dev notes')]:
        found = metrics[key]['found']
        pct = found / TOTAL * 100
        missing = metrics[key]['missing']
        print(f'{label}: {found}/{TOTAL} ({pct:.1f}%)')
        if missing and len(missing) <= 20:
            print(f'  Missing: {missing}')

if __name__ == '__main__':
    main()
