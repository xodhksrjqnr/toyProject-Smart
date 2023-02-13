import axios from 'axios';

export async function update(product, file, detail) {
  const { id, name, price, code } = product;
  const size = product.size.split(',');
  const data = new FormData();
  for (const value of file) {
    data.append('imgFiles', value);
  }
  data.append('productId', id);
  data.append('name', name);
  data.append('price', price);
  data.append('code', code);
  for (const value of detail) {
    data.append('detailInfo', value);
  }
  for (const value of size) {
    data.append('size', value);
  }

  return axios
    .post(process.env.REACT_APP_UPDATE_URL, data)
    .then((data) => data) //
    .catch((error) => error.response);
}
