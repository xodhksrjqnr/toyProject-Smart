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

  async getProduct(id) {
    return this.httpClient.get(`products/${id}`).then((res) => res.data);
  }

  async search(search, order = 'id,desc') {
    return this.httpClient
      .get('/products/filter', {
        params: {
          search: search,
          sort: order,
        },
      })
      .then((res) => res.data);
  }
}
