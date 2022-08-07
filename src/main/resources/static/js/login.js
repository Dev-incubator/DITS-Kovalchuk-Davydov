const passwordInput = document.getElementById('password-input');
const passwordControl = document.getElementById('password-control');

passwordControl.addEventListener('click', (event) => {
    const target = event.target;
    const isContainOpenClass = target.classList.contains('open');
    target.classList.toggle('open');
    if (isContainOpenClass) {
        passwordInput.setAttribute('type', 'password');
    } else {
        passwordInput.setAttribute('type', 'text');
    }
});


