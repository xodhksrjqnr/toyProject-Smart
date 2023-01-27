import axios from 'axios';

export async function upload(product, file, detail) {
  const { name, price, code } = product;
  const size = product.size.split(',');
  const data = new FormData();
  for (const value of file) {
    data.append('imgFiles', value);
  }
  data.append('name', name);
  data.append('price', price);
  data.append('code', code);
  data.append('detailInfo', detail);
  for (const value of size) {
    data.append('size', value);
  }

  return axios
    .post(process.env.REACT_APP_REGISTER_URL, data) //
    .then((data) => data) //
    .catch((error) => error.response.status);
}
