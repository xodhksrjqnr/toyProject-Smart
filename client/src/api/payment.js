import axios from 'axios';
import Cookies from 'universal-cookie';

export async function payment(cart) {
  const cookies = new Cookies();

  const ordersObj = { orderList: [] };

  for (const item of cart) {
    const itemObj = {
      productId: item.productId,
      size: item.size,
      quantity: item.quantity,
    };

    ordersObj.orderList.push(itemObj);
  }

  return axios
    .post(process.env.REACT_APP_ORDERS_REGISTER_URL, ordersObj, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${cookies.get('loginToken')}`,
      },
      withCredentials: true,
    }) //
    .then((data) => data) //
    .catch((error) => error.response);
}
