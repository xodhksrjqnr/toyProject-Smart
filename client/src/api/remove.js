import axios from 'axios';

export async function remove(id) {
  const data = new FormData();
  data.append('productId', id);

  return axios
    .post(`${process.env.REACT_APP_DELETE_URL}${id}/delete`, data) //
    .then((data) => data) //
    .catch((error) => error.response.status);
}
