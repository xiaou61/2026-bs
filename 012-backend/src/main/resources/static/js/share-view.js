(function () {
    const shareLink = document.body.dataset.shareLink;

    axios.interceptors.response.use((response) => response.data);

    document.addEventListener("DOMContentLoaded", initialize);

    async function initialize() {
        try {
            const result = await axios.get(`/api/share/${shareLink}/info`);
            const info = unwrapResult(result);
            $("#shareTitle").text(info.title || "分享文档");
            $("#shareMeta").text(`文档类型：${info.docType} · ${info.expireTime ? `过期时间：${formatTime(info.expireTime)}` : "链接不过期"}`);

            if (info.hasPassword) {
                $("#shareGate").removeClass("d-none");
                $("#shareAccessBtn").on("click", () => accessDocument());
                $("#sharePasswordField").on("keydown", (event) => {
                    if (event.key === "Enter") {
                        accessDocument();
                    }
                });
            } else {
                await accessDocument();
            }
        } catch (error) {
            renderError(error.response?.data?.message || error.message || "分享链接不可用");
        }
    }

    function unwrapResult(result) {
        return result && Object.prototype.hasOwnProperty.call(result, "data") ? result.data : result;
    }

    async function accessDocument() {
        try {
            const result = await axios.post(`/api/share/${shareLink}/access`, {
                password: $("#sharePasswordField").val()
            });
            const doc = unwrapResult(result);
            $("#shareGate").addClass("d-none");
            $("#shareContent").removeClass("d-none");
            renderDocument(doc);
        } catch (error) {
            renderError(error.response?.data?.message || error.message || "访问分享文档失败");
        }
    }

    function renderDocument(doc) {
        if (doc.docType === "BOARD") {
            $("#shareBoardPane").removeClass("d-none");
            renderBoard(doc.content);
            return;
        }
        if (doc.docType === "MINDMAP") {
            $("#shareMindmapPane").removeClass("d-none");
            renderMindmap(doc.content);
            return;
        }
        $("#shareNotePane").removeClass("d-none");
        $("#shareNotePreview").html(marked.parse(doc.content || ""));
    }

    function renderBoard(content) {
        const data = parseBoardContent(content);
        const canvas = document.getElementById("shareBoardCanvas");
        const ctx = canvas.getContext("2d");
        const rect = canvas.parentElement.getBoundingClientRect();
        const dpr = window.devicePixelRatio || 1;
        canvas.width = rect.width * dpr;
        canvas.height = Math.max(rect.height, 520) * dpr;
        canvas.style.height = `${Math.max(rect.height, 520)}px`;
        ctx.setTransform(1, 0, 0, 1, 0, 0);
        ctx.scale(dpr, dpr);
        ctx.clearRect(0, 0, rect.width, Math.max(rect.height, 520));
        (data.strokes || []).forEach((stroke) => {
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
                const y = point.y * Math.max(rect.height, 520);
                if (index === 0) {
                    ctx.moveTo(x, y);
                } else {
                    ctx.lineTo(x, y);
                }
            });
            ctx.stroke();
        });
    }

    function renderMindmap(content) {
        const data = parseMindmapContent(content);
        $("#shareMindmapTree").html(`<ul>${renderMindmapNode(data.root)}</ul>`);
    }

    function renderMindmapNode(node) {
        const note = node.note ? `<div class="share-mindmap-note">${escapeHtml(node.note)}</div>` : "";
        const children = (node.children || []).map(renderMindmapNode).join("");
        return `<li><div class="share-mindmap-node"><strong>${escapeHtml(node.text || "未命名节点")}</strong>${note}</div>${children ? `<ul>${children}</ul>` : ""}</li>`;
    }

    function parseBoardContent(content) {
        try {
            return JSON.parse(content || "{}");
        } catch (error) {
            return { strokes: [] };
        }
    }

    function parseMindmapContent(content) {
        try {
            const parsed = JSON.parse(content || "{}");
            if (parsed.root) {
                return parsed;
            }
        } catch (error) {
            console.warn(error);
        }
        return {
            root: {
                id: "root",
                text: "中心主题",
                note: "",
                children: []
            }
        };
    }

    function escapeHtml(value) {
        return String(value || "")
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;");
    }

    function formatTime(value) {
        return new Date(value).toLocaleString("zh-CN", { hour12: false });
    }

    function renderError(message) {
        $("#shareMeta").text(message);
        $("#shareGate").addClass("d-none");
        $("#shareContent").addClass("d-none");
    }
})();
