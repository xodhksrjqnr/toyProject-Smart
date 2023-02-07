import axios from 'axios';
import Cookies from 'universal-cookie';

export async function payment(cart) {
  const data = new FormData();
  const cookies = new Cookies();
  for (const value of cart) {
    data.append('productId', value.productId);
    data.append('quantity', value.quantity);
    data.append('size', value.size);
  }

  return axios
    .get(process.env.REACT_APP_ORDERS_REGISTER_URL, data, {
      headers: {
        Authorization: `loginToken=${cookies.get('loginToken')}`,
      },
      withCredentials: true,
    }) //
    .then((data) => data) //
    .catch((error) => error.response);
}
