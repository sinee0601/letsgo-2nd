const modal = document.getElementById('chatModal');
const openBtn = document.querySelector('.btn-open-modal');
const closeBtn = document.getElementById('btn-close-modal');
const sendBtn = document.getElementById('btn-send');
const chatInput = document.getElementById('chatInput');
const chatBox = document.getElementById('chatBox');

openBtn.addEventListener('click', () => modal.classList.add('show'));
closeBtn.addEventListener('click', () => modal.classList.remove('show'));

function sendMessage() {
    const text = chatInput.value.trim();
    if (!text) return;

    chatBox.innerHTML += `<div><strong>나:</strong> ${text}</div>`;
    chatInput.value = '';
    chatBox.scrollTop = chatBox.scrollHeight;

    setTimeout(() => {
        chatBox.innerHTML += `<div><strong>🤖:</strong> 방금 "${text}" 라고 했냐? 테스트 중이다.</div>`;
        chatBox.scrollTop = chatBox.scrollHeight;
    }, 1000);
}

sendBtn.addEventListener('click', sendMessage);
chatInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') sendMessage();
});