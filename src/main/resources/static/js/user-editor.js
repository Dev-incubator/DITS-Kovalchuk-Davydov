const createNewUserForm = document.getElementById('newUserForm');
const editUserForm = document.getElementById('editUserForm');
const newUserFormButton = document.getElementById('newUserFormCloseButton');
const editUserFormButton = document.getElementById('editUserFormCloseButton');
const detailList = document.getElementById('detailList');
const deleteConfirmationModal = document.getElementById('confirmDeleteModal');
const token = document.head.querySelector('meta[name="_csrf"]').getAttribute('content');
let usersData = null;
let currentUserId = null;


window.onLoad = fillUsers();

detailList.addEventListener('click', ({target}) => {
    if (target.closest('.user-info')) {
        clickUserHandler(target);
    }
});

deleteConfirmationModal.addEventListener('click', ({target}) => {
    if (target.closest('.confirmation-modal')) {
        userDeleteClickHandler(target);
    }
})

createNewUserForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(createNewUserForm);
    const firstName = formData.get('firstName');
    const lastName = formData.get('lastName');
    const role = formData.get('role');
    const login = formData.get('login');
    const password = formData.get('password');
    addNewUser(firstName, lastName, role, login, password).then();
    createNewUserForm.reset();
});

editUserForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(editUserForm);
    const firstName = formData.get('firstName');
    const lastName = formData.get('lastName');
    const role = formData.get('role');
    const login = formData.get('login');
    const password = formData.get('password');
    editUser(currentUserId, firstName, lastName, role, login, password).then();
    createNewUserForm.reset();
});

function setCurrentUserId(target) {
    currentUserId = target.closest('.user-info').dataset.id;
}

function clickUserHandler(target) {
    setCurrentUserId(target);
    if (target.classList.contains('users-list-item')) {
        target.closest('.user-info').classList.toggle('open');
    } else if (target.closest('.user__edit-button')) {
        fillEditUserForm(currentUserId).then();
    }
}

function userDeleteClickHandler(target) {
    if (target.closest('.confirm-button')) {
        deleteUser(currentUserId).then();
        console.log('user with id:' + currentUserId + ' was deleted');
    }
}

function getUserInfoHtml({userId, firstName, lastName, role, login, password}) {
    const userInfo = document.createElement('div');
    userInfo.className = 'user-info mb-5';
    userInfo.dataset.id = userId;
    userInfo.innerHTML = `
    <div class="row">
      <a class="col-md-9 d-flex users-list-item" data-bs-toggle="collapse" href='#user${userId}' role="button" aria-expanded="false" aria-controls="collapseExample">
        <p class="user-info__name">${firstName} ${lastName} </p>
        <p class="user-info__email users__email__left">${login}</p>
      </a>
      <div class="col-md-3 test__control text-md-end">
        <button class="user__edit-button" id="editUserFormButton" data-bs-target="#editUserModal" data-bs-toggle="modal"><img src="/img/edit-icon.svg" alt="Edit user" class="icon-btn"></button>
        <button class="user__delete-button"><img src="/img/delete-icon.svg" data-bs-target="#confirmDeleteModal" data-bs-toggle="modal" alt="Delete user" class="icon-btn"></button>
      </div>
    </div>
    <div class="collapse user-detail-info mt-3" id=user${userId} data-user-id=${userId}>
            <div class="row user-detail-item mb-2 mt-3" data-id=${userId}>
              <div class="user-param">
                <span>Name</span><input class="form-input mb-1 w-50" type="text" value="${firstName}" disabled="disabled"/>
              </div>
              <div class="user-param">
                 <span>Surname</span><input class="form-input mb-1 w-50" type="text" value="${lastName}" disabled="disabled"/>
              </div>
              <div class="user-param">
                <span>Role</span><input class="form-input mb-1 w-50" type="text" value="${role}" disabled="disabled"/>
              </div>
              <div class="user-param">
                <span>Login</span><input class="form-input mb-1 w-50" type="text" value="${login}" disabled="disabled"/>
              </div>
              <div class="user-param">
                <span>Password</span><input class="form-input mb-1 w-50" type="text" value="${password}" disabled="disabled"/>
              </div>
            </div>
    </div>
  `
    return userInfo
}

function getUserInfoEditFormHtml(userId, firstName, lastName, role, login, password) {
    document.getElementById('firstName').value = firstName;
    document.getElementById('lastName').value = lastName;
    document.getElementById('role').value = role;
    document.getElementById('login').value = login;
    document.getElementById('password').value = password;
}

async function updateUsers(usersList) {
    console.log(usersList);
    detailList.classList.add('active');
    detailList.innerHTML = '';
    Array.from(await usersList).forEach(data => {
        detailList.appendChild(getUserInfoHtml(data))
    });
}

async function fillUsers() {
    usersData = getUsersData();
    console.log(usersData);
    detailList.classList.add('active');
    detailList.innerHTML = '';
    Array.from(await usersData).forEach(data => {
        detailList.appendChild(getUserInfoHtml(data))
    });
}

async function getUsersData() {
    const url = new URL("http://localhost:8080/admin/getUsers");
    const response = await fetch(url.toString());
    usersData = await response.json();
    return usersData;
}

async function fillEditUserForm(currentUserId) {
    let userInfo = usersData.find(({userId}) => {
        return currentUserId == userId;
    });
    console.log('Gotten user for edit/ ' + JSON.stringify(userInfo));
    getUserInfoEditFormHtml(currentUserId, userInfo.firstName, userInfo.lastName, userInfo.role, userInfo.login, userInfo.password);
}

async function addNewUser(firstName, lastName, role, login, password) {
    newUserFormButton.click();
    const url = new URL("http://localhost:8080/admin/addUser");
    let userInfo = {firstName, lastName, role, login, password};
    const response = await fetch(url.toString(), {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        },
        body: JSON.stringify(userInfo)
    });
    usersData = await response.json();
    await updateUsers(usersData);
}

async function editUser(userId, firstName, lastName, role, login, password) {
    editUserFormButton.click();
    const url = new URL("http://localhost:8080/admin/editUser");
    let userInfo = {userId, firstName, lastName, role, login, password};
    const response = await fetch(url.toString(), {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        },
        body: JSON.stringify(userInfo)
    });
    usersData = await response.json();
    await updateUsers(usersData);
}

async function deleteUser(userId) {
    const url = new URL("http://localhost:8080/admin/deleteUser");
    let params = {userId};
    url.search = new URLSearchParams(params).toString();
    const response = await fetch(url.toString(), {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        }
    });
    usersData = await response.json();
    console.log(usersData);
    await updateUsers(usersData);
}