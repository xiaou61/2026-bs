import re
from pathlib import Path

CTRL_DIR = Path('170-backend/src/main/java/com/eldercare/controller')
SKIP = {'BaseController.java', 'AuthController.java', 'StatisticsController.java'}

ROLE_PAT = re.compile(r'assertAnyRole\(role,\s*([^)]+)\)')
ENABLE_PAT = re.compile(r'@PutMapping\("/enable/\{id\}"\)')

for f in sorted(CTRL_DIR.glob('*.java')):
    if f.name in SKIP:
        continue
    text = f.read_text(encoding='utf-8')

    cls_match = re.search(r'public class (\w+)', text)
    cls = cls_match.group(1)

    path_match = re.search(r'@RequestMapping\("([^"]+)"\)', text)
    mapping = path_match.group(1)

    entity_match = re.search(r'@RequestBody (\w+) entity', text)
    entity = entity_match.group(1)

    svc_match = re.search(r'private final (\w+Service) service;', text)
    svc = svc_match.group(1)

    page_method = re.search(r'@GetMapping\("/page"\)\s*public [^{]+\{(.*?)\n    \}', text, re.S)
    page_auth = 'assertAuthenticated'
    if page_method and 'authService.assertAdmin(role)' in page_method.group(1):
        page_auth = 'assertAdmin'

    add_method = re.search(r'@PostMapping\n.*?service\.save\(entity\);', text, re.S)
    roles_str = 'ADMIN'
    if add_method:
        role_m = ROLE_PAT.search(add_method.group(0))
        if role_m:
            roles_str = role_m.group(1)
    roles = [r.strip().strip('"') for r in roles_str.split(',')]

    has_enable = bool(ENABLE_PAT.search(text))
    status_type = 'String' if 'String status' in text else 'Integer'

    roles_literal = ', '.join(f'"{r}"' for r in roles)
    page_check = 'checkAdmin(role)' if page_auth == 'assertAdmin' else 'checkAuthenticated(role)'
    write_check = 'checkAnyRole(role, WRITE_ROLES)'

    body = f'''package com.eldercare.controller;

import com.github.pagehelper.PageInfo;
import com.eldercare.common.Result;
import com.eldercare.entity.{entity};
import com.eldercare.service.AuthService;
import com.eldercare.service.{svc};
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{mapping}")
public class {cls} extends BaseController {{
    private final {svc} service;
    private static final String[] WRITE_ROLES = {{{roles_literal}}};

    public {cls}(AuthService authService, {svc} service) {{
        super(authService);
        this.service = service;
    }}

    @GetMapping("/page")
    public Result<PageInfo<{entity}>> page(@RequestAttribute("role") String role, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) {status_type} status) {{
        {page_check};
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }}

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody {entity} entity) {{
        {write_check};
        service.save(entity);
        return Result.success();
    }}

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody {entity} entity) {{
        {write_check};
        service.save(entity);
        return Result.success();
    }}

    @DeleteMapping("/{{id}}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {{
        checkAdmin(role);
        service.delete(id);
        return Result.success();
    }}
'''
    if has_enable:
        body += '''
    @PutMapping("/enable/{id}")
    public Result<Void> enable(@RequestAttribute("role") String role, @PathVariable Long id) {
        checkAdmin(role);
        service.updateStatus(id, 1);
        return Result.success();
    }

    @PutMapping("/disable/{id}")
    public Result<Void> disable(@RequestAttribute("role") String role, @PathVariable Long id) {
        checkAdmin(role);
        service.updateStatus(id, 0);
        return Result.success();
    }
}
'''
    else:
        body += f'''
    @PutMapping("/process/{{id}}")
    public Result<Void> process(@RequestAttribute("role") String role, @PathVariable Long id) {{
        {write_check};
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }}

    @PutMapping("/finish/{{id}}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {{
        {write_check};
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }}
}}
'''

    f.write_text(body, encoding='utf-8')
    print(f'Rewritten {f.name}: page={page_auth}, roles={roles}, status={status_type}, enable={has_enable}')
