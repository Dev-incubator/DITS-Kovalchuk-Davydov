// TODO:
// Check if this file is necessary
// We have no info on page that uses current script
//
// And also rename this file

const togglePassword = document.querySelector('#togglePassword');
const password = document.querySelector('#password-input');

togglePassword.addEventListener('click', function () {
  const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
  password.setAttribute('type', type);
  this.classList.toggle('fa-eye-slash');
});

const loginHelp = document.getElementById('loginHelp');
const input = document.getElementById('login-input');

input.addEventListener('input',checkInputForPassword);

function checkInputForPassword() {
  console.log(input.value.length);
  if (input.value.length >= 1 && input.value.length < 4) {
    loginHelp.style.display = 'block';
  }
  else {
    loginHelp.style.display = 'none';
  }
}