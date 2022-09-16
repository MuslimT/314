const url = 'api/user'
let loggedInUser = document.querySelector('#User')

//fetch(url, {mode: "no-cors"})
fetch(url)
    .then(res => res.json())
    .then(data => {
        loggedInUser.innerHTML = `
                                <td>${data.id}</td>
                                <td>${data.username}</td>
                                <td>${data.email}</td>
                                <td>${data.age}</td>
                                <td>${data.roles.map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN')}</td>
                                `
    })