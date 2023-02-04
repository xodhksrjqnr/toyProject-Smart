import axios from 'axios';

export async function signup(info) {
  const { id, password, email, birthday, phone } = info;
  const data = new FormData();

  data.append('id', id);
  data.append('password', password);
  data.append('email', email);
  data.append('birthday', birthday);
  data.append('birthday', phone);

  return axios
    .post(process.env.REACT_APP_SIGNUP_URLL, data)
    .then((data) => data)
    .catch((error) => error.response);
}
