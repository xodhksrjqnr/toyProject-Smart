import axios from 'axios';

export async function emailCertification(email, findId) {
  const data = new FormData();
  data.append('email', email);

  const URL = findId
    ? process.env.REACT_APP_FIND_ID_URL
    : process.env.REACT_APP_EMAIL_CERTIFICATION_URL;

  return axios
    .post(URL, data)
    .then((res) => res)
    .catch((error) => error.response);
}
