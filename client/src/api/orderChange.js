import axios from 'axios';
import Cookies from 'universal-cookie';

export async function orderChange(orderItemId, text, isCancel) {
  const cookies = new Cookies();
  const data = new FormData();

  data.append('orderItemId', orderItemId);
  data.append('reason', text);

  const URL = isCancel
    ? process.env.REACT_APP_ORDERS_CANCEL_URL
    : process.env.REACT_APP_ORDERS_REFUND_URL;

  return axios
    .post(URL, data, {
      headers: {
        Authorization: `Bearer ${cookies.get('loginToken')}`,
      },
      withCredentials: true,
    }) //
    .then((res) => res) //
    .catch((error) => error.response.status);
}
