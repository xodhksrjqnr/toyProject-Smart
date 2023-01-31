import axios from 'axios';

export default class Product {
  constructor() {
    this.httpClient = axios.create({
      baseURL: process.env.REACT_APP_PRODUCT_API_URL,
    });
  }

  async products(value, sort, page, type) {
    return type
      ? this.search(value, sort, page)
      : this.categoryProduct(value, sort, page);
  }

  async registeredProducts() {
    return this.httpClient.get('products').then((res) => res.data);
  }

  async mainProduct(code = null, size = null, sort = 'id,desc') {
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

  async search(search, sort = 'id,desc', page) {
    return this.httpClient
      .get('/products/filter', {
        params: {
          search: search,
          sort: sort,
          page: page,
        },
      })
      .then((res) => res.data);
  }

  async categoryProduct(code, sort = 'id,desc', page) {
    return this.httpClient
      .get('/products/filter', {
        params: {
          code: code,
          sort: sort,
          page: page,
        },
      })
      .then((res) => res.data);
  }
}
