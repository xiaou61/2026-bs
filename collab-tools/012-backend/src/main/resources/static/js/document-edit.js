(function () {
    const documentId = Number(document.body.dataset.documentId || 0);
    const defaultNoteContent = `# 新建笔记

在这里开始记录你的灵感、会议纪要或任务清单。

## 今日待办
- [ ] 补充关键信息
- [ ] 完成内容整理
`;

    const state = {
        clientId: `client-${Date.now()}-${Math.random().toString(36).slice(2, 8)}`,
        currentUser: null,
        currentDoc: null,
        ws: null,
        autoSaveTimer: null,
        syncTimer: null,
        toast: null,
        applyingRemote: false,
        board: {
            strokes: [],
            color: "#1d3557",
            brushSize: 4,
            drawing: false,
            currentStroke: null,
            canvas: null,
            ctx: null
        },
        mindmap: {
            root: null,
            selectedId: "root"
        }
    };

    axios.defaults.baseURL = "/api";
    axios.interceptors.request.use((config) => {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    });

    axios.interceptors.response.use(
        (response) => response.data,
        (error) => {
            if (error.response?.status === 401) {
                localStorage.clear();
                window.location.href = "/login";
            }
            return Promise.reject(error);
        }
    );

    document.addEventListener("DOMContentLoaded", () => {
        ensureLogin();
        state.toast = new bootstrap.Toast(document.getElementById("editorToast"));
        bindCommonEvents();
        initialize();
    });

    async function initialize() {
        try {
            await loadCurrentUser();
            await loadDocument();
            connectWebSocket();
            setInterval(() => {
                if (state.currentDoc) {
                    saveDocument({ silent: true });
                }
            }, 30000);
        } catch (error) {
            console.error(error);
            alert(error.response?.data?.message || error.message || "初始化失败");
            window.location.href = "/documents";
        }
    }

    function ensureLogin() {
        const token = localStorage.getItem("token");
        if (!token || token === "undefined" || token === "null") {
            localStorage.clear();
            window.location.href = "/login";
        }
    }

    function unwrapResult(result) {
        return result && Object.prototype.hasOwnProperty.call(result, "data") ? result.data : result;
    }

    function showToast(message) {
        $("#editorToastBody").text(message);
        state.toast.show();
    }

    function updateSaveState(text, detail) {
        $("#saveStateText").text(text);
        $("#lastSavedText").text(detail || "");
    }

    function formatTime(value) {
        if (!value) {
            return "尚未记录";
        }
        return new Date(value).toLocaleString("zh-CN", { hour12: false });
    }

    function scheduleAutoSave() {
        updateSaveState("内容已变更", "等待自动保存...");
        clearTimeout(state.autoSaveTimer);
        state.autoSaveTimer = setTimeout(() => saveDocument({ silent: true }), 1200);
    }

    function scheduleSync() {
        if (state.applyingRemote) {
            return;
        }
        clearTimeout(state.syncTimer);
        state.syncTimer = setTimeout(() => {
            if (!state.ws || state.ws.readyState !== WebSocket.OPEN || !state.currentDoc) {
                return;
            }
            state.ws.send(JSON.stringify({
                type: "DOCUMENT_SYNC",
                clientId: state.clientId,
                documentId,
                title: $("#docTitle").val(),
                docType: state.currentDoc.docType,
                content: serializeCurrentContent(),
                userId: state.currentUser?.id,
                username: state.currentUser?.username
            }));
        }, 450);
    }

    function bindCommonEvents() {
        $("#docTitle").on("input", () => {
            if (!state.currentDoc) {
                return;
            }
            state.currentDoc.title = $("#docTitle").val();
            $("#workspaceTitle").text(state.currentDoc.title || "未命名文档");
            scheduleAutoSave();
            scheduleSync();
        });

        $("#manualSaveBtn").on("click", () => saveDocument({ silent: false }));
        $("#versionModal").on("shown.bs.modal", loadVersions);
        $("#createVersionBtn").on("click", createVersionSnapshot);
        $("#collabModal").on("shown.bs.modal", () => {
            loadCollaborators();
            $("#collabSearchResults").html("");
        });
        $("#collabSearchBtn").on("click", searchCollaborators);
        $("#collabRefreshBtn").on("click", loadCollaborators);
        $("#shareModal").on("shown.bs.modal", renderShareState);
        $("#createShareBtn").on("click", createShareLink);
        $("#cancelShareBtn").on("click", cancelShareLink);
        $("#attachmentModal").on("shown.bs.modal", loadAttachments);
        $("#uploadAttachmentBtn").on("click", uploadAttachment);
    }

    async function loadCurrentUser() {
        const result = await axios.get("/auth/info");
        state.currentUser = unwrapResult(result);
        $("#currentUserInfo").text(`${state.currentUser.nickname} @${state.currentUser.username}`);
    }

    async function loadDocument() {
        const result = await axios.get(`/document/${documentId}`);
        state.currentDoc = unwrapResult(result);
        renderDocumentMeta();
        initializeEditorByType();
    }

    function renderDocumentMeta() {
        $("#docTitle").val(state.currentDoc.title || "");
        $("#workspaceTitle").text(state.currentDoc.title || "未命名文档");
        $("#docTypeBadge").text(state.currentDoc.docType || "NOTE");
        $("#workspaceHint").text(state.currentDoc.docType === "NOTE"
            ? "Markdown 内容会实时预览并同步给其他窗口"
            : state.currentDoc.docType === "BOARD"
                ? "白板支持手绘、撤销与清空，并会同步到其他协作者"
                : "脑图支持节点增删改，结构变化会实时同步");
        updateSaveState("文档已载入", `最近更新时间：${formatTime(state.currentDoc.updateTime)}`);
    }

    function initializeEditorByType() {
        $("#notePane, #boardPane, #mindmapPane").addClass("d-none");
        if (state.currentDoc.docType === "BOARD") {
            $("#boardPane").removeClass("d-none");
            initializeBoardEditor(parseBoardContent(state.currentDoc.content));
            return;
        }
        if (state.currentDoc.docType === "MINDMAP") {
            $("#mindmapPane").removeClass("d-none");
            initializeMindmapEditor(parseMindmapContent(state.currentDoc.content));
            return;
        }

        $("#notePane").removeClass("d-none");
        initializeNoteEditor(state.currentDoc.content);
    }

    function connectWebSocket() {
        const protocol = window.location.protocol === "https:" ? "wss" : "ws";
        const query = new URLSearchParams({
            userId: String(state.currentUser.id),
            username: state.currentUser.username
        });
        state.ws = new WebSocket(`${protocol}://${window.location.host}/ws/collab/${documentId}?${query}`);

        state.ws.addEventListener("open", () => {
            $("#connectionStatus").text("协作已连接");
        });

        state.ws.addEventListener("close", () => {
            $("#connectionStatus").text("协作已断开");
        });

        state.ws.addEventListener("message", (event) => {
            const payload = JSON.parse(event.data);
            if (payload.type === "CONNECTED" || payload.type === "ONLINE_COUNT") {
                const count = payload.onlineCount || 1;
                $("#onlineStatus").text(`在线 ${count} 人`);
                return;
            }

            if (payload.type === "USER_JOIN") {
                $("#onlineStatus").text(`在线 ${payload.onlineCount || 1} 人`);
                showToast(`${payload.username || "协作者"} 加入了当前文档`);
                return;
            }

            if (payload.type === "USER_LEAVE") {
                $("#onlineStatus").text(`在线 ${payload.onlineCount || 1} 人`);
                return;
            }

            if (payload.type === "DOCUMENT_SYNC" && payload.clientId !== state.clientId) {
                applyRemoteUpdate(payload);
            }
        });
    }

    async function saveDocument(options) {
        if (!state.currentDoc) {
            return;
        }
        const silent = Boolean(options && options.silent);

        try {
            const payload = {
                title: $("#docTitle").val(),
                content: serializeCurrentContent()
            };
            await axios.put(`/document/${documentId}`, payload);
            state.currentDoc.title = payload.title;
            state.currentDoc.content = payload.content;
            state.currentDoc.updateTime = new Date().toISOString();
            updateSaveState("已保存", `保存时间：${formatTime(state.currentDoc.updateTime)}`);
            if (!silent) {
                showToast("文档保存成功");
            }
        } catch (error) {
            console.error(error);
            updateSaveState("保存失败", error.response?.data?.message || error.message);
            if (!silent) {
                alert(error.response?.data?.message || error.message || "保存失败");
            }
        }
    }

    function applyRemoteUpdate(payload) {
        if (!state.currentDoc) {
            return;
        }
        state.applyingRemote = true;
        state.currentDoc.title = payload.title || state.currentDoc.title;
        state.currentDoc.content = payload.content || state.currentDoc.content;
        $("#docTitle").val(state.currentDoc.title);
        $("#workspaceTitle").text(state.currentDoc.title || "未命名文档");

        if (state.currentDoc.docType === "BOARD") {
            initializeBoardEditor(parseBoardContent(state.currentDoc.content));
        } else if (state.currentDoc.docType === "MINDMAP") {
            initializeMindmapEditor(parseMindmapContent(state.currentDoc.content));
        } else {
            initializeNoteEditor(state.currentDoc.content);
        }

        updateSaveState("已同步远端更新", `同步时间：${formatTime(new Date().toISOString())}`);
        setTimeout(() => {
            state.applyingRemote = false;
        }, 0);
    }

    function serializeCurrentContent() {
        if (!state.currentDoc) {
            return "";
        }
        if (state.currentDoc.docType === "BOARD") {
            return JSON.stringify({
                version: 1,
                type: "BOARD",
                background: "#ffffff",
                strokes: state.board.strokes
            });
        }
        if (state.currentDoc.docType === "MINDMAP") {
            return JSON.stringify({
                version: 1,
                type: "MINDMAP",
                root: state.mindmap.root
            });
        }
        return $("#noteInput").val();
    }

    function initializeNoteEditor(content) {
        const normalized = !content || content === "{}" ? defaultNoteContent : content;
        $("#noteInput").val(normalized);
        renderMarkdownPreview(normalized);
        $("#noteInput").off("input").on("input", function () {
            const value = $(this).val();
            renderMarkdownPreview(value);
            scheduleAutoSave();
            scheduleSync();
        });
    }

    function renderMarkdownPreview(content) {
        $("#notePreview").html(marked.parse(content || ""));
    }

    function parseBoardContent(content) {
        try {
            const parsed = JSON.parse(content || "{}");
            return {
                version: 1,
                type: "BOARD",
                background: "#ffffff",
                strokes: Array.isArray(parsed.strokes) ? parsed.strokes : []
            };
        } catch (error) {
            return {
                version: 1,
                type: "BOARD",
                background: "#ffffff",
                strokes: []
            };
        }
    }

    function initializeBoardEditor(boardContent) {
        state.board.strokes = boardContent.strokes || [];
        state.board.canvas = document.getElementById("boardCanvas");
        state.board.ctx = state.board.canvas.getContext("2d");
        $("#boardColor").val(state.board.color);
        $("#boardBrushSize").val(state.board.brushSize);
        $("#boardBrushValue").text(`${state.board.brushSize} px`);

        resizeBoardCanvas();
        state.board.canvas.onpointerdown = startBoardStroke;
        state.board.canvas.onpointermove = continueBoardStroke;
        state.board.canvas.onpointerup = finishBoardStroke;
        state.board.canvas.onpointerleave = finishBoardStroke;
        window.onresize = resizeBoardCanvas;

        $("#boardColor").off("input").on("input", function () {
            state.board.color = $(this).val();
        });
        $("#boardBrushSize").off("input").on("input", function () {
            state.board.brushSize = Number($(this).val());
            $("#boardBrushValue").text(`${state.board.brushSize} px`);
        });
        $("#boardUndoBtn").off("click").on("click", () => {
            if (!state.board.strokes.length) {
                return;
            }
            state.board.strokes.pop();
            redrawBoard();
            scheduleAutoSave();
            scheduleSync();
        });
        $("#boardClearBtn").off("click").on("click", () => {
            state.board.strokes = [];
            redrawBoard();
            scheduleAutoSave();
            scheduleSync();
        });
    }

    function resizeBoardCanvas() {
        if (!state.board.canvas || !state.board.ctx) {
            return;
        }
        const rect = state.board.canvas.parentElement.getBoundingClientRect();
        const width = Math.max(rect.width, 320);
        const height = Math.max(rect.height, 420);
        const dpr = window.devicePixelRatio || 1;
        state.board.canvas.width = width * dpr;
        state.board.canvas.height = height * dpr;
        state.board.canvas.style.width = `${width}px`;
        state.board.canvas.style.height = `${height}px`;
        state.board.ctx.setTransform(1, 0, 0, 1, 0, 0);
        state.board.ctx.scale(dpr, dpr);
        redrawBoard();
    }

    function startBoardStroke(event) {
        state.board.drawing = true;
        state.board.currentStroke = {
            color: state.board.color,
            size: state.board.brushSize,
            points: [getBoardPoint(event)]
        };
        redrawBoard();
    }

    function continueBoardStroke(event) {
        if (!state.board.drawing || !state.board.currentStroke) {
            return;
        }
        state.board.currentStroke.points.push(getBoardPoint(event));
        redrawBoard();
    }

    function finishBoardStroke() {
        if (!state.board.drawing || !state.board.currentStroke) {
            return;
        }
        state.board.drawing = false;
        if (state.board.currentStroke.points.length === 1) {
            state.board.currentStroke.points.push({ ...state.board.currentStroke.points[0] });
        }
        state.board.strokes.push(state.board.currentStroke);
        state.board.currentStroke = null;
        redrawBoard();
        scheduleAutoSave();
        scheduleSync();
    }

    function getBoardPoint(event) {
        const rect = state.board.canvas.getBoundingClientRect();
        return {
            x: (event.clientX - rect.left) / rect.width,
            y: (event.clientY - rect.top) / rect.height
        };
    }

    function redrawBoard() {
        if (!state.board.ctx || !state.board.canvas) {
            return;
        }
        const ctx = state.board.ctx;
        const rect = state.board.canvas.getBoundingClientRect();
        ctx.clearRect(0, 0, rect.width, rect.height);
        state.board.strokes.forEach((stroke) => drawStroke(ctx, stroke, rect));
        if (state.board.currentStroke) {
            drawStroke(ctx, state.board.currentStroke, rect);
        }
    }

    function drawStroke(ctx, stroke, rect) {
        if (!stroke.points || !stroke.points.length) {
            return;
        }
        ctx.beginPath();
        ctx.lineJoin = "round";
        ctx.lineCap = "round";
        ctx.strokeStyle = stroke.color || "#1d3557";
        ctx.lineWidth = Number(stroke.size || 4);

        stroke.points.forEach((point, index) => {
            const x = point.x * rect.width;
            const y = point.y * rect.height;
            if (index === 0) {
                ctx.moveTo(x, y);
            } else {
                ctx.lineTo(x, y);
            }
        });
        ctx.stroke();
    }

    function parseMindmapContent(content) {
        try {
            const parsed = JSON.parse(content || "{}");
            if (parsed.root) {
                return parsed;
            }
        } catch (error) {
            console.warn("脑图内容解析失败，使用默认结构", error);
        }
        return {
            version: 1,
            type: "MINDMAP",
            root: {
                id: "root",
                text: "中心主题",
                note: "",
                children: []
            }
        };
    }

    function initializeMindmapEditor(mindmapContent) {
        state.mindmap.root = ensureMindmapNodeShape(mindmapContent.root || parseMindmapContent(null).root);
        if (!findMindmapNode(state.mindmap.root, state.mindmap.selectedId)) {
            state.mindmap.selectedId = state.mindmap.root.id;
        }
        renderMindmapTree();
        populateMindmapForm();

        $("#mindmapNodeText").off("input").on("input", function () {
            const node = getSelectedMindmapNode();
            if (!node) {
                return;
            }
            node.text = $(this).val() || "未命名节点";
            renderMindmapTree();
            scheduleAutoSave();
            scheduleSync();
        });

        $("#mindmapNodeNote").off("input").on("input", function () {
            const node = getSelectedMindmapNode();
            if (!node) {
                return;
            }
            node.note = $(this).val();
            renderMindmapTree();
            scheduleAutoSave();
            scheduleSync();
        });

        $("#mindmapAddChildBtn").off("click").on("click", () => {
            const node = getSelectedMindmapNode();
            if (!node) {
                return;
            }
            node.children.push(createMindmapNode("新子节点"));
            renderMindmapTree();
            scheduleAutoSave();
            scheduleSync();
        });

        $("#mindmapAddSiblingBtn").off("click").on("click", () => {
            if (state.mindmap.selectedId === state.mindmap.root.id) {
                showToast("中心主题不能添加同级节点，请添加子节点");
                return;
            }
            const parent = findMindmapParent(state.mindmap.root, state.mindmap.selectedId);
            if (!parent) {
                return;
            }
            parent.children.push(createMindmapNode("新同级节点"));
            renderMindmapTree();
            scheduleAutoSave();
            scheduleSync();
        });

        $("#mindmapDeleteBtn").off("click").on("click", () => {
            if (state.mindmap.selectedId === state.mindmap.root.id) {
                showToast("中心主题不能被删除");
                return;
            }
            const parent = findMindmapParent(state.mindmap.root, state.mindmap.selectedId);
            if (!parent) {
                return;
            }
            parent.children = parent.children.filter((child) => child.id !== state.mindmap.selectedId);
            state.mindmap.selectedId = state.mindmap.root.id;
            renderMindmapTree();
            populateMindmapForm();
            scheduleAutoSave();
            scheduleSync();
        });
    }

    function renderMindmapTree() {
        const html = renderMindmapNode(state.mindmap.root);
        $("#mindmapTree").html(`<ul>${html}</ul>`);
        $(".mindmap-node-card").off("click").on("click", function () {
            state.mindmap.selectedId = $(this).data("nodeId");
            renderMindmapTree();
            populateMindmapForm();
        });
    }

    function renderMindmapNode(node) {
        const activeClass = node.id === state.mindmap.selectedId ? "active" : "";
        const note = node.note ? `<div class="mindmap-node-note">${escapeHtml(node.note)}</div>` : "";
        const children = (node.children || []).map(renderMindmapNode).join("");
        return `<li class="mindmap-tree-node">
            <div class="mindmap-node-card ${activeClass}" data-node-id="${node.id}">
                <div>
                    <div class="mindmap-node-title">${escapeHtml(node.text || "未命名节点")}</div>
                    ${note}
                </div>
                <small>${(node.children || []).length} 子节点</small>
            </div>
            ${children ? `<ul>${children}</ul>` : ""}
        </li>`;
    }

    function populateMindmapForm() {
        const node = getSelectedMindmapNode();
        $("#mindmapNodeText").val(node?.text || "");
        $("#mindmapNodeNote").val(node?.note || "");
    }

    function getSelectedMindmapNode() {
        return findMindmapNode(state.mindmap.root, state.mindmap.selectedId);
    }

    function ensureMindmapNodeShape(node) {
        return {
            id: node.id || `node-${Date.now()}`,
            text: node.text || "未命名节点",
            note: node.note || "",
            children: Array.isArray(node.children) ? node.children.map(ensureMindmapNodeShape) : []
        };
    }

    function createMindmapNode(text) {
        return {
            id: `node-${Date.now()}-${Math.random().toString(36).slice(2, 6)}`,
            text,
            note: "",
            children: []
        };
    }

    function findMindmapNode(node, targetId) {
        if (!node) {
            return null;
        }
        if (node.id === targetId) {
            return node;
        }
        for (const child of node.children || []) {
            const found = findMindmapNode(child, targetId);
            if (found) {
                return found;
            }
        }
        return null;
    }

    function findMindmapParent(node, targetId) {
        if (!node || !Array.isArray(node.children)) {
            return null;
        }
        if (node.children.some((child) => child.id === targetId)) {
            return node;
        }
        for (const child of node.children) {
            const found = findMindmapParent(child, targetId);
            if (found) {
                return found;
            }
        }
        return null;
    }

    function escapeHtml(value) {
        return String(value || "")
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;");
    }

    async function loadVersions() {
        const result = await axios.get(`/document/${documentId}/versions`);
        const versions = unwrapResult(result) || [];
        if (!versions.length) {
            $("#versionList").html('<div class="stack-item stack-empty">暂时还没有保存过历史版本。</div>');
            return;
        }

        const html = versions.map((version) => `
            <div class="stack-item">
                <div class="stack-item-head">
                    <div>
                        <strong>${escapeHtml(version.versionName || `版本 v${version.versionNumber}`)}</strong>
                        <div class="share-note">${escapeHtml(version.changeLog || "未填写变更说明")}</div>
                        <small class="share-note">创建时间：${formatTime(version.createTime)}，大小：${version.fileSize || 0} bytes</small>
                    </div>
                    <button class="btn btn-outline-primary btn-sm version-restore-btn" data-version-id="${version.id}">恢复</button>
                </div>
            </div>
        `).join("");
        $("#versionList").html(html);
        $(".version-restore-btn").off("click").on("click", async function () {
            const versionId = $(this).data("versionId");
            await axios.post(`/document/${documentId}/versions/${versionId}/restore`);
            await loadDocument();
            showToast("版本恢复成功");
            bootstrap.Modal.getInstance(document.getElementById("versionModal")).hide();
        });
    }

    async function createVersionSnapshot() {
        await axios.post(`/document/${documentId}/versions`, {
            versionName: $("#versionNameInput").val(),
            changeLog: $("#versionChangeLogInput").val()
        });
        $("#versionNameInput").val("");
        $("#versionChangeLogInput").val("");
        await loadVersions();
        showToast("已保存新版本");
    }

    async function searchCollaborators() {
        const keyword = $("#collabKeywordInput").val().trim();
        if (!keyword) {
            $("#collabSearchResults").html('<div class="stack-item stack-empty">请输入用户名或昵称后再搜索。</div>');
            return;
        }

        const result = await axios.get("/user/search", { params: { keyword } });
        const users = unwrapResult(result) || [];
        if (!users.length) {
            $("#collabSearchResults").html('<div class="stack-item stack-empty">没有找到匹配用户。</div>');
            return;
        }

        const html = users.map((user) => `
            <div class="stack-item">
                <div class="stack-item-head">
                    <div>
                        <strong>${escapeHtml(user.nickname)}</strong>
                        <div class="share-note">@${escapeHtml(user.username)}</div>
                    </div>
                    <button class="btn btn-primary btn-sm collab-invite-btn" data-user-id="${user.id}">邀请</button>
                </div>
            </div>
        `).join("");
        $("#collabSearchResults").html(html);

        $(".collab-invite-btn").off("click").on("click", async function () {
            await axios.post(`/document/${documentId}/collaborators`, {
                userId: $(this).data("userId"),
                permission: $("#collabPermissionSelect").val()
            });
            await loadCollaborators();
            showToast("协作者已添加");
        });
    }

    async function loadCollaborators() {
        const result = await axios.get(`/document/${documentId}/collaborators`);
        const rows = unwrapResult(result) || [];
        if (!rows.length) {
            $("#collaboratorList").html('<div class="stack-item stack-empty">当前没有协作者。</div>');
            return;
        }

        const ownerId = state.currentDoc.userId;
        const canManage = state.currentUser && state.currentUser.id === ownerId;
        const html = rows.map((row) => {
            const isOwner = row.permission === "OWNER";
            const permissionControl = isOwner
                ? '<span class="badge text-bg-dark">创建者</span>'
                : `<select class="form-select form-select-sm collaborator-permission" data-collab-id="${row.collaborationId}" ${canManage ? "" : "disabled"}>
                        <option value="EDIT" ${row.permission === "EDIT" ? "selected" : ""}>可编辑</option>
                        <option value="VIEW" ${row.permission === "VIEW" ? "selected" : ""}>仅查看</option>
                   </select>`;
            const removeButton = !isOwner && canManage
                ? `<button class="btn btn-outline-danger btn-sm collaborator-remove" data-collab-id="${row.collaborationId}">移除</button>`
                : "";
            return `<div class="stack-item">
                <div class="stack-item-head">
                    <div>
                        <strong>${escapeHtml(row.nickname || row.username)}</strong>
                        <div class="share-note">@${escapeHtml(row.username)}</div>
                        <small class="share-note">最近编辑：${formatTime(row.lastEditTime)}</small>
                    </div>
                    <div class="d-flex gap-2 align-items-center">${permissionControl}${removeButton}</div>
                </div>
            </div>`;
        }).join("");
        $("#collaboratorList").html(html);

        $(".collaborator-permission").off("change").on("change", async function () {
            const collabId = $(this).data("collabId");
            await axios.put(`/document/${documentId}/collaborators/${collabId}`, {
                permission: $(this).val()
            });
            showToast("协作权限已更新");
        });

        $(".collaborator-remove").off("click").on("click", async function () {
            await axios.delete(`/document/${documentId}/collaborators/${$(this).data("collabId")}`);
            await loadCollaborators();
            showToast("协作者已移除");
        });
    }

    function renderShareState() {
        const shareLink = state.currentDoc?.shareLink;
        if (!shareLink) {
            $("#shareResult").html('<div class="share-note">当前尚未创建分享链接。</div>');
            return;
        }
        renderShareResult({
            shareLink,
            shareUrl: `/share/${shareLink}`,
            expireTime: state.currentDoc.shareExpireTime,
            hasPassword: Boolean(state.currentDoc.sharePassword)
        });
    }

    async function createShareLink() {
        const result = await axios.post(`/document/${documentId}/share`, {
            password: $("#sharePasswordInput").val(),
            expireHours: Number($("#shareExpireSelect").val())
        });
        const data = unwrapResult(result);
        state.currentDoc.shareLink = data.shareLink;
        state.currentDoc.shareExpireTime = data.expireTime;
        renderShareResult(data);
        showToast("分享链接已生成");
    }

    async function cancelShareLink() {
        await axios.delete(`/document/${documentId}/share`);
        state.currentDoc.shareLink = null;
        state.currentDoc.shareExpireTime = null;
        state.currentDoc.sharePassword = null;
        $("#shareResult").html('<div class="share-note">分享已关闭，旧链接将失效。</div>');
        showToast("分享已关闭");
    }

    function renderShareResult(data) {
        const fullUrl = `${window.location.origin}${data.shareUrl}`;
        const passwordText = data.hasPassword ? "需要密码访问" : "无需密码";
        $("#shareResult").html(`
            <div class="stack-item">
                <strong>分享链接已生成</strong>
                <div class="share-note mt-2">${passwordText}，过期时间：${data.expireTime ? formatTime(data.expireTime) : "不过期"}</div>
                <a class="d-block mt-2" href="${fullUrl}" target="_blank">${fullUrl}</a>
                <button id="copyShareLinkBtn" class="btn btn-outline-primary btn-sm mt-3">复制链接</button>
            </div>
        `);
        $("#copyShareLinkBtn").off("click").on("click", async () => {
            await navigator.clipboard.writeText(fullUrl);
            showToast("分享链接已复制");
        });
    }

    async function loadAttachments() {
        const result = await axios.get(`/document/${documentId}/attachments`);
        const attachments = unwrapResult(result) || [];
        if (!attachments.length) {
            $("#attachmentList").html('<div class="stack-item stack-empty">当前还没有上传附件。</div>');
            return;
        }

        const html = attachments.map((attachment) => `
            <div class="stack-item">
                <div class="stack-item-head">
                    <div>
                        <strong>${escapeHtml(attachment.fileName)}</strong>
                        <div class="share-note">${attachment.fileType || "未知类型"} · ${attachment.fileSize || 0} bytes</div>
                    </div>
                    <div class="d-flex gap-2">
                        <a class="btn btn-outline-primary btn-sm" href="/api/attachment/${attachment.id}/download">下载</a>
                        <button class="btn btn-outline-danger btn-sm attachment-delete-btn" data-attachment-id="${attachment.id}">删除</button>
                    </div>
                </div>
            </div>
        `).join("");
        $("#attachmentList").html(html);
        $(".attachment-delete-btn").off("click").on("click", async function () {
            await axios.delete(`/document/${documentId}/attachments/${$(this).data("attachmentId")}`);
            await loadAttachments();
            showToast("附件已删除");
        });
    }

    async function uploadAttachment() {
        const file = $("#attachmentFileInput")[0].files[0];
        if (!file) {
            showToast("请先选择附件");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);
        await axios.post(`/document/${documentId}/attachments`, formData, {
            headers: { "Content-Type": "multipart/form-data" }
        });
        $("#attachmentFileInput").val("");
        await loadAttachments();
        showToast("附件上传成功");
    }
})();
