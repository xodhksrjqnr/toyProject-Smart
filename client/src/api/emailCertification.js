import axios from 'axios';

export async function emailCertification(email) {
  const data = new FormData();

  data.append('email', email);

  return axios
    .post(process.env.REACT_APP_EMAIL_CERTIFICATION_URL, data)
    .then((res) => res)
    .catch((error) => error.response);
}
