import axios from 'axios';
import Cookies from 'universal-cookie';

export async function myorder() {
  const cookies = new Cookies();
  return axios
    .get(process.env.REACT_APP_ORDERS_URL, {
      headers: {
        Authorization: `Bearer ${cookies.get('loginToken')}`,
      },
      withCredentials: true,
    }) //
    .then((res) => res) //
    .catch((error) => error.response.status);
}
