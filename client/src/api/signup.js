import axios from 'axios';

export async function signup(info) {
  const { id, password, email, birthday, phoneNumber } = info;
  const data = new FormData();

  data.append('nickName', id);
  data.append('password', password);
  data.append('email', email);
  data.append('birthday', birthday);
  data.append('phoneNumber', phoneNumber);

  return axios
    .post(process.env.REACT_APP_SIGNUP_URL, data)
    .then((data) => data)
    .catch((error) => error.response);
}
