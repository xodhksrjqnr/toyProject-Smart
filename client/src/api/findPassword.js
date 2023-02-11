import axios from 'axios';

export async function findPassword(info) {
  const { email, id } = info;
  const data = new FormData();

  data.append('email', email);
  data.append('nickName', id);

  return axios
    .post(process.env.REACT_APP_FIND_PASSWORD_URL, data)
    .then((res) => res)
    .catch((error) => error.response);
}
