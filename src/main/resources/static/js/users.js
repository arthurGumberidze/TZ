const allUsersTableBody = document.getElementById('all-users-table-body');
const URLUsersApi = 'http://localhost:8080/rest/api/users';
let users;

async function loadIntoTablesBody(url, tableBody) {
    const response = await fetch(url);
    users = await response.json();

    tableBody.innerHTML = ''

    for (const user of users) {
        const bodyElement = document.createElement("tr")
        bodyElement.id = user.id
        bodyElement.appendChild(createTd(user.id));
        bodyElement.appendChild(createTd(user.lastname));
        bodyElement.appendChild(createTd(user.email));
        bodyElement.appendChild(createTd(user.username));
        bodyElement.appendChild(createTd(`${user.namesOfRoles.join(', ')}`));
        const editCell = document.createElement("td");
        editCell.innerHTML = `
            <button type="button" onClick="editModalOpen(event, ${user.id})" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editModal">Edit</button>
        `;
        bodyElement.appendChild(editCell);

        const deleteCell = document.createElement("td");
        deleteCell.innerHTML = `
            <button type="button" onClick="deleteModalOpen(event, ${user.id})" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</button>
        `;
        bodyElement.appendChild(deleteCell);
        tableBody.appendChild(bodyElement)
    }
}

function createTd(user) {
    const newElement = document.createElement("td");
    newElement.textContent = user;
    return newElement;
}

loadIntoTablesBody(URLUsersApi, allUsersTableBody);

function editModalOpen(e, id) {
    e.preventDefault()
    fetch('http://localhost:8080/rest/api/users/' + id, {
        method: 'GET'
    })
        .then(res => res.json())
        .then((data) => {
            let controlId = document.getElementById('idEdit')
            let controllastname = document.getElementById('lastnameEdit')
            let controlEmail = document.getElementById('emailEdit')
            let controlUsername = document.getElementById('usernameEdit')
            let controlPassword = document.getElementById('passwordEdit')
            let controlRoles = document.getElementById('rolesEdit')
            let optionSelect = controlRoles.getElementsByTagName('option')

            reSelectRolesOption(optionSelect, data);
            fillValuesFromData(controlId, controllastname, controlEmail, controlUsername, data)
            controlPassword.value = data.password
        })
}

function fillValuesFromData(controlId, controlName, controlEmail, controlUsername, data) {
    controlId.value = data.id
    controlName.value = data.name
    controlEmail.value = data.email
    controlUsername.value = data.username
}

function deleteModalOpen(e, id) {
    e.preventDefault()
    fetch('http://localhost:8080/rest/api/users/' + id, {
        method: 'GET'
    })
        .then(res => res.json())
        .then((data) => {
            let controlDelId = document.getElementById('idDelete')
            let controlDellastname = document.getElementById('lastnameDelete')
            let controlDelEmail = document.getElementById('emailDelete')
            let controlDelUsername = document.getElementById('usernameDelete')
            let controlRoles = document.getElementById('rolesDelete')
            let optionSelect = controlRoles.getElementsByTagName('option')

            reSelectRolesOption(optionSelect, data)
            fillValuesFromData(controlDelId,  controlDellastname, controlDelEmail, controlDelUsername, data)
        })
}

function sendDeleteForm(e) {
    e.preventDefault()
    let form = document.getElementById('deleteForm')
    let id = form.querySelector('#idDelete').value
    fetch(`http://localhost:8080/rest/api/users/`+id, {
        method: 'DELETE'
    })
    let row = document.getElementById(`${id}`)
    row.remove()
    document.getElementById('deleteFormClose').click()
}

function reSelectRolesOption(optionSelect, data) {
    for (let i = 0; i < optionSelect.length; i++) {
        optionSelect[i].selected = false
    }
    for (let i = 0; i < optionSelect.length; i++) {
        data.roles.forEach(role => {
            if (role.name.includes(optionSelect[i].id)) {
                optionSelect[i].selected = true
            }
        })
    }
}

async function sendSaveForm(e) {
    e.preventDefault()

    let form = document.getElementById('saveForm')
    let formData = new FormData(form)
    let values = Object.fromEntries(formData.entries())

    let option = form.querySelectorAll('option')
    let roleArr = []

    for (let i = 0; i < option.length; i++) {
        if (option[i].selected === true) {
            let eachRole = {}
            eachRole.id = option[i].value
            roleArr.push(eachRole)
        }
    }
    values.roles = roleArr
    console.log(values.roles)

    await fetch(URLUsersApi, {
        method: 'POST',
        headers: {
            // 'Accept': 'application/json',
            'Content-Type': 'application/json',
            // 'Referer': null
        },
        body: JSON.stringify(values)
    }).then(form.reset())
    loadIntoTablesBody(URLUsersApi, allUsersTableBody);
    document.getElementById('nav-users-table-tab').click()
}

async function sendEditForm(e) {
    e.preventDefault()
    let form = document.getElementById('editForm')
    let formData = new FormData(form)
    let values = Object.fromEntries(formData.entries())
    let option = form.querySelectorAll('option')
    let roleArr = []

    for (let i = 0; i < option.length; i++) {
        if (option[i].selected === true) {
            let eachRole = {}
            eachRole.id = option[i].value
            roleArr.push(eachRole)
        }
    }

    values.roles = roleArr
    await fetch(URLUsersApi, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
    }).then(form.reset())
    loadIntoTablesBody(URLUsersApi, allUsersTableBody);
    document.getElementById('editClose').click()
}