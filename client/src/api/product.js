import axios from 'axios';

export default class Product {
  constructor() {
    this.httpClient = axios.create({
      baseURL: process.env.REACT_APP_PRODUCT_API_URL,
    });
  }

  async registeredProducts() {
    return this.httpClient.get('products').then((res) => res.data);
  }

  async mainProduct(code = null, size = null, sort = 'id,DESC') {
    console.log(code, size, sort);
    return this.httpClient
      .get('products/filter', {
        params: {
          code: code,
          size: size,
          sort: sort,
        },
      })
      .then((res) => res.data);
  }
}
