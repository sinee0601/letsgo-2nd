var CHAT_API_URL = 'http://127.0.0.1:8000/chat';

function getChatSessionId() {
    var key = 'chatbot_session_id';
    var id = localStorage.getItem(key);
    if (!id) {
        id = (window.crypto && crypto.randomUUID)
            ? crypto.randomUUID()
            : 'sess-' + Date.now() + '-' + Math.random().toString(16).slice(2);
        localStorage.setItem(key, id);
    }
    return id;
}

document.addEventListener('DOMContentLoaded', function () {
    var contextPath = '';
    var bodyContextPath = document.body.getAttribute('data-context-path');
    if (bodyContextPath) {
        contextPath = bodyContextPath.replace(/\/$/, '');
    }

    fetch(contextPath + '/view/chatbot.html')
        .then(function (response) {
            return response.text();
        })
        .then(function (html) {
            document.body.insertAdjacentHTML('beforeend', html);
            startChatbot(contextPath);
        });
});

function startChatbot(contextPath) {
    var panel = document.getElementById('chatModal');
    var openBtn = document.querySelector('.btn-open-chatbot');
    var closeBtn = document.getElementById('btn-close-chatbot');
    var sendBtn = document.getElementById('btn-send');
    var chatInput = document.getElementById('chatInput');
    var chatBox = document.getElementById('chatBox');

    if (panel == null || openBtn == null || closeBtn == null || sendBtn == null
        || chatInput == null || chatBox == null) {
        console.error('챗봇 요소가 없습니다.');
        return;
    }

    openBtn.addEventListener('click', function () {
        panel.classList.toggle('show');
        if (panel.classList.contains('show')) {
            chatInput.focus();
        }
    });

    closeBtn.addEventListener('click', function () {
        panel.classList.remove('show');
    });

    sendBtn.addEventListener('click', function () {
        sendMessage(chatInput, chatBox);
    });

    chatInput.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            sendMessage(chatInput, chatBox);
        }
    });
}

function sendMessage(chatInput, chatBox) {
    var text = chatInput.value.trim();
    if (!text) {
        return;
    }

    appendMessage(chatBox, 'user', text);
    chatInput.value = '';

    var typing = appendTyping(chatBox);

    fetch(CHAT_API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            message: text,
            session_id: getChatSessionId()
        })
    })
        .then(function (res) { return res.json(); })
        .then(function (data) {

            typing.remove();
            appendMessage(chatBox, 'bot', data.bot_response);
        })
        .catch(function (err) {
            typing.remove();
            appendMessage(chatBox, 'bot', '응답을 가져오지 못했습니다.');
            console.error(err);
        });
}

function appendMessage(chatBox, who, text) {
    var msg = document.createElement('div');
    msg.className = 'chat-msg ' + (who === 'user' ? 'chat-msg-user' : 'chat-msg-bot');

    if (who === 'bot') {
        var avatar = document.createElement('div');
        avatar.className = 'chat-msg-avatar';
        avatar.textContent = '🤖';
        msg.appendChild(avatar);
    }

    var bubble = document.createElement('div');
    bubble.className = 'chat-bubble';
    bubble.textContent = text;
    msg.appendChild(bubble);

    chatBox.appendChild(msg);
    chatBox.scrollTop = chatBox.scrollHeight;
    return msg;
}

function appendTyping(chatBox) {
    var msg = document.createElement('div');
    msg.className = 'chat-msg chat-msg-bot';

    var avatar = document.createElement('div');
    avatar.className = 'chat-msg-avatar';
    avatar.textContent = '🤖';

    var bubble = document.createElement('div');
    bubble.className = 'chat-bubble chat-typing';
    bubble.innerHTML = '<span></span><span></span><span></span>';

    msg.appendChild(avatar);
    msg.appendChild(bubble);
    chatBox.appendChild(msg);
    chatBox.scrollTop = chatBox.scrollHeight;
    return msg;
}
